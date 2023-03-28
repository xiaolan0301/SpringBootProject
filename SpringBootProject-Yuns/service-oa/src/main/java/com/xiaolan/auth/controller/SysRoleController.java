package com.xiaolan.auth.controller;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.xiaolan.auth.service.RoleService;
import com.xiaolan.common.result.Result;
import com.xiaolan.model.system.SysRole;
import com.xiaolan.vo.system.AssginRoleVo;
import com.xiaolan.vo.system.SysRoleQueryVo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.Map;

@Api("角色管理")
@RestController
@RequestMapping("/admin/system/sysRole")
public class SysRoleController {

    @Autowired
    RoleService service;


    @ApiOperation(value = "根据用户获取角色数据")
    @GetMapping("/toAssign/{userId}")
    public Result toAssign(@PathVariable Long userId) {
        List<SysRole> list = service.findRoleByAdminId(userId);
        return Result.ok(list);
    }

    @ApiOperation("用户分配角色")
    @PostMapping("/doAssign")
    public Result doAssign(@RequestBody AssginRoleVo assginRoleVo) {
        service.doAssign(assginRoleVo);
        return Result.ok();
    }




    /*
    查询所有管理角色
     */
   /* @GetMapping("/findAll")
    public List<SysRole> findAll(){
        List<SysRole> list = service.list();
        return list;
    }*/
    @ApiOperation("查询所有角色")
    @GetMapping("/findAll")
    public Result<List<SysRole>> findAll(){
        List<SysRole> list = service.list();
        return Result.ok(list);
    }

    @PreAuthorize("hasAuthority('bnt.sysRole.list')")
    @ApiOperation("条件查询分页")
    @GetMapping("/{page}/{limit}")
    public Result pageQueryRole(@PathVariable Long page,
                         @PathVariable Long limit,
                         SysRoleQueryVo sysRoleQueryVo){
        //调用service的方法实现
        //1 创建Page对象，传递分页相关参数
        //page 当前页  limit 每页显示记录数
        Page<SysRole> page1 = new Page<>(page,limit);

        //2 封装条件，判断条件是否为空，不为空进行封装
        LambdaQueryWrapper<SysRole> wrapper = new LambdaQueryWrapper<>();
        String roleName = sysRoleQueryVo.getRoleName();
        if(!StringUtils.isEmpty(roleName)) {
            //封装 like模糊查询
            wrapper.like(SysRole::getRoleName,roleName);
        }

        //3 调用方法实现
        IPage<SysRole> pageModel = service.page(page1,wrapper);
        return Result.ok(pageModel);

    }

    //根据ID删除
    @PreAuthorize("hasAuthority('bnt.sysRole.remove')")
    @ApiOperation("根据ID删除")
    @DeleteMapping("/byIdDelete/{id}")
    public Result byIdDelete(@PathVariable Long id){
        boolean res = service.removeById(id);
        if(res){
            Result<Object> ok = Result.ok();
            return ok;
        }else{
            return Result.fail();
        }
    }

    //批量删除
    @PreAuthorize("hasAuthority('bnt.sysRole.remove')")
    @ApiOperation("批量删除")
    @DeleteMapping("DeleteOF")
    public Result DeleteOF(@RequestBody List<Long> ids){
        boolean res = service.removeByIds(ids);;
        if(res){
            Result<Object> ok = Result.ok();
            return ok;
        }else{
            return Result.fail();
        }
    }

    //根据ID查询
    @PreAuthorize("hasAuthority('bnt.sysRole.list')")
    @ApiOperation("根据ID查询")
    @GetMapping("/get/{id}")
    Result byIdSelect(@PathVariable Long id){
        SysRole byId = service.getById(id);
        if(byId!=null){
            return Result.ok(byId);
        }else{
            return Result.fail();
        }
    }

    //添加角色

    @PreAuthorize("hasAuthority('bnt.sysRole.add')")
    @ApiOperation("添加角色")
    @PostMapping ("/AddUser")
    public Result AddUser(@RequestBody SysRole sysRole){
        boolean save = service.save(sysRole);
        if(save){
            Result<Object> ok = Result.ok();
            return ok;
        }else{
            return Result.fail();
        }
    }

    //修改角色
    @PreAuthorize("hasAuthority('bnt.sysRole.update')")
    @ApiOperation("修改角色")
    @PutMapping("/update")
    public Result UpdateById(@RequestBody SysRole role){
        boolean b = service.updateById(role);
        if(b){
            Result<Object> ok = Result.ok();
            return ok;
        }else{
            return Result.fail();
        }
    }


}
