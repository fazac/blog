package com.yyft.blog.mapper;

import com.yyft.blog.entity.Feeling;
import com.yyft.blog.entity.ModuleType;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;
import org.springframework.stereotype.Repository;

import java.util.List;

@Mapper
@Repository
public interface ModuleTypeMapper {

    @Insert("insert into module_type (type_name, sort_id, create_time) values " +
            "(#{typeName,jdbcType=VARCHAR}, #{sortId,jdbcType=INTEGER}, #{createTime,jdbcType=TIMESTAMP})")
    int insert(ModuleType record);

    @Select("select * from module_type order by sort_id asc")
    List<ModuleType> getModuleTypes();

    @Update("<script> update module_type " +
            " <trim prefix='set' suffixOverrides=','> " +
            " <if test='typeName!=null'> type_name = #{typeName,jdbcType=VARCHAR}, </if> " +
            " <if test='sortId!=null'> sort_id = #{sortId,jdbcType=INTEGER}, </if> " +
            " </trim> where sn = #{sn,,jdbcType=INTEGER} " +
            " </script>")
    int update(ModuleType record);

    @Select("select *  from module_type where sn = #{sn,jdbcType=INTEGER}")
    ModuleType selectByPrimaryKey(Integer sn);
}