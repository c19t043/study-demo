package cn.cjf.shiro.web;

import cn.cjf.shiro.domain.User;
import org.apache.shiro.authc.IncorrectCredentialsException;
import org.apache.shiro.authc.UnknownAccountException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@WebServlet(name = "loginServlet", urlPatterns = "/login")
public class LoginServlet extends HttpServlet {
    private static List<User> list;

    static {
        list = new ArrayList<>();
        list.add(new User(1, "admin", "666"));
        list.add(new User(2, "zhangsan", "666"));
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        doPost(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String exceptionClassName = (String) req.getAttribute("shiroLoginFailure");
        if(exceptionClassName != null){
            if(UnknownAccountException.class.getName().equals(exceptionClassName)){
                // 最终会抛给异常处理器
                req.setAttribute("errorMsg","账户不存在");
            }else if(IncorrectCredentialsException.class.getName().equals(exceptionClassName)){
                req.setAttribute("errorMsg","用户名/密码错误");
            }else{
                // 最终在异常处理器生成未知错误
                req.setAttribute("errorMsg","其他异常信息");
            }
        }
        // 此方法不处理登录成功（认证成功）,shiro认证成功会自动跳转到上一个请求路径
        // 登录失败还到login页面
        req.getRequestDispatcher("/WEB-INF/login.jsp").forward(req, resp);




//        String username = req.getParameter("username");
//        String password = req.getParameter("password");
//        Optional<User> optional = list.stream().filter(user -> {
//            if (user.getUsername().equals(username) &&
//                    user.getPassword().equals(password)) {
//                return true;
//            } else {
//                return false;
//            }
//        }).findAny();
//
//        if (optional.isPresent()) {
//            req.getSession().setAttribute("user", optional.get());
//            req.setAttribute("username", username);
//            req.getRequestDispatcher("/main").forward(req, resp);
//        } else {
//            req.setAttribute("errorMsg", "账号或密码有误");
//            req.getRequestDispatcher("/WEB-INF/login.jsp").forward(req, resp);
//        }
    }
}
