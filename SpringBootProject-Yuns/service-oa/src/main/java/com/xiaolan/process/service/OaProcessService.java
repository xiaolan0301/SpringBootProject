package com.xiaolan.process.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.xiaolan.model.process.Process;
import com.baomidou.mybatisplus.extension.service.IService;
import com.xiaolan.vo.process.ApprovalVo;
import com.xiaolan.vo.process.ProcessFormVo;
import com.xiaolan.vo.process.ProcessQueryVo;
import com.xiaolan.vo.process.ProcessVo;

import java.util.Map;

/**
 * <p>
 * 审批类型 服务类
 * </p>
 *
 * @author atguigu
 * @since 2023-03-26
 */
public interface OaProcessService extends IService<Process> {
    IPage<ProcessVo> selectPage(Page<ProcessVo> pageParam, ProcessQueryVo processQueryVo);

    void deployByZip(String deployPath);

    void startUp(ProcessFormVo processFormVo);

    IPage<ProcessVo> findPending(Page<java.lang.Process> page1);

    Map<String, Object> show(Long id);

    void approve(ApprovalVo approvalVo);

    IPage<ProcessVo> findProcessed(Page<java.lang.Process> pageParam);

    Object findStarted(Page<ProcessVo> pageParam);
}
