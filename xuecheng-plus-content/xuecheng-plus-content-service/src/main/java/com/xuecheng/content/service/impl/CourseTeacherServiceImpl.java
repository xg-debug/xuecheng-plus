package com.xuecheng.content.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.xuecheng.base.exception.XueChengPlusException;
import com.xuecheng.content.mapper.CourseTeacherMapper;
import com.xuecheng.content.model.dto.EditCourseTeacherDto;
import com.xuecheng.content.model.dto.SaveCourseTeacherDto;
import com.xuecheng.content.model.po.CourseTeacher;
import com.xuecheng.content.service.CourseTeacherService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.List;

@Slf4j
@Service
public class CourseTeacherServiceImpl implements CourseTeacherService {

    @Resource
    private CourseTeacherMapper courseTeacherMapper;

    @Override
    public List<CourseTeacher> findCourseTeacher(Long courseId) {
        LambdaQueryWrapper<CourseTeacher> queryWrapper = new LambdaQueryWrapper<CourseTeacher>();
        queryWrapper.eq(CourseTeacher::getCourseId, courseId);
        List<CourseTeacher> teacherList = courseTeacherMapper.selectList(queryWrapper);
        return teacherList;
    }

    @Transactional
    @Override
    public CourseTeacher saveCourseTeacher(SaveCourseTeacherDto teacherDto) {
        // 若传递了id,则是修改；否则是新增
        Long id = teacherDto.getId();
        if(id == null) {
            // 检查是否存在相同记录
            LambdaQueryWrapper<CourseTeacher> queryWrapper = new LambdaQueryWrapper<>();
            queryWrapper.eq(CourseTeacher::getCourseId, teacherDto.getCourseId());
            queryWrapper.eq(CourseTeacher::getTeacherName, teacherDto.getTeacherName());
            CourseTeacher one = courseTeacherMapper.selectOne(queryWrapper);
            if(one != null) {
                throw new XueChengPlusException("该教师已关联本课程,请勿重复添加");
            }
            // 没有相同记录则添加
            CourseTeacher courseTeacher = new CourseTeacher();
            BeanUtils.copyProperties(teacherDto, courseTeacher);
            int insert = courseTeacherMapper.insert(courseTeacher);
            if(insert <= 0) {
                throw new XueChengPlusException("新增教师信息失败");
            }
            // 新增成功后，数据库表中的主键id会映射到CourseTeacher中的主键id
            return courseTeacherMapper.selectById(courseTeacher.getId());
        } else {
            courseTeacherMapper.updateById(teacherDto);
            return courseTeacherMapper.selectById(id);
        }

    }

    @Transactional
    @Override
    public CourseTeacher editCourseTeacher(EditCourseTeacherDto teacherDto) {
        CourseTeacher courseTeacherNew = new CourseTeacher();
        BeanUtils.copyProperties(teacherDto, courseTeacherNew);
        int u = courseTeacherMapper.updateById(courseTeacherNew);
        if(u <= 0) {
            throw new XueChengPlusException("修改教师信息失败");
        }
        return courseTeacherMapper.selectById(teacherDto.getId());
    }

    @Transactional
    @Override
    public void deleteCourseTeacher(Long courseId, Long teacherId) {
        LambdaQueryWrapper<CourseTeacher> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(CourseTeacher::getId, teacherId);
        queryWrapper.eq(CourseTeacher::getCourseId, courseId);
        int d = courseTeacherMapper.delete(queryWrapper);
        if(d <= 0) {
            throw new XueChengPlusException("删除教师信息失败");
        }
    }
}
