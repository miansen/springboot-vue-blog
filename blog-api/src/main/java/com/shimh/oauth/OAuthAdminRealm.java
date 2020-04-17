package com.shimh.oauth;

import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.SimpleAuthenticationInfo;
import org.apache.shiro.authc.UnknownAccountException;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;

import org.springframework.beans.factory.annotation.Autowired;

import com.shimh.common.constant.Base;
import com.shimh.entity.AdminUser;
import com.shimh.service.AdminUserService;

/**
 * 处理后台用户登录的 Realm
 * 
 * @author miansen.wang
 * @date 2020-04-17
 */
public class OAuthAdminRealm extends AuthorizingRealm {

	@Autowired
	private AdminUserService adminUserService;
	
	@Override
	protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principals) {
		SimpleAuthorizationInfo info = new SimpleAuthorizationInfo();
		// 简单的添加一个 admin 角色
		info.addRole(Base.ROLE_ADMIN);
		return info;
	}

	@Override
	protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken token) throws AuthenticationException {
		String username = (String) token.getPrincipal();
		AdminUser adminUser = adminUserService.getByUsername(username);
		if (adminUser == null) {
			throw new UnknownAccountException();
		}
		return new SimpleAuthenticationInfo(adminUser, adminUser.getPassword(), getName());
	}

}
