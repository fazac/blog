package com.yyft.blog.mapper;

import com.yyft.blog.entity.YfUsr;
import org.springframework.stereotype.Repository;

@Repository
public interface YfUsrMapper {
    int deleteByPrimaryKey(Integer sn);

    int insert(YfUsr record);

    int insertSelective(YfUsr record);

    YfUsr selectByPrimaryKey(Integer sn);

    int updateByPrimaryKeySelective(YfUsr record);

    int updateByPrimaryKey(YfUsr record);
}