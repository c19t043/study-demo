package cn.cjf.shiro.web;

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

@WebServlet(name = "employeeServlet", urlPatterns = "/employee")
public class EmployeeServlet extends HttpServlet {

    private static List<Employee> list;

    static {
        list = new ArrayList<>();
        list.add(new Employee(1, "zhangsan", 12, "zhangsan@qq.com"));
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
                Optional<Employee> optionalEmployee = list.stream()
                        .filter(employee -> employee.getId().equals(id))
                        .findAny();
                Employee employee = optionalEmployee.get();
                req.setAttribute("employee", employee);
            }
            req.getRequestDispatcher("/WEB-INF/views/employee/input.jsp").forward(req, resp);
        } else if ("saveOrUpdate".equals(cmd)) {
            String idStr = req.getParameter("id");
            String name = req.getParameter("name");
            String age = req.getParameter("age");
            String email = req.getParameter("email");
            if (idStr != null && !"".equals(idStr)) {
                Integer id = Integer.parseInt(idStr);
                list.stream()
                        .filter(employee -> employee.getId().equals(id))
                        .forEach(employee -> {
                            employee.setName(name);
                            employee.setAge(Integer.parseInt(age));
                            employee.setEmail(email);
                        });
            } else {
                Optional<Employee> optional = list.stream().max(Comparator.comparing(Employee::getId));
                Integer id = optional.get().getId() + 1;
                list.add(new Employee(id, name, Integer.parseInt(age), email));
            }
            req.setAttribute("employeeList", list);
            req.getRequestDispatcher("/WEB-INF/views/employee/list.jsp").forward(req, resp);
        } else if ("delete".equals(cmd)) {
            String idStr = req.getParameter("id");
            if (idStr != null && !"".equals(idStr)) {
                Integer id = Integer.parseInt(idStr);
                Optional<Employee> optional = list.stream()
                        .filter(employee -> employee.getId().equals(id))
                        .findAny();
                Employee employee = optional.get();
                list.remove(employee);
            }
            req.setAttribute("employeeList", list);
            req.getRequestDispatcher("/WEB-INF/views/employee/list.jsp").forward(req, resp);
        } else {
            req.setAttribute("employeeList", list);
            req.getRequestDispatcher("/WEB-INF/views/employee/list.jsp").forward(req, resp);
        }
    }
}
