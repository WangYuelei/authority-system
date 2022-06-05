package com.wyl.service.impl;

import com.wyl.entity.Permission;
import com.wyl.dao.PermissionMapper;
import com.wyl.service.PermissionService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class PermissionServiceImpl extends ServiceImpl<PermissionMapper, Permission> implements PermissionService {

    @Override
    public List<Permission> findPermissionByUserId(Long userId) {
        return baseMapper.findPermissionByUserId(userId);
    }
}
