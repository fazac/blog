package com.yyft.blog.controller;

import com.yyft.blog.entity.Blog;
import com.yyft.blog.entity.Constants;
import com.yyft.common.utils.mapper.JsonMapper;
import com.yyft.common.utils.number.RandomUtil;
import com.yyft.common.utils.time.ClockUtil;
import lombok.extern.slf4j.Slf4j;
import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;

import java.nio.charset.StandardCharsets;
import java.util.Arrays;

/**
 * @Description
 * @Author fzc
 * @Date 2020-06-22 9:46
 * @Version 1.0
 */
@Slf4j
public class BlogControllerTest extends BaseMock {

    private MockMvc mvc;

    @Autowired
    private BlogController blogController;

    @Before
    @Override
    public void setUp() {
        mvc = MockMvcBuilders.standaloneSetup(blogController).build();
    }

    @Test
    @Rollback(false)
    public void saveBlog() throws Exception {
        String uri = "/blogs";
        Blog blog = new Blog();
        blog.setContent("hhhhhhhhhhh");
        blog.setCreateTime(ClockUtil.currentDate());
        blog.setDigest("hhh");
        blog.setTitle("A");
        blog.setStatus("1");
        blog.setSortId(1);
        String requestJson = JsonMapper.INSTANCE.toJson(blog);
        System.out.println(requestJson);
        MvcResult result = mvc
                .perform(MockMvcRequestBuilders.post(uri)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(requestJson)
                        .characterEncoding(StandardCharsets.UTF_8.name())
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk()).andDo(MockMvcResultHandlers.print()).andReturn();
        System.out.println("aaa " + result.getResponse().getContentAsString());
        assert (200 == result.getResponse().getStatus());
    }

    @Test
    public void getBlogs() throws Exception {
        String uri = "/blogs";
        MultiValueMap<String, String> map = new LinkedMultiValueMap<String, String>();
        map.add("page", "1");
        map.add("pageSize", "12");
        map.add("type", "");
        MvcResult result = mvc
                .perform(MockMvcRequestBuilders.get(uri)
                        .contentType(MediaType.APPLICATION_JSON)
                        .params(map)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk()).andDo(MockMvcResultHandlers.print()).andReturn();
        assert (200 == result.getResponse().getStatus());
    }

    @Test
    public void testStringJoin() {
//        StringBuffer sb = new StringBuffer();
//        sb.append("{\"location\":\"").append("http://wwww.baidu.com").append("\"}");
//        log.info(sb.toString());
//        log.info(RandomUtil.randomAsciiFixLength(12));
//        log.info(RandomUtil.randomLetterFixLength(12));
//        log.info(RandomUtil.randomStringFixLength(12));
        String lids = "|1||2||13|";
        String s = lids.substring(1, lids.length() - 1);
        log.info(s);
        String[] sa = s.split("\\|\\|");
        log.info(JsonMapper.INSTANCE.toJson(sa));
        s = s.replace(Constants.TWO_LINE_SEPERATOR, ",");
        log.info(s);
    }

}