package cn.cjf.controller;

import cn.cjf.common.response.ApiResult;
import cn.cjf.common.response.exception.ErrorEnum;
import cn.cjf.entity.bo.OrderBo;
import cn.cjf.service.OrderService;
import cn.cjf.service.ProductService;
import com.alibaba.fastjson.JSON;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

@Controller
public class OrderController {
    @Autowired
    OrderService orderService;
    @Autowired
    ProductService productService;

    @Autowired
    HttpServletRequest request;


    @GetMapping("/order/getList")
    public ApiResult getList() {
        OrderBo orderBo = (OrderBo) request.getSession().getAttribute("orderBo");
        System.out.println(JSON.toJSONString(orderBo));
        return ApiResult.succ();
    }

    /**
     * 返回String
     */
    @GetMapping("/order/test")
    public String toView() {
        return "view/test";
    }

    /**
     * 查询订单byId
     */
    @GetMapping("/order/{id}")
    public ApiResult getOrder(@PathVariable Long id) {
        OrderBo orderBo = orderService.findOrderById(id);
        return ApiResult.succ(orderBo);
    }

    @GetMapping("/order")
    public ApiResult getObject(HttpServletRequest request) {
        Long id = Long.valueOf(request.getParameter("id"));

        HttpSession session = request.getSession();
        Object managerId = session.getAttribute("managerId");
        if (managerId == null) {
            return ApiResult.failMsg(ErrorEnum.PARAMS_ERROR);
        }

        OrderBo orderBo = orderService.findOrderById(id);
        return ApiResult.succ(orderBo);
    }
}
