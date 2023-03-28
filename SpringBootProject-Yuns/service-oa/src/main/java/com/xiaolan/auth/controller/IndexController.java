package com.xiaolan.auth.controller;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.xiaolan.auth.service.SysMenuService;
import com.xiaolan.auth.service.SysUserService;
import com.xiaolan.common.exception.XiaoLException;
import com.xiaolan.common.jwt.JwtHelper;
import com.xiaolan.common.result.Result;
import com.xiaolan.common.util.MD5;
import com.xiaolan.model.system.SysLoginLog;
import com.xiaolan.model.system.SysMenu;
import com.xiaolan.model.system.SysUser;
import com.xiaolan.vo.system.LoginVo;
import com.xiaolan.vo.system.RouterVo;
import io.swagger.annotations.Api;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpRequest;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Api("后台登录管理")
@RestController
@RequestMapping("/admin/system/index")
public class IndexController {
    
    @Autowired
    SysUserService service;

    @Autowired
    SysMenuService sysMenuService;
    /**
     * 登录
     * @return
     */
    @PostMapping("login")
    public Result login(@RequestBody LoginVo loginVo) {
        LambdaQueryWrapper<SysUser> wrapper = new LambdaQueryWrapper();
        wrapper.eq(SysUser::getUsername,loginVo.getUsername());
        SysUser sysUser = service.getOne(wrapper);
        if(sysUser == null){
            throw new XiaoLException(201,"用户名不存在!!!!!");
        }
        if(sysUser.getStatus()==0){
            throw new XiaoLException(201,"账号已被禁用!!!!!");
        }
        //数据库里的密码
        String password_db = sysUser.getPassword();
        //用户输入的密码
        String password_input = MD5.encrypt(loginVo.getPassword());

        if(!password_db.equals(password_input)){
            throw new XiaoLException(201,"用户名或密码错误!!!!");
        }

        //使用jwt进行tokon 将用户ID跟用户名称存入token字符串
        String token = JwtHelper.createToken(sysUser.getId(), sysUser.getUsername());
        Map<String, Object> map = new HashMap<>();
        map.put("token",token);
        return Result.ok(map);
    }
    /**
     * 获取用户信息
     * @return
     */
    @GetMapping("info")
    public Result info(HttpServletRequest httpServletRequest) {
        //从请求头获取用户信息
        String token = httpServletRequest.getHeader("token");
        //从token字符串获取用户ID 或者 用户名称
        Long userId = JwtHelper.getUserId(token);
        //根据用户ID查询数据库，将用户信息查询出来
        SysUser sysUser = service.getById(userId);
        //根据用户ID获取用户可以操作菜单列表
        //**查询数据库动态构建路由结构，进行显示
        List<RouterVo> routhList = sysMenuService.findUserMenuListById(userId);
        //根据用户ID获取用户可以操作按钮列表
        List<String> ButtList = sysMenuService.findUserButtById(userId);
        Map<String, Object> map = new HashMap<>();
        map.put("roles","[admin]");
        map.put("name",sysUser.getName());
        map.put("avatar","https://oss.aliyuncs.com/aliyun_id_photo_bucket/default_handsome.jpg");
        map.put("routers",routhList);
        map.put("buttons",ButtList);
        return Result.ok(map);
    }
    /**
     * 退出
     * @return
     */
    @PostMapping("logout")
    public Result logout(){
        return Result.ok();
    }

}
