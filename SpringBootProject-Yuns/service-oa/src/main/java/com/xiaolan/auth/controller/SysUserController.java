package com.xiaolan.auth.controller;


import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.xiaolan.auth.service.SysUserService;
import com.xiaolan.common.result.Result;
import com.xiaolan.model.system.SysUser;
import com.xiaolan.vo.system.SysUserQueryVo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

/**
 * <p>
 * 用户表 前端控制器
 * </p>
 *
 * @author atguigu
 * @since 2023-03-22
 */
@Api("用户管理接口")
@RestController
@RequestMapping("/admin/system/sysUser")
public class SysUserController {

    @Autowired
    SysUserService service;

    @ApiOperation("用户状态切换")
    @GetMapping("updateStatus/{id}/{status}")
    public Result updateStatus(@PathVariable Long id, @PathVariable Integer status) {
        service.updateStatus(id, status);
        return Result.ok();
    }

    @ApiOperation("用户条件分页查询")
    @GetMapping("{page}/{limit}")
    public Result index(@PathVariable int page,
                        @PathVariable int limit,
                        SysUserQueryVo sysUserQueryVo
                        ){
        Page<SysUser> pagePrarm = new Page<>(page,limit);
        //判断 是否为空
        String username = sysUserQueryVo.getKeyword();
        String createTimeBegin = sysUserQueryVo.getCreateTimeBegin();
        String createTimeEnd = sysUserQueryVo.getCreateTimeEnd();
        LambdaQueryWrapper<SysUser> Wrapper = new LambdaQueryWrapper<>();
        if(!StringUtils.isEmpty(username)){
            Wrapper.like(SysUser::getUsername,username);
        }
        if(!StringUtils.isEmpty(createTimeBegin)){
            Wrapper.ge(SysUser::getCreateTime,createTimeBegin);
        }
        if(!StringUtils.isEmpty(createTimeEnd)){
            Wrapper.le(SysUser::getCreateTime,createTimeEnd);
        }

        Page<SysUser> page1 = service.page(pagePrarm, Wrapper);
        return Result.ok(page1);
    }

    @ApiOperation(value = "获取用户")
    @GetMapping("get/{id}")
    public Result get(@PathVariable Long id) {
        SysUser user = service.getById(id);
        return Result.ok(user);
    }

    @ApiOperation(value = "保存用户")
    @PostMapping("save")
    public Result save(@RequestBody SysUser user) {
        service.SaveUser(user);
        return Result.ok();
    }

    @ApiOperation(value = "更新用户")
    @PutMapping("update")
    public Result updateById(@RequestBody SysUser user) {
        service.updateById(user);
        return Result.ok();
    }

    @ApiOperation(value = "删除用户")
    @DeleteMapping("remove/{id}")
    public Result remove(@PathVariable Long id) {
        service.removeById(id);
        return Result.ok();
    }
}

