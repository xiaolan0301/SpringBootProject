package com.xiaolan.auth.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.xiaolan.auth.service.SysRoleMenuService;
import com.xiaolan.common.exception.XiaoLException;
import com.xiaolan.model.system.SysMenu;
import com.xiaolan.auth.mapper.SysMenuMapper;
import com.xiaolan.auth.service.SysMenuService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.xiaolan.model.system.SysRoleMenu;
import com.xiaolan.sysmenu.utils.MenuUtil;
import com.xiaolan.vo.system.AssginMenuVo;
import com.xiaolan.vo.system.MetaVo;
import com.xiaolan.vo.system.RouterVo;
import org.apache.commons.lang3.concurrent.ConcurrentUtils;
import org.apache.ibatis.annotations.Param;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

/**
 * <p>
 * 菜单表 服务实现类
 * </p>
 *
 * @author atguigu
 * @since 2023-03-23
 */
@Service("sysMenuServiceImpl")
public class SysMenuServiceImpl extends ServiceImpl<SysMenuMapper, SysMenu> implements SysMenuService {

    @Autowired
    SysRoleMenuService service;
    @Override
    public List<SysMenu> findNodes() {
        //获取所有菜单
        //双层遍历 如果j的praid = i.id 则添加到 chid集合里
        //
        List<SysMenu> sysMenus = baseMapper.selectList(null);
        //利用工具类递归
        List<SysMenu> res = MenuUtil.buildTree(sysMenus);
        return res;
    }

    @Override
    public void removeMenuById(Long id) {
        //查看这个菜单是否有子菜单
            //遍历全部菜单，查找有没有praId= id的
        List<SysMenu> sysMenus = baseMapper.selectList(null);
        List<SysMenu> sysMenu = new ArrayList<>();
        for(SysMenu menu:sysMenus){
            if(menu.getParentId()==id){
                sysMenu.add(menu);
            }
        }
        if(sysMenu.size()>0){
            throw new XiaoLException(201,"菜单不能删除");
        }else{
            baseMapper.deleteById(id);
        }

    }
    //根据角色获取菜单
    @Override
    public List<SysMenu> findSysMenuByRoleId(Long roleId) {
        //根据状态 获取所有菜单
        LambdaQueryWrapper<SysMenu> sysMenuWrapper = new LambdaQueryWrapper<>();
        sysMenuWrapper.eq(SysMenu::getStatus,1);
        List<SysMenu> sysMenus = baseMapper.selectList(sysMenuWrapper);
        //获取到菜单 根据roleId 找到角色菜单 对应的 菜单ID
        LambdaQueryWrapper<SysRoleMenu> sysRoleMenuWrapper = new LambdaQueryWrapper<>();
        sysRoleMenuWrapper.eq(SysRoleMenu::getRoleId,roleId);
        //获取到RoleID 在角色菜单里的字段集合
        List<SysRoleMenu> sysRoleMenus = service.list(sysRoleMenuWrapper);
        List<SysMenu> sysMenuss = new ArrayList<>();
        //遍历菜单 找到与RoleId对应的菜单
        for(SysMenu sysMenu:sysMenus){
            //如果角色菜单里有菜单ID 获取到了
            if(sysRoleMenus.contains(sysMenu.getId())){
                sysMenu.setSelect(true);
            }else {
                sysMenu.setSelect(false);
            }
        }
        List<SysMenu> sysMenus1 = MenuUtil.buildTree(sysMenus);
        return sysMenus1;
    }

    //分配菜单
    @Override
    public void doAssign(AssginMenuVo assignMenuVo) {
    //根据角色ID删除原有的菜单
    LambdaQueryWrapper<SysRoleMenu> wrapper = new LambdaQueryWrapper<>();
    wrapper.eq(SysRoleMenu::getRoleId,assignMenuVo.getRoleId());
    service.remove(wrapper);
    //添加
        //获取到菜单ID
        for (Long id:assignMenuVo.getMenuIdList()){
            if (StringUtils.isEmpty(id)) continue;
            SysRoleMenu sysRoleMenu = new SysRoleMenu();
            sysRoleMenu.setRoleId(assignMenuVo.getRoleId());
            sysRoleMenu.setMenuId(id);
            service.save(sysRoleMenu);
        }
    }
    //查询数据库动态构建路由结构，进行显示
    @Override
    public List<RouterVo> findUserMenuListById(Long userId) {
        List<SysMenu> sysMenus = null;
        //是否为管理员
        // **是的话 遍历所有菜单
        // **不是的话 通过userID来查询多表 最后获取路由
        if(userId.longValue() == 1){
            LambdaQueryWrapper<SysMenu> wrapper = new LambdaQueryWrapper();
            wrapper.eq(SysMenu::getStatus,1);
            wrapper.orderByAsc(SysMenu::getSortValue);
            sysMenus = baseMapper.selectList(wrapper);
        }else{
            sysMenus = baseMapper.selectRouter(userId);
        }
        //构建成树状结构
        List<SysMenu> MenuRoute = MenuUtil.buildTree(sysMenus);
        //构建成路由结构
        List<RouterVo> router = this.buildTree(MenuRoute);
        System.out.println("*********"+router);
        return router;
    }

    @Override
    public List<RouterVo> buildTree(List<SysMenu> menuRoute) {
        List<RouterVo> routers = new ArrayList<>();
        for (SysMenu menu : menuRoute) {
            RouterVo router = new RouterVo();
            router.setHidden(false);
            router.setAlwaysShow(false);
            router.setPath(getRouterPath(menu));
            router.setComponent(menu.getComponent());
            router.setMeta(new MetaVo(menu.getName(), menu.getIcon()));
            List<SysMenu> children = menu.getChildren();
            //如果当前是菜单，需将按钮对应的路由加载出来，如：“角色授权”按钮对应的路由在“系统管理”下面
            if(menu.getType().intValue() == 1) {
                List<SysMenu> hiddenMenuList = children.stream().filter(item -> !StringUtils.isEmpty(item.getComponent())).collect(Collectors.toList());
                for (SysMenu hiddenMenu : hiddenMenuList) {
                    RouterVo hiddenRouter = new RouterVo();
                    hiddenRouter.setHidden(true);
                    hiddenRouter.setAlwaysShow(false);
                    hiddenRouter.setPath(getRouterPath(hiddenMenu));
                    hiddenRouter.setComponent(hiddenMenu.getComponent());
                    hiddenRouter.setMeta(new MetaVo(hiddenMenu.getName(), hiddenMenu.getIcon()));
                    routers.add(hiddenRouter);
                }
            } else {
                if (!CollectionUtils.isEmpty(children)) {
                    if(children.size() > 0) {
                        router.setAlwaysShow(true);
                    }
                    router.setChildren(buildTree(children));
                }
            }
            routers.add(router);
        }
        return routers;
    }
    
    //查看用户按钮权限
    @Override
    public List<String> findUserButtById(Long userId) {
        //如果管理员都可以操作，如果不是通过查询到能操作的菜单 然后看pram
        List<SysMenu> sysMenus = null;
        //是否为管理员
        // **是的话 遍历所有菜单
        // **不是的话 通过userID来查询多表 最后获取路由
        if (userId.longValue() == 1) {
            LambdaQueryWrapper<SysMenu> wrapper = new LambdaQueryWrapper();
            wrapper.eq(SysMenu::getStatus, 1);
            wrapper.orderByAsc(SysMenu::getSortValue);
            sysMenus = baseMapper.selectList(wrapper);
        }else{
            sysMenus = baseMapper.selectRouter(userId);
        }
            List<String> perms = new ArrayList<>();
            for(SysMenu res:sysMenus){
                if(res.getType()==2){
                    if(!StringUtils.isEmpty(res.getPerms())){
                        perms.add(res.getPerms());
                    }
                }
            }
            return perms;
    }

    public String getRouterPath(SysMenu menu) {
        String routerPath = "/" + menu.getPath();
        if(menu.getParentId().intValue() != 0) {
            routerPath = menu.getPath();
        }
        return routerPath;
    }
}
