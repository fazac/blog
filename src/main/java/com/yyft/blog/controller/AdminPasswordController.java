package com.yyft.blog.controller;

import com.google.zxing.WriterException;
import com.yyft.blog.entity.Constants;
import com.yyft.blog.entity.YfUsr;
import com.yyft.blog.service.YfTokenService;
import com.yyft.blog.service.YfUserService;
import com.yyft.blog.util.TotpUtil;
import com.yyft.common.model.Result;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;

/**
 * @author fzc
 * @version 1.0
 * @description
 * @date 2021/4/4 12:57
 */
@Slf4j
@Controller
@RequestMapping(value = "/admin/password")
public class AdminPasswordController {

    private YfUserService yfUserService;

    private YfTokenService yfTokenService;

    @RequestMapping
    public String pagePassword(Model model) {
        model.addAttribute("url", Constants.KEY_PATH);
        return "admin/password";
    }

    @GetMapping("refreshKey")
    @ResponseBody
    public Result refreshKey(HttpServletRequest request) {
        String token = request.getSession().getAttribute("access_token").toString();
        YfUsr yf = yfUserService.selectById(yfTokenService.findUserIdByToken(token));
        String key = TotpUtil.createSecretKey();
        yf.setTotpSk(key);
        yfUserService.updateById(yf);
        String url = TotpUtil.createGoogleAuthQRCodeData(key);
        try {
            String path = request.getSession().getServletContext().getRealPath(Constants.KEY_DIR);
            TotpUtil.generateMatrixPic(url, Constants.KEY_IMG_SIZE, Constants.KEY_IMG_SIZE, path, Constants.KEY_NAME);
        } catch (IOException | WriterException e) {
            log.error("生成新密钥图片错误", e);
            return Result.createError();
        }
        return Result.createSuccess();
    }

    @Autowired
    public void setYfUserService(YfUserService yfUserService) {
        this.yfUserService = yfUserService;
    }

    @Autowired
    public void setYfTokenService(YfTokenService yfTokenService) {
        this.yfTokenService = yfTokenService;
    }
}
