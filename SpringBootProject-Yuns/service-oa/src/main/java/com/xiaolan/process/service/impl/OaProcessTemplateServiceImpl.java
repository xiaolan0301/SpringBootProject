package com.xiaolan.process.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.xiaolan.model.process.ProcessTemplate;
import com.xiaolan.model.process.ProcessType;
import com.xiaolan.process.mapper.OaProcessTemplateMapper;
import com.xiaolan.process.service.OaProcessService;
import com.xiaolan.process.service.OaProcessTemplateService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.xiaolan.process.service.OaProcessTypeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import java.util.List;

/**
 * <p>
 * 审批模板 服务实现类
 * </p>
 *
 * @author atguigu
 * @since 2023-03-26
 */
@Service
public class OaProcessTemplateServiceImpl extends ServiceImpl<OaProcessTemplateMapper, ProcessTemplate> implements OaProcessTemplateService {

    @Autowired
    OaProcessTypeService oaProcessTypeService;

    @Autowired
    private OaProcessService processService;


    @Override
    public IPage<ProcessTemplate> selectPage(Page<ProcessTemplate> pageParam) {
        //通过Id查询审批的名称
        Page<ProcessTemplate> processTemplatePage = baseMapper.selectPage(pageParam,null);
        //获取审批列表的ID
        List<ProcessTemplate> records = processTemplatePage.getRecords();
        for(ProcessTemplate pro : records){
            Long processTypeId = pro.getProcessTypeId();
            LambdaQueryWrapper<ProcessType> wrapper = new LambdaQueryWrapper<>();
            wrapper.eq(ProcessType::getId,processTypeId);
            ProcessType byId = oaProcessTypeService.getOne(wrapper);
            pro.setProcessTypeName(byId.getName());
        }

        return processTemplatePage;
    }


    @Transactional
    @Override
    public void publish(Long id) {
        ProcessTemplate processTemplate = this.getById(id);
        processTemplate.setStatus(1);
        baseMapper.updateById(processTemplate);

        //优先发布在线流程设计
        if(!StringUtils.isEmpty(processTemplate.getProcessDefinitionPath())) {
            processService.deployByZip(processTemplate.getProcessDefinitionPath());
        }
    }
}
