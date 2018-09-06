package com.simon.basics.shiro;

import com.simon.basics.dao.UserMapper;
import com.simon.basics.model.User;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.*;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.crypto.hash.SimpleHash;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.session.InvalidSessionException;
import org.apache.shiro.session.Session;
import org.apache.shiro.subject.PrincipalCollection;
import org.apache.shiro.subject.Subject;
import org.apache.shiro.util.ByteSource;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * @author fengtianying
 * @date 2018/9/3 15:16
 */
public class AuthRealm extends AuthorizingRealm {

    @Autowired
    private UserMapper userMapper;
    //认证.登录
    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken authenticationToken) throws AuthenticationException {
        System.out.println("登陆验证");
        //查询sql
        UsernamePasswordToken token = (UsernamePasswordToken) authenticationToken;
        String userName = token.getUsername();
        User user = userMapper.selectByPrimaryKey(Integer.parseInt("1"));
        //验证密码
        AuthenticationInfo authcInfo = new SimpleAuthenticationInfo(userName, user.getPassword().toCharArray(), ByteSource.Util.bytes(userName),this.getName());
//        UsernamePasswordToken utoken=(UsernamePasswordToken) token;//获取用户输入的token
//        String username = utoken.getUsername();
//        User user = userService.findUserByUserName(username);
//        return new SimpleAuthenticationInfo(user, user.getPassword(),this.getClass().getName());//放入shiro.调用CredentialsMatcher检验密码
//        this.setSession();
        return authcInfo;
    }
    //授权
    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principal) {
        System.out.println("授权");
        User user=(User) principal.fromRealm(this.getClass().getName()).iterator().next();//获取session中的用户
//        List<String> permissions=new ArrayList<>();
//        Set<Role> roles = user.getRoles();
//        if(roles.size()>0) {
//            for(Role role : roles) {
//                Set<Module> modules = role.getModules();
//                if(modules.size()>0) {
//                    for(Module module : modules) {
//                        permissions.add(module.getMname());
//                    }
//                }
//            }
//        }
//        SimpleAuthorizationInfo info=new SimpleAuthorizationInfo();
//        info.addStringPermissions(permissions);//将权限放入shiro中.
        return null;
    }

    /**
     * 保存登录名
     */
    private void setSession(Object key, Object value){
        Session session = getSession();
        System.out.println("Session默认超时时间为[" + session.getTimeout() + "]毫秒");
        if(null != session){
            session.setAttribute(key, value);
        }
    }
    private Session getSession(){
        try{
            Subject subject = SecurityUtils.getSubject();
            Session session = subject.getSession(false);
            if (session == null){
                session = subject.getSession();
            }
            if (session != null){
                return session;
            }
        }catch (InvalidSessionException e){

        }
        return null;
    }
    public static void main(String[] args) {
        int hashIterations1 = 2;//加密的次数
        Object salt = "simon";//盐值
        Object credentials = "123".toCharArray();//密码
        String hashAlgorithmName = "MD5";//加密方式
        Object simpleHash = new SimpleHash(hashAlgorithmName, credentials,
                salt, hashIterations1);
        System.out.println("加密后的值----->" + simpleHash);//ea86bb6ddec9c18cd45e762d1d3495e9
        //ea86bb6ddec9c18cd45e762d1d3495e9 c2ltb24=
        System.out.println(new SimpleHash("SHA-256", credentials, ByteSource.Util.bytes(salt), hashIterations1));//86016a1bdbac2f4e488e0a201fb31409
    }
}
