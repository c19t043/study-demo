package cn.cjf.ok2.controller;

import cn.cjf.ok2.MainApplication;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

@SpringBootTest(classes = MainApplication.class)
@RunWith(SpringRunner.class)
public class ValidateController3MvcTest {
    @Autowired
    ValidateController3 validateController3;

    public MockMvc mockMvc;

    public String baseUrl;

    @Before
    public void before() {
        mockMvc = MockMvcBuilders.standaloneSetup(validateController3).build();
        baseUrl = "/validate3";
    }

    /**
     * {@link ValidateController3#param(java.lang.String)}
     */
    @Test
    public void testparam() throws Exception {
        ResultActions resultActions = this.mockMvc.perform(
                MockMvcRequestBuilders
                        .get(baseUrl + "/param")
                        .param("email", "123@qq.com")
        );
        MvcResult mvcResult = resultActions.andReturn();
        String result = mvcResult.getResponse().getContentAsString();
        System.out.println("=====客户端获得反馈数据:" + result);
    }
}
