package com.xiaolan.process.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.xiaolan.model.process.ProcessTemplate;
import com.xiaolan.model.process.ProcessType;
import com.xiaolan.process.mapper.OaProcessTypeMapper;
import com.xiaolan.process.service.OaProcessTemplateService;
import com.xiaolan.process.service.OaProcessTypeService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * <p>
 * 审批类型 服务实现类
 * </p>
 *
 * @author atguigu
 * @since 2023-03-26
 */
@Service
public class OaProcessTypeServiceImpl extends ServiceImpl<OaProcessTypeMapper, ProcessType> implements OaProcessTypeService {
    @Autowired
    OaProcessTemplateService templateService;
    //获取审批分类与对应的审批模板
    @Override
    public  List<ProcessType> findProcessType() {
        //获取审批所有分类
        List<ProcessType> processTypes = baseMapper.selectList(null);
        //遍历所有分类
        for(ProcessType proType:processTypes){
            Long typeId = proType.getId();
            LambdaQueryWrapper<ProcessTemplate> wrapper = new LambdaQueryWrapper<>();
            wrapper.eq(ProcessTemplate::getProcessTypeId,typeId);
            //找到与分类所属的模板
            List<ProcessTemplate> templatesList = templateService.list(wrapper);
            proType.setProcessTemplateList(templatesList);
        }
        //将模板封装到对应类
        return processTypes;
    }

}
