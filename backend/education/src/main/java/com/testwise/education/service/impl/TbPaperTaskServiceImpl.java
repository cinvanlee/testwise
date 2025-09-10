package com.testwise.education.service.impl;

import com.testwise.education.domain.PaperTask;
import com.testwise.education.mapper.TbPaperTaskMapper;
import com.testwise.education.service.ITbPaperTaskService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * 试卷任务管理Service业务层处理
 *
 * @author
 * @date 2024-07-30
 */
@Service
public class TbPaperTaskServiceImpl extends ServiceImpl<TbPaperTaskMapper, PaperTask> implements ITbPaperTaskService {
}
