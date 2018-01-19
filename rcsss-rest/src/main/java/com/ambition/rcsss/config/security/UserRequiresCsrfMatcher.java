package com.ambition.rcsss.config.security;

import java.util.Arrays;
import java.util.HashSet;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.security.web.util.matcher.RequestMatcher;

/**
 * 覆盖DefaultRequiresCsrfMatcher类
 * 提供自定义的域名过滤功能
 *
 * @author hhu
 * @version $Id: CsrfSecurityRequestMatcher.java, v 0.1 2017年5月16日 上午10:30:26 hhu Exp $
 */
public class UserRequiresCsrfMatcher implements RequestMatcher {
    private final HashSet<String> allowedMethods = new HashSet<String>(Arrays.asList("GET", "HEAD",
                                                     "TRACE", "OPTIONS"));

    @Override
    public boolean matches(HttpServletRequest request) {

        if (execludeUrls != null && execludeUrls.size() > 0) {
            String servletPath = request.getRequestURL().toString();
            for (String url : execludeUrls) {
                if (servletPath.contains(url)) {
                    return false;
                }
            }
        }
        return !this.allowedMethods.contains(request.getMethod());
    }

    /**
     * 需要排除的url列表
     */
    private List<String> execludeUrls;

    public List<String> getExecludeUrls() {
        return execludeUrls;
    }

    public void setExecludeUrls(List<String> execludeUrls) {
        this.execludeUrls = execludeUrls;
    }
}