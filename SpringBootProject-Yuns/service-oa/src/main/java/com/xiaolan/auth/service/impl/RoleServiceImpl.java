package com.xiaolan.auth.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.xiaolan.auth.mapper.SysRoleMapper;
import com.xiaolan.auth.service.RoleService;
import com.xiaolan.auth.service.SysUserRoleService;
import com.xiaolan.model.system.SysRole;
import com.xiaolan.model.system.SysUserRole;
import com.xiaolan.vo.system.AssginRoleVo;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;
@Service("roleServiceImpl")
public class RoleServiceImpl extends ServiceImpl<SysRoleMapper, SysRole> implements RoleService {

    //角色分配业务
    //显示所有角色
    //根据用户ID查询所属角色
        //根据用户ID查询角色ID where userID
        //在角色表里 where {查到的roleID}
    @Resource
    SysUserRoleService service;
    public List<SysRole> findRoleByAdminId(Long userId){
        //*所有角色信息
        List<SysRole> allRolesList = baseMapper.selectList(null);

        //*根据用户ID查询角色ID
        //**条件查询
        LambdaQueryWrapper<SysUserRole> wrapper = new LambdaQueryWrapper();
        //相当于   where UserID = userId
        wrapper.eq(SysUserRole::getUserId,userId);
        //通过条件查询 获取的UserRolelist UserID对应的所有数据
        List<SysUserRole> UserRolelist = service.list(wrapper);
        //找到UserRolelist UserId对应的RoleID 遍历获取每一个RoleID
        List<Long> RoleIdList = new ArrayList();
        for (SysUserRole userRole:UserRolelist) {
            RoleIdList.add(userRole.getRoleId());
        }
        //在角色列表 找到RoleId所对应的字段
        List<SysRole> assginRoleList = new ArrayList<>();
        for (SysRole roleId:allRolesList) {
            if(RoleIdList.contains(roleId.getId())){
                assginRoleList.add(roleId);
            }
        }
        return assginRoleList;

    }

    //*分配接口2---当用户点确定后
    //**删除原来用户对应的角色
    //**重新进行分配
    public void doAssign(AssginRoleVo assginRoleVo) {
        //找到对应的用户ID 删除 where userid=userid
        LambdaQueryWrapper<SysUserRole> wrapper = new LambdaQueryWrapper();
        wrapper.eq(SysUserRole::getUserId,assginRoleVo.getUserId());
        boolean remove = service.remove(wrapper);
        //*为用户重新分配角色
        //**添加 UserID 和 对应角色ID
        //**循环获得每一个对应角色 并新建对象 save进去
        for (Long id:assginRoleVo.getRoleIdList()) {
            SysUserRole userRole = new SysUserRole();
            userRole.setUserId(assginRoleVo.getUserId());
            userRole.setRoleId(id);
            service.save(userRole);
        }
    }
}
