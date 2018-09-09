package com.simon.basics.filter;

import com.simon.basics.model.vo.ReturnParam;
import com.simon.basics.util.JSONUtil;
import org.apache.shiro.subject.Subject;
import org.apache.shiro.web.filter.authz.AuthorizationFilter;
import org.apache.shiro.web.filter.authz.RolesAuthorizationFilter;

import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

/**
 * 自定义角色拦截
 * @author fengtianying
 * @date 2018/9/7 14:17
 */
public class CustomRolesAuthorizationFilter extends RolesAuthorizationFilter {
    @Override
    public boolean isAccessAllowed(ServletRequest servletRequest, ServletResponse servletResponse, Object mappedValue) throws IOException {
        Subject subject = getSubject(servletRequest, servletResponse);
        String[] rolesArray = (String[]) mappedValue;
        if (rolesArray == null || rolesArray.length == 0) {
            return true;
        }
        for (String aRolesArray : rolesArray) {
            if (subject.hasRole(aRolesArray)) {
                return true;
            }
        }
        //设置响应头
        servletResponse.setCharacterEncoding("UTF-8");
        servletResponse.setContentType("application/json");
        PrintWriter out = servletResponse.getWriter();
        //写回给客户端
        out.write(JSONUtil.objectToJson(ReturnParam.sessionOverdue()));
        //刷新和关闭输出流
        out.flush();
        out.close();
        return false;
    }
}
