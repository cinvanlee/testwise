package com.testwise.education.mapper;


import com.testwise.education.domain.ExaminationPaper;
import com.testwise.education.domain.dto.StatisticsDTO;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * 试卷管理Mapper接口
 * 
 * @author
 * @date 2024-07-30
 */
@Mapper
public interface TbExaminationPaperMapper extends BaseMapper<ExaminationPaper> {

    List<StatisticsDTO> getMonthTotal();
}
