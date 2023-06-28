package edu.swu.cs.controller;


import edu.swu.cs.domain.ResponseResult;
import edu.swu.cs.entity.DeptCategory;
import edu.swu.cs.service.IDeptCategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

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

    @GetMapping("getCategoryByRoot")
    public ResponseResult getCategoryByRoot(){
        return ResponseResult.okResult(deptCategoryService.getCategoryByRoot());
    }

    @GetMapping("deleteCategory")
    public ResponseResult deleteCategory(Long id){
        return deptCategoryService.deleteCategory(id);
    }

    @PreAuthorize("hasAnyAuthority('system:menu:add','admin')")
    @PostMapping ("addDept")
    public ResponseResult addDept(@RequestBody  DeptCategory deptCategory){
        return deptCategoryService.addDept(deptCategory);
    }

    @GetMapping("/getDeptDoctorsByDoctorName")
    public ResponseResult getDeptDoctorsByDoctorName(String userName){
        return ResponseResult.okResult(deptCategoryService.getDeptDoctorsByDoctorName(userName));
    }
}

