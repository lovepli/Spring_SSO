package com.cjs.sso.service;

import com.alibaba.fastjson.JSON;
import com.cjs.sso.domain.MyUser;
import com.cjs.sso.entity.SysPermission;
import com.cjs.sso.entity.SysUser;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * @author ChengJianSheng
 * @date 2019-02-11
 *
 *
 *如下代码集成自spring security登陆验证方法
 */
@Slf4j
@Service
public class MyUserDetailsService implements UserDetailsService {

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private UserService userService;

    @Autowired
    private PermissionService permissionService;


    /**
     * 授权的过程。类似shiro的doGetAuthorizationInfo方法！！！
     * @param username
     * @return
     * @throws UsernameNotFoundException
     */
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        //从数据库查找用户
        SysUser sysUser = userService.getByUsername(username);
        if (null == sysUser) {
            log.warn("用户{}不存在", username);
            throw new UsernameNotFoundException(username);
        }
        //查询用户所有权限
        List<SysPermission> permissionList = permissionService.findByUserId(sysUser.getId());
        List<SimpleGrantedAuthority> authorityList = new ArrayList<>();
        if (!CollectionUtils.isEmpty(permissionList)) {
            for (SysPermission sysPermission : permissionList) {
                //将用户的所有权限加进权限框架管理
                authorityList.add(new SimpleGrantedAuthority(sysPermission.getCode()));
            }
        }
        //构造一个基本用户信息 包含用户名，加密后的密码，所有权限
        MyUser myUser = new MyUser(sysUser.getUsername(), passwordEncoder.encode(sysUser.getPassword()), authorityList);

        log.info("登录成功！用户: {}", JSON.toJSONString(myUser));

        return myUser;
    }
}
