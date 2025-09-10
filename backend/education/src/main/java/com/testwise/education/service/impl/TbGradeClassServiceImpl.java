package com.testwise.education.service.impl;

import com.testwise.education.domain.GradeClass;
import com.testwise.education.mapper.TbGradeClassMapper;
import com.testwise.education.service.ITbGradeClassService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * 年级管理Service业务层处理
 * 
 * @author
 * @date 2024-07-30
 */
@Service
public class TbGradeClassServiceImpl extends ServiceImpl<TbGradeClassMapper, GradeClass> implements ITbGradeClassService {
}
