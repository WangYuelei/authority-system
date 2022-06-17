package com.wyl.dao;

import com.wyl.entity.Department;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;

import java.util.List;
import java.util.Map;

public interface DepartmentMapper extends BaseMapper<Department> {

    List<Department> selectList(Map params);
}
