package com.yyft.blog.controller;

import com.yyft.blog.BlogApplication;
import lombok.extern.slf4j.Slf4j;
import org.junit.Before;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.AbstractTransactionalJUnit4SpringContextTests;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;

/**
 * @Description
 * @Author fzc
 * @Date 2020-06-15 15:02
 * @Version 1.0
 */
@Slf4j
@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(classes = BlogApplication.class)
@WebAppConfiguration
public class BaseMock extends AbstractTransactionalJUnit4SpringContextTests {

    protected MockMvc mockMvc;

    @Before
    public void setUp() {

    }

    /**
     * 获取登入信息session
     * @return
     * @throws Exception
     */
//        protected HttpSession getLoginSession() throws Exception{
//            MvcResult result =  mockMvc.perform( MockMvcRequestBuilders.post( "/login/mockLogin" ) //请求的url
//                    .param( "username" , "ceshi" ).param("password", "0NfZVMKwfhLJ+ZCbqZ+Tzg=="))
//                    .andExpect( MockMvcResultMatchers.status().isOk() ) //返回的状态200
//                    .andDo( MockMvcResultHandlers.print() ) //在控制台打印
//                    .andReturn(); //返回
//            return result.getRequest().getSession();
//        }

}