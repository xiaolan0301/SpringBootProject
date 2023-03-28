package com.xiaolan.sysmenu.utils;

import com.xiaolan.model.system.SysMenu;

import java.util.ArrayList;
import java.util.List;

public class MenuUtil {


    public static List<SysMenu> buildTree(List<SysMenu> sysMenus) {
        List<SysMenu> tree = new ArrayList<>();
        //遍历sysMenus获取每个SysMenu的值
        for(SysMenu sysMenu:sysMenus){
            //递归入口
            if (sysMenu.getParentId().longValue() == 0){
                  tree.add(getChildren(sysMenu,sysMenus));
            }
        }
        return tree;
    }

    public static SysMenu getChildren(SysMenu sysMenu, List<SysMenu> sysMenus) {
        sysMenu.setChildren(new ArrayList<SysMenu>());
        //遍历全部菜单 找到sysMenu下级
        for(SysMenu menu : sysMenus){
            if(menu.getParentId().longValue()==sysMenu.getId().longValue()){
                if (sysMenu.getChildren() == null) {
                    sysMenu.setChildren(new ArrayList<>());
                }
                sysMenu.getChildren().add(getChildren(menu,sysMenus));
            }
        }
        return sysMenu;
    }
}
