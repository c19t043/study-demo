package cn.cjf.controller;

import cn.cjf.common.response.ApiResult;
import cn.cjf.entity.bo.OrderBo;
import cn.cjf.service.OrderService;
import cn.cjf.service.ProductService;
import mockit.Expectations;
import mockit.Injectable;
import mockit.Tested;
import mockit.integration.junit4.JMockit;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.mock.web.MockHttpSession;

public class OrderControllerJmockitTest {
    /**
     * 要测试的对象
     */
    @Tested
    OrderController orderController;

    @Injectable
    OrderService orderService;

    @Injectable
    ProductService productService;

    /**
     * 测试{@link OrderController#getObject(javax.servlet.http.HttpServletRequest)}
     * 模拟HttpSession
     */
    @Test
    public void testgetObject_4() {
        MockHttpSession session = new MockHttpSession();
        session.setAttribute("managerId", 1);

        MockHttpServletRequest request = new MockHttpServletRequest();
        request.setSession(session);
        request.setParameter("id", "3");

        new Expectations() {
            {
                orderService.findOrderById(anyLong);
                result = new OrderBo(Long.valueOf("3"), Long.valueOf("2"), Long.valueOf("2222"));
            }
        };

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
        request.setParameter("id", "3");

        new Expectations() {
            {
                orderService.findOrderById(anyLong);
                result = new OrderBo(Long.valueOf("3"), Long.valueOf("2"), Long.valueOf("2222"));
            }
        };

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
     * 测试{@link OrderController#getOrder(Long)}
     * 模拟Controller内部orderService依赖
     */
    @Test
    public void testgetOrder_2() {
        new Expectations() {
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
}
