package com.busience.common.security;

import javax.servlet.http.HttpSessionEvent;
import javax.servlet.http.HttpSessionListener;

public class SessionListener implements HttpSessionListener{
	
	@Override
	public void sessionCreated(HttpSessionEvent httpSessionEvent) {

		httpSessionEvent.getSession().setMaxInactiveInterval(60*60); //1시간
	}

	@Override
    public void sessionDestroyed(HttpSessionEvent httpSessionEvent) {
    }
}
