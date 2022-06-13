package com.gabrisal.api.common.filter;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;

@Slf4j
@Component
public class XSSFilter implements Filter {
    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        log.info("********* filter init **********");
        Filter.super.init(filterConfig);
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        log.info("********* filter doFilter **********");
        HttpServletRequest httpRequest = (HttpServletRequest) request;
        if (httpRequest.getMethod().equalsIgnoreCase("POST")
                && httpRequest.getContentType().startsWith("multipart/form-data")) {
           chain.doFilter(request, response);
        } else {
            chain.doFilter(new XSSRequestWrapper((HttpServletRequest) request), response);
        }
    }

    @Override
    public void destroy() {
        log.info("********* filter destroy **********");
        Filter.super.destroy();
    }
}
