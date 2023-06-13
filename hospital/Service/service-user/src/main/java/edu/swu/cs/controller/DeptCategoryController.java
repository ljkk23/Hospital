package edu.swu.cs.controller;


import edu.swu.cs.domain.ResponseResult;
import edu.swu.cs.entity.DeptCategory;
import edu.swu.cs.service.IDeptCategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.web.bind.annotation.RestController;

/**
 * <p>
 * 科室分类表 前端控制器
 * </p>
 *
 * @author liujian
 * @since 2022-11-13
 */
@RestController
@RequestMapping("/dept-category")
public class DeptCategoryController {

    @Autowired
    private IDeptCategoryService deptCategoryService;

    /**
     *
     * @return ResponseResult
     */
    @GetMapping("getCategory")
    public ResponseResult getCategory(){

        return ResponseResult.okResult(deptCategoryService.getCategory());
    }

    @GetMapping("deleteCategory")
    public ResponseResult deleteCategory(Long id){
        return deptCategoryService.deleteCategory(id);
    }

    @PostMapping ("addDept")
    public ResponseResult addDept(DeptCategory deptCategory){
        return deptCategoryService.addDept(deptCategory);
    }

}

