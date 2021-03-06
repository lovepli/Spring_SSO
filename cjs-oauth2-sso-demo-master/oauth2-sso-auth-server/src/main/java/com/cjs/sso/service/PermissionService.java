package com.cjs.sso.service;

import com.cjs.sso.entity.SysPermission;

import java.util.List;

/**
 * @author ChengJianSheng
 * @date 2019-02-12
 */
public interface PermissionService {

    /**
     * 通过用户id查找用户所有的权限
     * @param userId
     * @return
     */
    List<SysPermission> findByUserId(Integer userId);

}
