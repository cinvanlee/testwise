package com.testwise.web.controller.education;

import java.util.List;
import java.util.Optional;
import javax.servlet.http.HttpServletResponse;

import com.testwise.common.utils.StringUtils;
import com.testwise.education.domain.*;
import com.testwise.education.domain.query.PageQuery;
import com.testwise.education.service.ITbDisciplineService;
import com.testwise.education.service.ITbGradeClassService;
import com.testwise.education.service.ITbQuestionsBankOptionsService;
import com.testwise.education.service.ITbQuestionsBankService;
import com.baomidou.mybatisplus.core.toolkit.CollectionUtils;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import lombok.AllArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.testwise.common.annotation.Log;
import com.testwise.common.core.controller.BaseController;
import com.testwise.common.core.domain.AjaxResult;
import com.testwise.common.enums.BusinessType;
import com.testwise.common.core.page.TableDataInfo;

/**
 * 题库管理Controller
 *
 * @author
 * @date 2024-07-30
 */
@RestController
@RequestMapping("/education/bank")
@AllArgsConstructor
public class TbQuestionsBankController extends BaseController {
    private final ITbQuestionsBankService tbQuestionsBankService;
    private final ITbQuestionsBankOptionsService tbQuestionsBankOptionsService;
    private final ITbGradeClassService tbGradeClassService;
    private final ITbDisciplineService tbDisciplineService;

    /**
     * 查询题库管理列表
     */
    @PreAuthorize("@ss.hasPermi('system:bank:list')")
    @GetMapping("/list")
    public TableDataInfo list(QuestionsBank questionsBank, PageQuery pageQuery) {
        Page<QuestionsBank> page = tbQuestionsBankService.page(new Page<>(pageQuery.getPageNum(),pageQuery.getPageSize()), Wrappers.<QuestionsBank>lambdaQuery()
                .eq(StringUtils.isNotBlank(questionsBank.getGradeClassId()), QuestionsBank::getGradeClassId, questionsBank.getGradeClassId())
                .like(StringUtils.isNotBlank(questionsBank.getTitle()), QuestionsBank::getTitle, questionsBank.getTitle())
                .eq(StringUtils.isNotBlank(questionsBank.getDisciplineId()), QuestionsBank::getDisciplineId, questionsBank.getDisciplineId())
                .eq(StringUtils.isNotBlank(questionsBank.getQuestionsType()), QuestionsBank::getQuestionsType, questionsBank.getQuestionsType())
                .notIn(CollectionUtils.isNotEmpty(questionsBank.getExcludeBankIds()), QuestionsBank::getId, questionsBank.getExcludeBankIds()));
        List<QuestionsBank> list = page.getRecords();
        for (QuestionsBank entity : list) {
            GradeClass gradeClass = tbGradeClassService.getById(entity.getGradeClassId());
            gradeClass = Optional.ofNullable(gradeClass).orElse(new GradeClass());
            Discipline discipline = tbDisciplineService.getById(entity.getDisciplineId());
            discipline = Optional.ofNullable(discipline).orElse(new Discipline());
            entity.setDisciplineName(String.format("%s(%s)", discipline.getDisciplineName(), gradeClass.getGradeClassName()));
            List<QuestionsBankOptions> bankOptionsList = tbQuestionsBankOptionsService.list(Wrappers.<QuestionsBankOptions>lambdaQuery()
                    .eq(QuestionsBankOptions::getQuestionsBankId, entity.getId())
                    .orderByAsc(QuestionsBankOptions::getSortField));
            entity.setBankOptionsList(bankOptionsList);
        }
        return getDataPageTable(page);
    }

    /**
     * 获取题库管理详细信息
     */
    @PreAuthorize("@ss.hasPermi('system:bank:query')")
    @GetMapping(value = "/{id}")
    public AjaxResult getInfo(@PathVariable("id") String id) {
        QuestionsBank bank = tbQuestionsBankService.getById(id);
        List<QuestionsBankOptions> bankOptionsList = tbQuestionsBankOptionsService.list(Wrappers.<QuestionsBankOptions>lambdaQuery()
                .eq(QuestionsBankOptions::getQuestionsBankId, id)
                .orderByAsc(QuestionsBankOptions::getSortField));
        bank.setBankOptionsList(bankOptionsList);
        return success(bank);
    }

    /**
     * 新增题库管理
     */
    @PreAuthorize("@ss.hasPermi('system:bank:add')")
    @Log(title = "题库管理", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody QuestionsBank questionsBank) {
        return toAjax(tbQuestionsBankService.addEntity(questionsBank));
    }

    /**
     * 修改题库管理
     */
    @PreAuthorize("@ss.hasPermi('system:bank:edit')")
    @Log(title = "题库管理", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody QuestionsBank questionsBank) {
        return toAjax(tbQuestionsBankService.editEntity(questionsBank));
    }

    /**
     * 删除题库管理
     */
    @PreAuthorize("@ss.hasPermi('system:bank:remove')")
    @Log(title = "题库管理", businessType = BusinessType.DELETE)
    @DeleteMapping("/{ids}")
    public AjaxResult remove(@PathVariable String[] ids) {
        return toAjax(tbQuestionsBankService.removeEntity(ids));
    }
}
