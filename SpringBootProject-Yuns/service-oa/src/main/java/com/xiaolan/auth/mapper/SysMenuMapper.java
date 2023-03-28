package com.xiaolan.auth.mapper;


import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.xiaolan.model.system.SysMenu;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * <p>
 * 菜单表 Mapper 接口
 * </p>
 *
 * @author atguigu
 * @since 2023-03-23
 */
@Mapper
public interface SysMenuMapper extends BaseMapper<SysMenu> {

    List<SysMenu> selectRouter(Long userId);
}
