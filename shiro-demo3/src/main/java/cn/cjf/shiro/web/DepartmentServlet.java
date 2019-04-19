package cn.cjf.shiro.web;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet(name = "departmentServlet", urlPatterns = "/department")
public class DepartmentServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        doPost(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String cmd = req.getParameter("cmd");
        if ("input".equals(cmd)) {
            String id = req.getParameter("id");
            if (id != null) {
                req.setAttribute("name", "总经办");
                req.setAttribute("cmdType", "编辑");
            } else {
                req.setAttribute("cmdType", "新增");
            }
            req.getRequestDispatcher("/WEB-INF/views/department/input.jsp").forward(req, resp);
        } else if ("saveOrUpdate".equals(cmd)) {

        } else if ("delete".equals(cmd)) {

        } else {
            req.getRequestDispatcher("/WEB-INF/views/department/list.jsp").forward(req, resp);
        }
    }
}
