package com.testwise.web.controller.education;

import java.util.Arrays;
import java.util.List;
import javax.servlet.http.HttpServletResponse;

import com.testwise.common.utils.StringUtils;
import com.testwise.education.domain.GradeClass;
import com.testwise.education.domain.query.PageQuery;
import com.testwise.education.service.ITbGradeClassService;
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
 * 年级管理Controller
 *
 * @author
 * @date 2024-07-30
 */
@RestController
@RequestMapping("/education/class")
@AllArgsConstructor
public class TbGradeClassController extends BaseController {
    private final ITbGradeClassService tbGradeClassService;

    /**
     * 查询年级管理列表
     */
    @PreAuthorize("@ss.hasPermi('system:class:list')")
    @GetMapping("/list")
    public TableDataInfo list(GradeClass gradeClass, PageQuery pageQuery) {
        Page<GradeClass> page = tbGradeClassService.page(new Page<>(pageQuery.getPageNum(),pageQuery.getPageSize()), Wrappers.<GradeClass>lambdaQuery()
                .like(StringUtils.isNotBlank(gradeClass.getGradeClassName()), GradeClass::getGradeClassName, gradeClass.getGradeClassName()));
        return getDataPageTable(page);
    }

    /**
     * 查询年级管理列表
     */
    @GetMapping("/selectList")
    public AjaxResult selectList(GradeClass gradeClass) {
        List<GradeClass> list = tbGradeClassService.list(Wrappers.<GradeClass>lambdaQuery()
                .like(StringUtils.isNotBlank(gradeClass.getGradeClassName()), GradeClass::getGradeClassName, gradeClass.getGradeClassName()));
        return success(list);
    }

    /**
     * 获取年级管理详细信息
     */
    @PreAuthorize("@ss.hasPermi('system:class:query')")
    @GetMapping(value = "/{id}")
    public AjaxResult getInfo(@PathVariable("id") String id) {
        return success(tbGradeClassService.getById(id));
    }

    /**
     * 新增年级管理
     */
    @PreAuthorize("@ss.hasPermi('system:class:add')")
    @Log(title = "年级管理", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody GradeClass gradeClass) {
        return toAjax(tbGradeClassService.save(gradeClass));
    }

    /**
     * 修改年级管理
     */
    @PreAuthorize("@ss.hasPermi('system:class:edit')")
    @Log(title = "年级管理", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody GradeClass gradeClass) {
        return toAjax(tbGradeClassService.updateById(gradeClass));
    }

    /**
     * 删除年级管理
     */
    @PreAuthorize("@ss.hasPermi('system:class:remove')")
    @Log(title = "年级管理", businessType = BusinessType.DELETE)
    @DeleteMapping("/{ids}")
    public AjaxResult remove(@PathVariable String[] ids) {
        return toAjax(tbGradeClassService.removeBatchByIds(Arrays.asList(ids)));
    }
}
