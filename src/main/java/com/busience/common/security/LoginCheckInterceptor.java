package com.busience.common.security;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.util.ObjectUtils;
import org.springframework.web.servlet.HandlerInterceptor;

@Component
public class LoginCheckInterceptor implements HandlerInterceptor{
	
	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
		//principal 값이 없다면 로그인 페이지로 이동
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		
		if(ObjectUtils.isEmpty(authentication)) {
			response.sendRedirect("/");
			return false;
		}else{
			return true;
		}
	}
}
