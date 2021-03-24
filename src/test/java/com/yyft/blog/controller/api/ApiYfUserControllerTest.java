package com.yyft.blog.controller.api;

import com.yyft.blog.controller.BaseMock;
import com.yyft.blog.controller.api.ApiYfUserController;
import com.yyft.blog.entity.YfUsr;
import com.yyft.common.utils.mapper.JsonMapper;
import com.yyft.common.utils.security.MD5Util;
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

/**
 * @Description
 * @Author fzc
 * @Date 2020-06-15 15:00
 * @Version 1.0
 */
@Slf4j
public class ApiYfUserControllerTest extends BaseMock {


    private MockMvc mvc;

    @Autowired
    private ApiYfUserController apiYfUserController;


    @Before
    @Override
    public void setUp() {
        mvc = MockMvcBuilders.standaloneSetup(apiYfUserController).build();
    }


//    @Test
    @Rollback(false)
    public void register() throws Exception {
        String uri = "/usr/register";
        YfUsr usr = new YfUsr();
        usr.setName("aa");
//        String salt = RandomUtil.randomStringRandomLength(Constants.MIN_SALT_LENGTH, Constants.MAX_SALT_LENGTH);
        String salt = "BwKY1wRbJE";
        usr.setPassword(MD5Util.passwordEncode("madman", salt));
        usr.setPhone("12300000000");
        usr.setSalt(salt);
        System.out.println(salt);
        System.out.println(MD5Util.passwordEncode("madman", salt));
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

    @Test
//    @Rollback(false)
    public void login() throws Exception {
        String uri = "/usr/login";
        String mobile = "12300000000";
        String pass = MD5Util.passwordEncode("madman", "BwKY1wRbJE");
        MultiValueMap<String, String> map = new LinkedMultiValueMap<String, String>();
        map.add("mobile", mobile);
        map.add("pass", pass);
        MvcResult result = mvc
                .perform(MockMvcRequestBuilders.get(uri)
                        .contentType(MediaType.APPLICATION_JSON)
                        .params(map)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk()).andDo(MockMvcResultHandlers.print()).andReturn();
        System.out.println("aaa " + result.getResponse().getContentAsString());
        assert (200 == result.getResponse().getStatus());
    }
}