package cn.cjf.shiro;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.config.IniSecurityManagerFactory;
import org.apache.shiro.mgt.SecurityManager;
import org.apache.shiro.subject.Subject;
import org.apache.shiro.util.Factory;
import org.apache.shiro.web.filter.authc.AnonymousFilter;
import org.junit.Assert;
import org.junit.Test;

import java.util.Arrays;

/**
 * 测试shiro认证
 */
public class ShiroTest {

    @Test
    public void testAuthorized() throws Exception {
        // 创建SecurityManager
        Factory<SecurityManager> factory = new IniSecurityManagerFactory("classpath:shiro-authorized.ini");
        // 通过工厂对象创建SecurityManager对象
        SecurityManager securityManager = factory.getInstance();
        // 将SecurityManager绑定到当前环境中
        SecurityUtils.setSecurityManager(securityManager);

        // 创建当前登录的主体
        Subject subject = SecurityUtils.getSubject();

        // 收集主体登录的身份/凭证，即账户密码
        UsernamePasswordToken token =
                new UsernamePasswordToken("zhangsan", "666");
        try {
            // 主体登录
            subject.login(token);
        } catch (AuthenticationException e) {
//            System.out.println("未知账户异常");
            e.printStackTrace();
        }
        // 进行授权操作时，用户必选通过认证
        Assert.assertTrue(subject.hasRole("role1"));

        // 判断当前用户是否拥有某一个权限,返回true，表示拥有该权限，false表示没有
        Assert.assertTrue(subject.isPermitted("user:delete"));


    }

    @Test
    public void testHasPermission() throws Exception {
        // 创建SecurityManager
        Factory<SecurityManager> factory = new IniSecurityManagerFactory("classpath:shiro-permission.ini");
        // 通过工厂对象创建SecurityManager对象
        SecurityManager securityManager = factory.getInstance();
        // 将SecurityManager绑定到当前环境中
        SecurityUtils.setSecurityManager(securityManager);

        // 创建当前登录的主体
        Subject subject = SecurityUtils.getSubject();

        // 收集主体登录的身份/凭证，即账户密码
        UsernamePasswordToken token =
                new UsernamePasswordToken("zhangsan", "666");
        try {
            // 主体登录
            subject.login(token);
        } catch (AuthenticationException e) {
//            System.out.println("未知账户异常");
            e.printStackTrace();
        }

        // 进行授权操作时，用户必选通过认证
        // 判断当前用户是否拥有某一个权限,返回true，表示拥有该权限，false表示没有
        Assert.assertTrue(subject.isPermitted("user:delete"));
        // 判断用户是否拥有一些权限,true表示都拥有，false表示不全部拥有
        Assert.assertTrue(subject.isPermittedAll("user:delete","user:create"));
        // 判断用户是否拥有每个权限,分别返回结果
        boolean[] permitted = subject.isPermitted("user:create", "user:delete");
        Assert.assertTrue(permitted[0]);
        Assert.assertTrue(permitted[1]);

        // 判断用户是否拥有某个权限，如果有，不做任何操作，没有抛出异常UnAuthorizedException
        subject.checkPermission("user:create");

    }

    @Test
    public void testHasRole() throws Exception {
        // 创建SecurityManager
        Factory<SecurityManager> factory = new IniSecurityManagerFactory("classpath:shiro-permission.ini");
        // 通过工厂对象创建SecurityManager对象
        SecurityManager securityManager = factory.getInstance();
        // 将SecurityManager绑定到当前环境中
        SecurityUtils.setSecurityManager(securityManager);

        // 创建当前登录的主体
        Subject subject = SecurityUtils.getSubject();

        // 收集主体登录的身份/凭证，即账户密码
        UsernamePasswordToken token =
                new UsernamePasswordToken("zhangsan", "666");
        try {
            // 主体登录
            subject.login(token);
        } catch (AuthenticationException e) {
//            System.out.println("未知账户异常");
            e.printStackTrace();
        }

        // 判断当前用户是否拥有某个角色，返回true拥有，否则false
        Assert.assertTrue(subject.hasRole("role1"));
        // 判断拥有多个角色,全部拥有true，有一个不拥有返回false
        Assert.assertTrue(subject.hasAllRoles(Arrays.asList("role1", "role2")));

        // 单独返回每个角色是否拥有
        boolean[] booleans = subject.hasRoles(Arrays.asList("role1", "role3"));
        Assert.assertTrue(booleans[0]);
        Assert.assertFalse(booleans[1]);

        // 判断当前用户是否拥有角色，如果拥有，不做任何操作，没有UnAuthorizedException
        subject.checkRole("role1");

        // 判断当用是否拥有一些角色
        subject.checkRoles("role1", "role2");
    }

    @Test
    public void testLoginByMyPasswordRealm() throws Exception {
        // 创建SecurityManager
        Factory<SecurityManager> factory = new IniSecurityManagerFactory("classpath:shiro-cryptography.ini");
        // 通过工厂对象创建SecurityManager对象
        SecurityManager securityManager = factory.getInstance();
        // 将SecurityManager绑定到当前环境中
        SecurityUtils.setSecurityManager(securityManager);

        // 创建当前登录的主体
        Subject subject = SecurityUtils.getSubject();

        // 收集主体登录的身份/凭证，即账户密码
        UsernamePasswordToken token =
                new UsernamePasswordToken("zhangsan", "666");
        try {
            // 主体登录
            subject.login(token);
        } catch (AuthenticationException e) {
//            System.out.println("未知账户异常");
            e.printStackTrace();
        }

        // 判断登录是否成功
        Assert.assertTrue(subject.isAuthenticated());

        // 判断是否登出
        subject.logout();
        Assert.assertFalse(subject.isAuthenticated());
    }

    @Test
    public void testLoginByMyRealm() throws Exception {
        // 创建SecurityManager
        Factory<SecurityManager> factory = new IniSecurityManagerFactory("classpath:shiro-realm.ini");
        // 通过工厂对象创建SecurityManager对象
        SecurityManager securityManager = factory.getInstance();
        // 将SecurityManager绑定到当前环境中
        SecurityUtils.setSecurityManager(securityManager);

        // 创建当前登录的主体
        Subject subject = SecurityUtils.getSubject();

        // 收集主体登录的身份/凭证，即账户密码
        UsernamePasswordToken token =
                new UsernamePasswordToken("zhangsan", "666");
        try {
            // 主体登录
            subject.login(token);
        } catch (AuthenticationException e) {
//            System.out.println("未知账户异常");
            e.printStackTrace();
        }

        // 判断登录是否成功
        Assert.assertTrue(subject.isAuthenticated());

        // 判断是否登出
        subject.logout();
        Assert.assertFalse(subject.isAuthenticated());
    }

    @Test
    public void testLogin() throws Exception {
        // 创建SecurityManager
        Factory<SecurityManager> factory = new IniSecurityManagerFactory("classpath:shiro.ini");
        // 通过工厂对象创建SecurityManager对象
        SecurityManager securityManager = factory.getInstance();
        // 将SecurityManager绑定到当前环境中
        SecurityUtils.setSecurityManager(securityManager);

        // 创建当前登录的主体
        Subject subject = SecurityUtils.getSubject();

        // 收集主体登录的身份/凭证，即账户密码
        UsernamePasswordToken token =
                new UsernamePasswordToken("zhangsan", "666");
        try {
            // 主体登录
            subject.login(token);
        } catch (AuthenticationException e) {
//            System.out.println("未知账户异常");
            e.printStackTrace();
        }

        // 判断登录是否成功
        Assert.assertTrue(subject.isAuthenticated());

        // 判断是否登出
        subject.logout();
        Assert.assertFalse(subject.isAuthenticated());
    }
}
