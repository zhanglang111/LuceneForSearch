//package com.etc.newmoudle.Filter;
//
//
//import org.springframework.core.annotation.Order;
//
//import javax.servlet.*;
//import javax.servlet.annotation.WebFilter;
//import java.io.IOException;
//
//@Order(2)
//@WebFilter(filterName="secondFilter", urlPatterns="/*")
//public class SecondFilter implements Filter {
//
//    @Override
//    public void init(FilterConfig filterConfig) throws ServletException {
//
//    }
//
//    @Override
//    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
//            throws IOException, ServletException {
//        System.out.println("second filter 1");
//        System.out.println("before:" + response);
//        chain.doFilter(request, response);
//        System.out.println("after:" + response);
//        System.out.println("second filter 2");
//
//    }
//
//    @Override
//    public void destroy() {
//
//    }
//}
