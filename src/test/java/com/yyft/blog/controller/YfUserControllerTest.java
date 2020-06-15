package com.yyft.blog.controller;

import com.yyft.blog.entity.YfUsr;
import com.yyft.common.utils.mapper.JsonMapper;
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

import java.nio.charset.StandardCharsets;

/**
 * @Description
 * @Author fzc
 * @Date 2020-06-15 15:00
 * @Version 1.0
 */
@Slf4j
public class YfUserControllerTest extends BaseMock {


    private MockMvc mvc;

    @Autowired
    private YfUserController yfUserController;


    @Before
    @Override
    public void setUp() {
        mvc = MockMvcBuilders.standaloneSetup(yfUserController).build();
    }


    @Test
    @Rollback(false)
    public void insert() throws Exception {
        String uri = "/usr/addUser";
        YfUsr usr = new YfUsr();
        usr.setName("aa");
        usr.setPassword("mad");
        usr.setPhone("12300000000");
        String requestJson = JsonMapper.INSTANCE.toJson(usr);
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
}