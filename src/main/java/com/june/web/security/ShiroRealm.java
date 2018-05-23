package com.june.web.security;

import java.util.Objects;

import javax.annotation.Resource;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.IncorrectCredentialsException;
import org.apache.shiro.authc.SimpleAuthenticationInfo;
import org.apache.shiro.authc.UnknownAccountException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.springframework.stereotype.Component;

import com.june.model.Blogger;
import com.june.service.BloggerService;
import com.june.util.Constants;

@Component
public class ShiroRealm extends AuthorizingRealm {
	
	@Resource
	private BloggerService bloggerService;

	/**
	 * 认证，登录时调用
	 */
	@Override
	protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken authenticToken) 
			throws AuthenticationException {
		
		UsernamePasswordToken token = (UsernamePasswordToken) authenticToken;
		String userName = token.getUsername();
		String password = new String(token.getPassword());
		Blogger blogger = bloggerService.findByName(userName);
		if (Objects.isNull(blogger)) {
			throw new UnknownAccountException();
        } 
		if (!password.equals(blogger.getPassword())) {
			throw new IncorrectCredentialsException();
		}
		SecurityUtils.getSubject().getSession().setAttribute(Constants.CURRENT_USER, blogger);
		return new SimpleAuthenticationInfo(userName, password, getName());
	}

	/**
	 *
	 * 授权，权限验证时调用
	 *
	 */
	@Override
	protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principals) {
		return null;
	}
	
}
