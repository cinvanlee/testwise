package com.testwise.education.service.impl;

import com.testwise.education.domain.QuestionsBankOptions;
import com.testwise.education.mapper.TbQuestionsBankOptionsMapper;
import com.testwise.education.service.ITbQuestionsBankOptionsService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * 题库选项管理Service业务层处理
 *
 * @author
 * @date 2024-07-30
 */
@Service
public class TbQuestionsBankOptionsServiceImpl extends ServiceImpl<TbQuestionsBankOptionsMapper, QuestionsBankOptions> implements ITbQuestionsBankOptionsService {
}
