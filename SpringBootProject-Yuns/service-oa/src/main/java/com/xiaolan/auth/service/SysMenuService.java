package com.xiaolan.auth.service;

import com.xiaolan.model.system.SysMenu;
import com.baomidou.mybatisplus.extension.service.IService;
import com.xiaolan.vo.system.AssginMenuVo;
import com.xiaolan.vo.system.RouterVo;

import java.util.List;

/**
 * <p>
 * 菜单表 服务类
 * </p>
 *
 * @author atguigu
 * @since 2023-03-23
 */
public interface SysMenuService extends IService<SysMenu> {

    List<SysMenu> findNodes();

    void removeMenuById(Long id);

    List<SysMenu> findSysMenuByRoleId(Long roleId);

    void doAssign(AssginMenuVo assignMenuVo);

    List<RouterVo> findUserMenuListById(Long userId);

    List<RouterVo> buildTree(List<SysMenu> menuRoute);

    List<String> findUserButtById(Long userId);
}
