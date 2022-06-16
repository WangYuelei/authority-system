package com.wyl.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.wyl.entity.Department;
import com.wyl.vo.query.DepartmentQueryVo;

import java.util.List;

public interface DepartmentService extends IService<Department> {
    /**
     *查询部门列表
     * @param departmentQueryVo
     * @return
     */
    List<Department> findDepartmentList(DepartmentQueryVo departmentQueryVo);

    /**
     * 查询上级部门列表
     * @return
     */
    List<Department> findParentDepartment();

    boolean check(Long id);

    /**
     * 判断部门下是否有子部门
     * @param id
     * @return
     */
    boolean hasChildrenOfDepartment(Long id);
    /**
     * 判断部门下是否有用户
     * @param id
     * @return
     */
    boolean hasUserOfDepartment(Long id);
}
