package com.etc.newmoudle.Filter;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.core.annotation.Order;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import java.io.IOException;

@Order(1)
//重点
@WebFilter(filterName = "testFilter1", urlPatterns = "/*")
public class SetCharacterEncodingFilter implements Filter {

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {

    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response,
                         FilterChain filterChain) throws IOException, ServletException {
        if (request.getCharacterEncoding() == null) {
            request.setCharacterEncoding("UTF-8");

            System.out.println("所有输出的文件必须是UTF8格式");
        }
        filterChain.doFilter(request, response);
    }

    @Override
    public void destroy() {

    }

}
