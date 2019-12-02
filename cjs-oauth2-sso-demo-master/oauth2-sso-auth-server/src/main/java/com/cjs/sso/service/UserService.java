package com.cjs.sso.service;

import com.cjs.sso.entity.SysUser;

/**
 * @author ChengJianSheng
 * @date 2019-02-12
 */
public interface UserService {

    /**
     * 通过用户名查找用户
     * @param username
     * @return
     */
    SysUser getByUsername(String username);
}
