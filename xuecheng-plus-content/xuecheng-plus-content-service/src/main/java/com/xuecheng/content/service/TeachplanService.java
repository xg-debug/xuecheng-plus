package com.xuecheng.content.service;

import com.xuecheng.content.model.dto.SaveTeachplanDto;
import com.xuecheng.content.model.dto.TeachplanDto;

import java.util.List;

public interface TeachplanService {

    /**
     * 查询课程计划树型结构
     * @param courseId 课程id
     * @return List<TeachplanDto>
     */
    public List<TeachplanDto> findTeachplanTree(Long courseId);

    /**
     * @description 保存或修改课程计划
     * @param teachplanDto  课程计划信息
     * @return void
     */
    public void saveTeachplan(SaveTeachplanDto teachplanDto);
}
