package cn.cjf.shiro.realm;

import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.SimpleAuthenticationInfo;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;

import java.util.ArrayList;
import java.util.List;

/**
 * @author chenjunfan
 * @date 2019/4/17
 */
public class MyAuthorizedRealm extends AuthorizingRealm {
    @Override
    public String getName() {
        return "MyAuthorizedRealm";
    }

    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principalCollection) {

        //传入参数：principalCollection 用户认证凭证信息（）
        //SimpleAuthenticationInfo: 认证方法返回封装认证信息中第一个参数：用户信息（username）

        // 当前登录用户名信息：用户凭证
        String username = (String) principalCollection.getPrimaryPrincipal();

        // 模拟查询数据库：查询用户实现指定的角色，以及用户权限
        List<String> roles = new ArrayList<>();
        // 假设用户再数据库中拥有role1角色
        roles.add("role1");

        List<String> permission = new ArrayList<>();
        //假设用户再数据库中用户user:delete权限
        permission.add("user:*");

        // 返回用户再数据库中的权限与角色
        SimpleAuthorizationInfo info = new SimpleAuthorizationInfo();
        info.addRoles(roles);
        info.addStringPermissions(permission);

        return info;
    }


    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken authenticationToken) throws AuthenticationException {
        // 参数token，表示登录时封装的UsernamePasswordToken

        // 通过用户名查找用户信息，封装AuthenticationInfo对象
        String username = (String) authenticationToken.getPrincipal();

        // 通过用户名查询数据库，将用户名对应的数据查询返回：账户与密码
        if (!"zhangsan".equals(username)) {
            return null;
        }

        // 假设查询的密码是666
        String passworkd = "666";

        /*
         info对象表示realm登录比对信息,
         参数1：用户信息，
         参数2：密码
         参数3：当前realm的名字
          */
        SimpleAuthenticationInfo info =
                new SimpleAuthenticationInfo(username, passworkd, getName());

        return info;
    }
}
