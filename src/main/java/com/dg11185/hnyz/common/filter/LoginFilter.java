package com.dg11185.hnyz.common.filter;


import com.dg11185.hnyz.common.constant.SysConstant;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.HashSet;
import java.util.Set;

/**
 * 登陆过滤器
 */
public class LoginFilter implements Filter{

    private static final Set<String> loginSets = new HashSet<String>();

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        String urls = filterConfig.getInitParameter("loginUrls");
        String[] urlArr = urls.split(",");
        for(String url:urlArr){
            loginSets.add(url);
        }
    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        HttpServletRequest request=(HttpServletRequest)servletRequest;
        HttpServletResponse response  =(HttpServletResponse) servletResponse;
        HttpSession session = request.getSession();
        Object user = session.getAttribute(SysConstant.LOGIN_ADMIN);
        String path = request.getServletPath();
        System.out.println(path + "---" + (user == null));
/*        if(user == null){
            //如果是登陆地址,放行
            if(loginSets.contains(path)){
                filterChain.doFilter(servletRequest,servletResponse);
            }else{
                //否则,跳转到登陆页
                response.sendRedirect(request.getContextPath()+"/system/toLogin.do");
            }
        }else{
            String isClick = request.getParameter("click");
            if("true".equals(isClick)){
                request.getSession().setAttribute("clickMenu",path);
            }
            filterChain.doFilter(servletRequest,servletResponse);
        }*/
        filterChain.doFilter(servletRequest,servletResponse);
    }

    @Override
    public void destroy() {

    }
}
