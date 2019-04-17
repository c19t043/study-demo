package cn.cjf.shiro;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.config.IniSecurityManagerFactory;
import org.apache.shiro.mgt.SecurityManager;
import org.apache.shiro.subject.Subject;
import org.apache.shiro.util.Factory;
import org.junit.Assert;
import org.junit.Test;

/**
 * 测试shiro认证
 */
public class ShiroTest {
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
