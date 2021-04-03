package com.yyft.blog.entity;

/**
 * @Description 常量
 * @Author fzc
 * @Date 2020-06-16 10:10
 * @Version 1.0
 */

public interface Constants {
    int MIN_SALT_LENGTH = 10;
    int MAX_SALT_LENGTH = 13;
    int CACHE_EXPIRE_DAY = 3;
    int PAGE_SIZE = 10;
    int FONT_PAGE_SIZE = 5;
    String TOKEN_KEY_NAME = "token";
    String JWT_ISSUER = "yyft.tk";
    String LINE_SEPERATOR = "|";
    String TWO_LINE_SEPERATOR = "||";
    String ADMIN_PATH = "/admin/";
}