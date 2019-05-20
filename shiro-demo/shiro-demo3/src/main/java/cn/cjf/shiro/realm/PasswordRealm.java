package cn.cjf.shiro.realm;

import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.SimpleAuthenticationInfo;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.crypto.hash.Md5Hash;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.apache.shiro.util.ByteSource;

public class PasswordRealm extends AuthorizingRealm {
    @Override
    public String getName() {
        return "passwordRealm";
    }

    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principalCollection) {
        return null;
    }

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

        // 模拟数据库中加密之后的密文：密码+账户(盐)+散列次数
        Md5Hash md5Hash = new Md5Hash(passworkd, username,3);
        passworkd = md5Hash.toString();

        /*
         info对象表示realm登录比对信息,
         参数1：用户信息，
         参数2：密码
         参数3：盐
         参数4：当前realm的名字
          */
        SimpleAuthenticationInfo info =
                new SimpleAuthenticationInfo(username, passworkd, ByteSource.Util.bytes(username), getName());

        return info;
    }
}
