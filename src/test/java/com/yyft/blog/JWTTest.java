package com.yyft.blog;

import io.jsonwebtoken.JwtBuilder;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Encoders;
import io.jsonwebtoken.security.Keys;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import javax.crypto.SecretKey;
import java.security.Key;
import java.security.KeyPair;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.util.Date;

@SpringBootTest
public class JWTTest {

    @Test
    public void testJWT() {
//        Key key = Keys.secretKeyFor(SignatureAlgorithm.HS256);
//        JwtBuilder builder = Jwts.builder().setId("888").setSubject("Joe1")
//                .setIssuedAt(new Date())
//                .signWith(key);
//        String jws = builder.compact();
//        System.out.println(jws);
//        try {
//            Jwts.parserBuilder().setSigningKey(key).build().parseClaimsJws(jws + "1");
//        } catch (JwtException e) {
//            e.printStackTrace();
//        }
//        assert Jwts.parserBuilder().setSigningKey(key).build().parseClaimsJws(jws).getBody().getSubject().equals("Joe1");
//        SecretKey key = Keys.secretKeyFor(SignatureAlgorithm.HS256);
//        String secretString = Encoders.BASE64.encode(key.getEncoded());
//        System.out.println(secretString);
        KeyPair keyPair = Keys.keyPairFor(SignatureAlgorithm.RS256);
        PrivateKey privateKey = keyPair.getPrivate();
        System.out.println(Encoders.BASE64.encode(privateKey.getEncoded()));
        PublicKey publicKey = keyPair.getPublic();
        System.out.println(Encoders.BASE64.encode(publicKey.getEncoded()));

        String jws = Jwts.builder()
                .setHeaderParam("k_id", "bbb123")
                .setHeaderParam("k_name", "bbb")
                .setSubject("Bob")
                .signWith(privateKey)
                .compact();
        System.out.println(jws);

    }
}


