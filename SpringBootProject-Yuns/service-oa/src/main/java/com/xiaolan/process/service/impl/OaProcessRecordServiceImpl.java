package com.xiaolan.process.service.impl;


import com.xiaolan.auth.service.SysUserService;
import com.xiaolan.model.process.ProcessRecord;
import com.xiaolan.model.system.SysUser;
import com.xiaolan.process.mapper.OaProcessRecordMapper;
import com.xiaolan.process.service.OaProcessRecordService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.xiaolan.security.custom.LoginUserInfoHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 审批记录 服务实现类
 * </p>
 *
 * @author atguigu
 * @since 2023-03-27
 */
@Service
public class OaProcessRecordServiceImpl extends ServiceImpl<OaProcessRecordMapper,ProcessRecord> implements OaProcessRecordService {

    @Autowired
    private SysUserService service;

    @Autowired
    private OaProcessRecordMapper processRecordMapper;
    @Override
    public void record(Long processId, Integer status, String description) {
        Long userId = LoginUserInfoHelper.getUserId();
        SysUser user = service.getById(userId);

        ProcessRecord precord = new ProcessRecord();
        precord.setProcessId(processId);
        precord.setStatus(status);
        precord.setDescription(description);
        precord.setOperateUser(user.getUsername());
        precord.setOperateUserId(user.getId());
        processRecordMapper.insert(precord);
    }
}
