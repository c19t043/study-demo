package cn.cjf.controller;


import cn.cjf.base.WebBaseTest;
import cn.cjf.domain.entity.User;
import org.junit.Assert;
import org.junit.Test;
import org.springframework.http.ResponseEntity;

public class UserControllerWebTest extends WebBaseTest{


    @Test
    public void testGetUser(){
        ResponseEntity<User> responseEntity = super.restTemplate.getForEntity(baseUrl + "/user/test", User.class);
        Assert.assertEquals("test",responseEntity.getBody().getUsername());
    }
}
