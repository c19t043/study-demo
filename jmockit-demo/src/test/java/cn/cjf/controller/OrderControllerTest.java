package cn.cjf.controller;

import cn.cjf.base.BaseTest;
import cn.cjf.common.response.ApiResult;
import cn.cjf.entity.bo.OrderBo;
import cn.cjf.service.OrderService;
import mockit.*;
import org.junit.Assert;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.mock.web.MockHttpSession;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;

import javax.servlet.http.HttpServletRequest;

public class OrderControllerTest extends BaseTest {

    /**
     * 要测试的对象
     */
    @Autowired
    OrderController orderController;

    @Autowired
    OrderService orderService;

    /**
     * 测试{@link OrderController#getObject(javax.servlet.http.HttpServletRequest)}
     * 模拟HttpSession
     */
    @Test
    public void testgetObject_4() {
        MockHttpSession session = new MockHttpSession();
        session.setAttribute("managerId",1);

        MockHttpServletRequest request = new MockHttpServletRequest();
        request.setSession(session);
        request.setParameter("id","3");

        ApiResult order = orderController.getObject(request);
        Assert.assertNotNull(order.getData());

        OrderBo orderBo = (OrderBo) order.getData();
        Assert.assertEquals(orderBo.getId(), Long.valueOf("3"));
    }

    /**
     * 测试{@link OrderController#getObject(javax.servlet.http.HttpServletRequest)}
     * 模拟HttpServletRequest,HttpSession
     */
    @Test
    public void testgetObject_3() {
        MockHttpServletRequest request = new MockHttpServletRequest();
        request.setParameter("id","3");

        ApiResult order = orderController.getObject(request);
        Assert.assertNotNull(order.getData());

        OrderBo orderBo = (OrderBo) order.getData();
        Assert.assertEquals(orderBo.getId(), Long.valueOf("3"));

    }

    /**
     * 测试{@link OrderController#toView()}
     */
    @Test
    public void testtoView() {
        String result = orderController.toView();
        Assert.assertEquals(result, "view/test");
    }

    /**
     * 测试{@link OrderController#getOrder(java.lang.Long)}
     * 模拟Controller内部orderService依赖
     */
    @Test
    public void testgetOrder_2() {
        new Expectations(orderService) {
            {
                orderService.findOrderById(anyLong);
                result = new OrderBo(Long.valueOf("2"), Long.valueOf("2"), Long.valueOf("2222"));
            }
        };

        ApiResult order = orderController.getOrder(Long.valueOf(2));
        Assert.assertNotNull(order.getData());

        OrderBo orderBo = (OrderBo) order.getData();
        Assert.assertEquals(orderBo.getId(), Long.valueOf("2"));
    }

    /**
     * 测试{@link OrderController#getOrder(java.lang.Long)}
     * 直接模拟结果
     */
    @Test
    public void testgetOrder() {
        new Expectations(orderController) {
            {
                orderController.getOrder(anyLong);
                result = ApiResult.succ(new OrderBo(Long.valueOf("2"), Long.valueOf("2"), Long.valueOf("2222")));
            }
        };

        ApiResult order = orderController.getOrder(Long.valueOf(2));
        Assert.assertNotNull(order.getData());

        OrderBo orderBo = (OrderBo) order.getData();
        Assert.assertEquals(orderBo.getId(), Long.valueOf("2"));
    }
}
