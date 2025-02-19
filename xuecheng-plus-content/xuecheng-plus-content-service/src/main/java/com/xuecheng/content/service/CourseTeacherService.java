package com.xuecheng.content.service;

import com.xuecheng.content.model.dto.EditCourseTeacherDto;
import com.xuecheng.content.model.dto.SaveCourseTeacherDto;
import com.xuecheng.content.model.po.CourseTeacher;

import java.util.List;

public interface CourseTeacherService {

    /**
     * 根据课程id查找所属课程的所有老师
     * @param courseId 课程计划id
     * @return 教师数组
     */
    public List<CourseTeacher> findCourseTeacher(Long courseId);

    /**
     * 添加教师
     * @param teacherDto 接受教师信息实体类
     * @return
     */
    public CourseTeacher saveCourseTeacher(SaveCourseTeacherDto teacherDto);

    /**
     * 修改教师信息
     * @param teacherDto 教师信息实体类
     * @return
     */
    public CourseTeacher editCourseTeacher(EditCourseTeacherDto teacherDto);

    /**
     * 删除某个课程的某个教师
     * @param courseId 课程id
     * @param teacherId 教师id
     */
    public void deleteCourseTeacher(Long courseId, Long teacherId);
}
