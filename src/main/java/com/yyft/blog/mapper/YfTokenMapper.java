package com.yyft.blog.mapper;

import com.yyft.blog.entity.YfToken;
import org.springframework.stereotype.Repository;

import java.util.Date;

@Repository
public interface YfTokenMapper {
    int deleteByPrimaryKey(Integer sn);

    int insert(YfToken record);

    int insertSelective(YfToken record);

    YfToken selectByPrimaryKey(Integer sn);

    int updateByPrimaryKeySelective(YfToken record);

    int updateByPrimaryKey(YfToken record);

    YfToken getByUserId(Integer sn, Date date);

    String findPublicKeyByToken(String token);
}