package com.xuecheng.content.api;

import com.xuecheng.content.model.dto.SaveCourseTeacherDto;
import com.xuecheng.content.model.po.CourseTeacher;
import com.xuecheng.content.service.CourseTeacherService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;

@Api(value = "师资管理编辑接口",tags = "师资管理编辑接口")
@RestController
public class CourseTeacherController {

    @Resource
    private CourseTeacherService courseTeacherService;

    @ApiOperation("课程所属教师查询")
    @GetMapping("/courseTeacher/list/{courseId}")
    public List<CourseTeacher> getCourseTeacherList(@PathVariable Long courseId) {
        return courseTeacherService.findCourseTeacher(courseId);
    }

    @ApiOperation("添加或修改教师信息")
    @PostMapping("/courseTeacher")
    public CourseTeacher saveCourseTeacher(@RequestBody SaveCourseTeacherDto teacherDto) {
        return courseTeacherService.saveCourseTeacher(teacherDto);
    }

//    @ApiOperation("修改教师")
//    @PutMapping("/courseTeacher")
//    public CourseTeacher editCourseTeacher(@RequestBody EditCourseTeacherDto teacherDto) {
//        return courseTeacherService.editCourseTeacher(teacherDto);
//    }

    @ApiOperation("删除教师")
    @DeleteMapping("/courseTeacher/course/{courseId}/{teacherId}")
    public ResponseEntity<Void> delCourseTeacher(@PathVariable Long courseId, @PathVariable Long teacherId) {
        courseTeacherService.deleteCourseTeacher(courseId, teacherId);
        return ResponseEntity.ok().build();
    }
}
