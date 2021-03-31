package com.yyft.blog.controller;

import com.yyft.blog.util.FileUploadUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;

@Slf4j
@Controller
@RequestMapping(value = "/blog/upload")
public class FileUploadController {
    private static final String IMAGE_URL_PREFIX = "http://localhost:8001/static/upload_file/";
    private final StringBuffer sb;

    {
        sb = new StringBuffer();
    }

    @PostMapping
    @ResponseBody
    public String uploadFile(MultipartFile file, HttpServletRequest request) {
        String path = request.getSession().getServletContext().getRealPath("upload_file");
        String imageUrl = FileUploadUtil.upload(file, path);
        sb.setLength(0);
        sb.append("{\"location\":\"").append(IMAGE_URL_PREFIX).append(imageUrl).append("\"}");
        return sb.toString();
    }

}
