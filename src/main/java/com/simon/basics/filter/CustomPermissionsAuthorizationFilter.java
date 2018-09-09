package com.simon.basics.filter;

import org.apache.shiro.subject.Subject;
import org.apache.shiro.web.filter.authz.PermissionsAuthorizationFilter;

import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import java.io.IOException;

/**
 * 自定义权限拦截
 * @Author simon.feng
 *
 */
public class CustomPermissionsAuthorizationFilter extends PermissionsAuthorizationFilter {

    @Override
    public boolean isAccessAllowed(ServletRequest request, ServletResponse response, Object mappedValue) throws IOException {
        String[] perms = (String[]) mappedValue;
        Subject subject = getSubject(request, response);
        boolean isPermitted = false;
        if (perms != null && perms.length > 0) {
            for (String str : perms) {
                if (subject.isPermitted(str)) {
                    isPermitted = true;
                }
            }
        }
        return isPermitted;
    }
}
