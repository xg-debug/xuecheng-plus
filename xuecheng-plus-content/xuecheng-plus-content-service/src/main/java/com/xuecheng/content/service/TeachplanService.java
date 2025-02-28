package com.xuecheng.content.service;

import com.xuecheng.content.model.dto.BindTeachplanMediaDto;
import com.xuecheng.content.model.dto.SaveTeachplanDto;
import com.xuecheng.content.model.dto.TeachplanDto;
import com.xuecheng.content.model.po.TeachplanMedia;

import java.util.List;

public interface TeachplanService {

    /**
     * 查询教学计划树型结构
     * @param courseId 课程id
     * @return List<TeachplanDto>
     */
    public List<TeachplanDto> findTeachplanTree(Long courseId);

    /**
     * @description 保存或修改教学计划
     * @param teachplanDto  教学计划信息
     * @return void
     */
    public void saveTeachplan(SaveTeachplanDto teachplanDto);

    /**
     * @description 删除教学计划
     * @param teachplanId 教学计划id
     * @return
     */
    public void deleteTeachplan(Long teachplanId);

    /**
     * 下移教学计划
     * @param teachplanId 教学计划id
     */
    public void moveDownTeachplan(Long teachplanId);

    /**
     * 上移教学计划
     * @param teachplanId 课程计划id
     */
    public void moveUpTeachplan(Long teachplanId);

    /**
     * @description 教学计划绑定媒资
     * @param bindTeachplanMediaDto
     * @return com.xuecheng.content.model.po.TeachplanMedia
     */
    public TeachplanMedia associationMedia(BindTeachplanMediaDto bindTeachplanMediaDto);

    /**
     * 教学计划解除已绑定的媒资
     * @param teachPlanId 教学计划id
     * @param mediaId 媒资标识
     */
    public void delAssociationMedia(Long teachPlanId, String mediaId);
}
