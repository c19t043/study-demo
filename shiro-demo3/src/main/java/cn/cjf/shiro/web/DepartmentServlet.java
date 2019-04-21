package cn.cjf.shiro.web;

import cn.cjf.shiro.domain.Department;
import cn.cjf.shiro.domain.Employee;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;

@WebServlet(name = "departmentServlet", urlPatterns = "/department")
public class DepartmentServlet extends HttpServlet {
    private static List<Department> list;

    static {
        list = new ArrayList<>();
        list.add(new Department(1, "总经办"));
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        doPost(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String cmd = req.getParameter("cmd");
        if ("input".equals(cmd)) {
            String idStr = req.getParameter("id");
            if (idStr != null && !"".equals(idStr)) {
                Integer id = Integer.parseInt(idStr);
                Optional<Department> optionalEmployee = list.stream()
                        .filter(department -> department.getId().equals(id))
                        .findAny();
                Department department = optionalEmployee.get();
                req.setAttribute("department", department);
            }
            req.getRequestDispatcher("/WEB-INF/views/department/input.jsp").forward(req, resp);
        } else if ("saveOrUpdate".equals(cmd)) {
            String idStr = req.getParameter("id");
            String name = req.getParameter("name");
            if (idStr != null && !"".equals(idStr)) {
                Integer id = Integer.parseInt(idStr);
                list.stream()
                        .filter(department -> department.getId().equals(id))
                        .forEach(department -> {
                            department.setName(name);
                        });
            } else {
                Optional<Department> optional = list.stream().max(Comparator.comparing(Department::getId));
                Integer id = optional.get().getId() + 1;
                list.add(new Department(id, name));
            }
            req.setAttribute("departmentList", list);
            req.getRequestDispatcher("/WEB-INF/views/department/list.jsp").forward(req, resp);
        } else if ("delete".equals(cmd)) {
            String idStr = req.getParameter("id");
            if (idStr != null && !"".equals(idStr)) {
                Integer id = Integer.parseInt(idStr);
                Optional<Department> optional = list.stream()
                        .filter(department -> department.getId().equals(id))
                        .findAny();
                Department department = optional.get();
                list.remove(department);
            }
            req.setAttribute("departmentList", list);
            req.getRequestDispatcher("/WEB-INF/views/department/list.jsp").forward(req, resp);
        } else {
            req.setAttribute("departmentList", list);
            req.getRequestDispatcher("/WEB-INF/views/department/list.jsp").forward(req, resp);
        }
    }
}
