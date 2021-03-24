package com.yyft.blog.util;

import com.yyft.blog.entity.Constants;
import com.yyft.blog.entity.YfToken;
import com.yyft.blog.entity.YfUsr;
import com.yyft.blog.service.YfTokenService;
import com.yyft.common.model.BizException;
import com.yyft.common.model.ResultCode;
import com.yyft.common.utils.collection.type.Pair;
import com.yyft.common.utils.number.RandomUtil;
import com.yyft.common.utils.text.EncodeUtil;
import com.yyft.common.utils.time.ClockUtil;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.time.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.security.KeyFactory;
import java.security.KeyPair;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.interfaces.RSAPublicKey;
import java.security.spec.X509EncodedKeySpec;
import java.util.Date;

@Component
@Slf4j
public class TokenUtil {

    private RedisUtil redisUtil;
    
    private YfTokenService yfTokenService;

    public static final String AUTH_HEADER_KEY = "Authorization";

    public static final String TOKEN_PREFIX = "Bearer ";


    public Pair<String, String> createToken(YfUsr yfUsr) {
        Date now = ClockUtil.currentDate();
        KeyPair keyPair = Keys.keyPairFor(SignatureAlgorithm.RS256);
        PrivateKey privateKey = keyPair.getPrivate();
        PublicKey publicKey = keyPair.getPublic();
        String salt = RandomUtil.randomStringRandomLength(Constants.MIN_SALT_LENGTH, Constants.MAX_SALT_LENGTH);
        String publicKeyString = EncodeUtil.encodeBase64(publicKey.getEncoded());
        redisUtil.addCache(Constants.TOKEN_KEY_NAME, publicKeyString, EncodeUtil.encodeBase64(privateKey.getEncoded()));
        YfToken yfToken = new YfToken();
        yfToken.setTokenSalt(salt);
        yfToken.setUpdateTime(now);
        yfToken.setExpireDate(DateUtils.addDays(now, Constants.CACHE_EXPIRE_DAY));
        String token = Jwts.builder().setHeaderParam("typ", "JWT")
                // 可以将基本不重要的对象信息放到claims
                .claim("mobile", yfUsr.getPhone())
                .claim("name", yfUsr.getName())
                .claim("userId", yfUsr.getSn())
                .claim("salt", salt)
                .setSubject(yfUsr.getPhone())           // 所有人
                .setIssuer(Constants.JWT_ISSUER)        // 签发主体
                .setIssuedAt(now)                       // 签发时间
                .setExpiration(yfToken.getExpireDate()) // 失效时间
                .setAudience(yfUsr.getPhone())          // 接收对象
                .signWith(privateKey, SignatureAlgorithm.RS256)
                .compact();
        yfToken.setToken(token);
        yfToken.setUserSn(yfUsr.getSn());
        yfToken.setPublicKey(publicKeyString);
        yfTokenService.addToken(yfToken);
        return new Pair<>(token, publicKeyString);
    }

    public YfUsr checkToken(String token) {
        try {
            String publicKey = yfTokenService.findPublicKeyByToken(token);
            X509EncodedKeySpec keySpecX509 = new X509EncodedKeySpec(EncodeUtil.decodeBase64(publicKey));
            KeyFactory keyFactory = KeyFactory.getInstance("RSA");
            RSAPublicKey pubKey = (RSAPublicKey) keyFactory.generatePublic(keySpecX509);
            Claims claims = Jwts.parserBuilder()
                    .requireIssuer(Constants.JWT_ISSUER)
                    .setSigningKey(pubKey)
                    .build()
                    .parseClaimsJws(token).getBody();
            YfUsr yfUsr = new YfUsr();
            yfUsr.setSn(claims.get("userId", Integer.class));
            yfUsr.setPhone(claims.get("mobile", String.class));
            yfUsr.setName(claims.get("name", String.class));
            yfUsr.setSalt(claims.get("salt", String.class));
            return yfUsr;
        } catch (BizException biz) {
            log.error("===== 自定义异常 =====", biz);
            throw biz;
        } catch (ExpiredJwtException eje) {
            log.error("===== Token过期 =====", eje);
            throw new BizException(ResultCode.PERMISSION_TOKEN_EXPIRED.message());
        } catch (Exception e) {
            log.error("===== token解析异常 =====", e);
            throw new BizException(ResultCode.PERMISSION_TOKEN_INVALID.message());
        }
    }

    @Autowired
    public void setRedisUtil(RedisUtil redisUtil) {
        this.redisUtil = redisUtil;
    }

    @Autowired
    public void setYfTokenService(YfTokenService yfTokenService) {
        this.yfTokenService = yfTokenService;
    }
}
