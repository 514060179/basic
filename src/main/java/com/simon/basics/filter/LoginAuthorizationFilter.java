package com.simon.basics.filter;

import com.simon.basics.model.vo.ReturnParam;
import com.simon.basics.util.JSONUtil;
import org.apache.shiro.subject.Subject;
import org.apache.shiro.web.filter.authc.FormAuthenticationFilter;

import javax.servlet.*;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

/**
 * @author fengtianying
 * @date 2018/9/4 13:16
 */
public class LoginAuthorizationFilter extends FormAuthenticationFilter {


    /**
     * 这个方法是未登录需要执行的方法
     */
    @Override
    protected boolean onAccessDenied(ServletRequest request,
                                     ServletResponse response) throws IOException {

        HttpServletResponse httpResponse = (HttpServletResponse) response;

        Subject subject = getSubject(request, response);
        //设置响应头
        httpResponse.setCharacterEncoding("UTF-8");
        httpResponse.setContentType("application/json");
        PrintWriter out = httpResponse.getWriter();

        if (subject.getPrincipal() == null) {
            //写回给客户端
            out.write(JSONUtil.objectToJson(ReturnParam.sessionOverdue()));
            //刷新和关闭输出流
            out.flush();
            out.close();
        } else {
            //写回给客户端
            out.write(JSONUtil.objectToJson(ReturnParam.sessionOverdue()));
            //刷新和关闭输出流
            out.flush();
            out.close();
        }
        return false;
    }

}
