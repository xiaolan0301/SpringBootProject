package com.xiaolan.process.service;

import com.xiaolan.model.process.ProcessType;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
 * <p>
 * 审批类型 服务类
 * </p>
 *
 * @author atguigu
 * @since 2023-03-26
 */
public interface OaProcessTypeService extends IService<ProcessType> {

    List<ProcessType> findProcessType();
}
