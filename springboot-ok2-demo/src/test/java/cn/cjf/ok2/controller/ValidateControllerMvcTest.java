package cn.cjf.ok2.controller;

import cn.cjf.ok2.MainApplication;
import cn.cjf.ok2.api.CommonResult;
import cn.cjf.ok2.api.ResultUtil;
import cn.cjf.ok2.domain.Leader;
import com.alibaba.fastjson.JSON;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.validation.BindingResult;

@SpringBootTest(classes = MainApplication.class)
@RunWith(SpringRunner.class)
//@AutoConfigureMockMvc
public class ValidateControllerMvcTest {
    @Autowired
    ValidateController validateController;

    public MockMvc mockMvc;

    public String baseUrl;

    @Before
    public void before() {
        mockMvc = MockMvcBuilders.standaloneSetup(validateController).build();
        baseUrl = "/validate";
    }

    /**
     * {@link ValidateController#groupValidate1(cn.cjf.ok2.domain.ValidateGroupBean)}
     */
    @Test
    public void testgroupValidate1() throws Exception {
        ResultActions resultActions = this.mockMvc.perform(
                MockMvcRequestBuilders
                        .get(baseUrl + "/groupValidate1")
                        .param("name", "12")
        ).andDo(MockMvcResultHandlers.print());
        MvcResult mvcResult = resultActions.andReturn();
        Exception resolvedException = mvcResult.getResolvedException();
        if (resolvedException == null) {
            Assert.fail();
        } else {
            BindingResult bindingResult = (BindingResult) resolvedException;
            CommonResult commonResult = ResultUtil.processBindResult(bindingResult);
            System.out.println("=====客户端获得反馈数据:" + JSON.toJSONString(commonResult));
        }
    }


    /**
     * {@link ValidateController#groupValidate(cn.cjf.ok2.domain.ValidateGroupBean)}
     */
    @Test
    public void testgroupValidate() throws Exception {
        ResultActions resultActions = this.mockMvc.perform(
                MockMvcRequestBuilders
                        .get(baseUrl + "/groupValidate")
                        .param("name", "12")
        ).andDo(MockMvcResultHandlers.print());
        MvcResult mvcResult = resultActions.andReturn();
        Exception resolvedException = mvcResult.getResolvedException();
        if (resolvedException == null) {
            String result = mvcResult.getResponse().getContentAsString();
            System.out.println("=====客户端获得反馈数据:" + result);
        } else {
            Assert.fail();
        }
    }

    /**
     * {@link ValidateController#param2(java.lang.String)}
     */
    @Test
    public void testparam2() throws Exception {
        ResultActions resultActions = this.mockMvc.perform(
                MockMvcRequestBuilders
                        .get(baseUrl + "/param2")
        );
        MvcResult mvcResult = resultActions.andReturn();
        String result = mvcResult.getResponse().getContentAsString();
        System.out.println("=====客户端获得反馈数据:" + result);
    }

    /**
     * {@link ValidateController#param(java.lang.String)}
     */
    @Test
    public void testparam() throws Exception {
        ResultActions resultActions = this.mockMvc.perform(
                MockMvcRequestBuilders
                        .get(baseUrl + "/param")
        );
        MvcResult mvcResult = resultActions.andReturn();
        String result = mvcResult.getResponse().getContentAsString();
        System.out.println("=====客户端获得反馈数据:" + result);
    }

    /**
     * {@link ValidateController#json(cn.cjf.ok2.domain.Leader, org.springframework.validation.BindingResult)}
     */
    @Test
    public void testjson() throws Exception {
        ResultActions resultActions = this.mockMvc.perform(
                MockMvcRequestBuilders.post(baseUrl + "/json")
                        .content(JSON.toJSONString(new Leader("12345", "2001-01-010", 6)))
                        .contentType(MediaType.APPLICATION_JSON)
        );
        MvcResult mvcResult = resultActions.andReturn();
        String result = mvcResult.getResponse().getContentAsString();
        System.out.println("=====客户端获得反馈数据:" + result);
    }

    /**
     * {@link ValidateController#testSimpleValidate2(cn.cjf.ok2.domain.Leader)}
     */
    @Test
    public void testSimpleValidate2() throws Exception {
        ResultActions resultActions = this.mockMvc.perform(
                MockMvcRequestBuilders.get(baseUrl + "/test2")
                        .param("birthday", "2001-01-011")
                        .param("name", "asdfgh")
                        .param("age", "6")
        );
//        MvcResult mvcResult = resultActions.andReturn();
//        Exception e = mvcResult.getResolvedException();
//        if (e instanceof BindingResult) {
//            BindingResult bindingResult = (BindingResult) e;
//            CommonResult<Object> objectCommonResult = ResultUtil.processBindResult(bindingResult);
//            System.out.println("=====客户端获得反馈数据:" + JSON.toJSONString(objectCommonResult));
//        }

        MvcResult mvcResult = resultActions.andReturn();
        String result = mvcResult.getResponse().getContentAsString();
        System.out.println("=====客户端获得反馈数据:" + result);
    }

    /**
     * {@link ValidateController#testSimpleValidate(cn.cjf.ok2.domain.Leader, org.springframework.validation.BindingResult)}
     */
    @Test
    public void testSimpleValidate() throws Exception {
        ResultActions resultActions = this.mockMvc.perform(
                MockMvcRequestBuilders.post(baseUrl + "/test")
                        .param("birthday", "2001-01-010")
                        .param("name", "12345")
                        .param("age", "1")
        );
        MvcResult mvcResult = resultActions.andReturn();
        String result = mvcResult.getResponse().getContentAsString();
        System.out.println("=====客户端获得反馈数据:" + result);
    }


}
