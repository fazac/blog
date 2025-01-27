package com.yyft.blog.mapper;

import com.yyft.blog.entity.YfUsr;
import org.apache.ibatis.annotations.Mapper;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface YfUsrMapper {
    int deleteByPrimaryKey(Integer sn);

    int insert(YfUsr record);

    int insertSelective(YfUsr record);

    YfUsr selectByPrimaryKey(Integer sn);

    int updateByPrimaryKeySelective(YfUsr record);

    int updateByPrimaryKey(YfUsr record);

    YfUsr findByMobilePass(String mobile, String pass);

    List<YfUsr> findByMobile(String mobile);
}