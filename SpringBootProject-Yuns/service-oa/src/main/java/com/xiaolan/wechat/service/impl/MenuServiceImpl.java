package com.xiaolan.wechat.service.impl;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.xiaolan.model.wechat.Menu;
import com.xiaolan.vo.wechat.MenuVo;
import com.xiaolan.wechat.mapper.MenuMapper;
import com.xiaolan.wechat.service.MenuService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import me.chanjar.weixin.common.error.WxErrorException;
import me.chanjar.weixin.mp.api.WxMpService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * <p>
 * 菜单 服务实现类
 * </p>
 *
 * @author atguigu
 * @since 2023-03-28
 */
@Service
public class MenuServiceImpl extends ServiceImpl<MenuMapper, Menu> implements MenuService {
    //查询所有菜单

    @Autowired
    private WxMpService wxMpService;
    @Override
    public List<MenuVo> findMenuInfo() {
        //获取所有菜单类
        List<Menu> menus = baseMapper.selectList(null);
        //查询一级菜单 ID = pid的
        List<Menu> oneMenus = new ArrayList<>();
        List<MenuVo> OneMenuVo = new ArrayList<>();
        for (Menu menu : menus) {
            //获取一级菜单
            if (menu.getParentId() == 0) {
                oneMenus.add(menu);
                MenuVo menuVo = new MenuVo();
                BeanUtils.copyProperties(menu, menuVo);
                OneMenuVo.add(menuVo);
            }

        }

        List<MenuVo> menuVoList = new ArrayList<>();
        //获取到二级菜单
        //遍历一级菜单
        for (MenuVo oneMenu : OneMenuVo) {
            List<MenuVo> twoList = new ArrayList<>();
            for (Menu twoMenu : menus) {
                if (twoMenu.getParentId() == oneMenu.getId()) {
                    MenuVo twoVoMenu = new MenuVo();
                    BeanUtils.copyProperties(twoMenu, twoVoMenu);
                    twoList.add(twoVoMenu);
                }
            }
            oneMenu.setChildren(twoList);
            menuVoList.add(oneMenu);
        }
        return menuVoList;
    }

    @Override
    public void syncMenu() {
        List<MenuVo> menuVoList = this.findMenuInfo();
        //菜单
        JSONArray buttonList = new JSONArray();
        for (MenuVo oneMenuVo : menuVoList) {
            JSONObject one = new JSONObject();
            one.put("name", oneMenuVo.getName());
            if (CollectionUtils.isEmpty(oneMenuVo.getChildren())) {
                one.put("type", oneMenuVo.getType());
                one.put("url", "http://oaxlan.vipgz4.91tunnel.com/#" + oneMenuVo.getUrl());
            } else {
                JSONArray subButton = new JSONArray();
                for (MenuVo twoMenuVo : oneMenuVo.getChildren()) {
                    JSONObject view = new JSONObject();
                    view.put("type", twoMenuVo.getType());
                    if (twoMenuVo.getType().equals("view")) {
                        view.put("name", twoMenuVo.getName());
                        //H5页面地址
                        view.put("url", "http://oaxlan.vipgz4.91tunnel.com#" + twoMenuVo.getUrl());
                    } else {
                        view.put("name", twoMenuVo.getName());
                        view.put("key", twoMenuVo.getMeunKey());
                    }
                    subButton.add(view);
                }
                one.put("sub_button", subButton);
            }
            buttonList.add(one);
        }
        JSONObject button = new JSONObject();
        button.put("button", buttonList);
        try {
            wxMpService.getMenuService().menuCreate(button.toJSONString());
        } catch (WxErrorException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void removeMenu() {
        try {
            wxMpService.getMenuService().menuDelete();
        } catch (WxErrorException e) {
            throw new RuntimeException(e);
        }
    }
}
