package com.yyft.blog;

import com.google.zxing.WriterException;
import com.yyft.blog.util.TotpUtil;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.io.IOException;

/**
 * @author fzc
 * @version 1.0
 * @description
 * @date 2021/4/4 13:41
 */
@SpringBootTest
@Slf4j
public class TotpTest {

    @Test
    public void createSecret() throws IOException, WriterException {
        for (int i = 10; i > 0; i--) {
            String secret = TotpUtil.createSecretKey();
            log.info(String.valueOf(secret.length()));
        }
//        String secret = "lf6umwncsjuext235e2bc72tcro5px43";
//        long count = TotpUtil.getTime();
//        String s1 = TotpUtil.getTOTP(secret, count);
//        log.info(s1);
//        String url = TotpUtil.createGoogleAuthQRCodeData(secret);
//        log.info(url);
//        TotpUtil.generateMatrixPic(url, 300, 300, "totp", "user.png");
    }

}
