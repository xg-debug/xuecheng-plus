package com.xuecheng.content.model.dto;

import com.xuecheng.content.model.po.CourseTeacher;
import io.swagger.annotations.ApiModel;
import lombok.Data;
import lombok.ToString;

@Data
@ToString
@ApiModel(value="AddCourseTeacherDto", description="新增课程教师信息")
public class SaveCourseTeacherDto extends CourseTeacher {

}
