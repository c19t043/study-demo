package cn.cjf.util;

import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;

import java.security.Principal;

public class SecurityUtil {
    /*
    此外，调用 SecurityContextHolder.getContext() 获取 SecurityContext 时，
    如果对应的 SecurityContext 不存在，则 Spring Security 将为我们建立一个空的 SecurityContext 并进行返回。
     */
    /**
     * 在程序的任何地方，通过如下方式我们可以获取到当前用户的用户名
     * @return
     */
    public static String getCurrentUsername() {
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        if (principal instanceof UserDetails) {
            return ((UserDetails) principal).getUsername();
        }
        if (principal instanceof Principal) {
            return ((Principal) principal).getName();
        }
        return String.valueOf(principal);
    }
    /**
     * 通过 Authentication.getPrincipal() 可以获取到代表当前用户的信息，这个对象通常是 UserDetails 的实例。获取当前用户的用户名是一种比较常见的需求，关于上述代码其实 Spring Security 在 Authentication 中的实现类中已经为我们做了相关实现，所以获取当前用户的用户名最简单的方式应当如下。
     */
    public static String getCurrentUsername1() {
        return SecurityContextHolder.getContext().getAuthentication().getName();
    }
}
