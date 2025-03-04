package com.xuecheng.content.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.xuecheng.base.exception.BusinessException;
import com.xuecheng.base.exception.XueChengPlusException;
import com.xuecheng.content.mapper.TeachplanMapper;
import com.xuecheng.content.mapper.TeachplanMediaMapper;
import com.xuecheng.content.model.dto.BindTeachplanMediaDto;
import com.xuecheng.content.model.dto.SaveTeachplanDto;
import com.xuecheng.content.model.dto.TeachplanDto;
import com.xuecheng.content.model.po.Teachplan;
import com.xuecheng.content.model.po.TeachplanMedia;
import com.xuecheng.content.service.TeachplanService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.time.LocalDateTime;
import java.util.List;

@Slf4j
@Service
public class TeachplanServiceImpl implements TeachplanService {

    @Resource
    private TeachplanMapper teachplanMapper;

    @Resource
    private TeachplanMediaMapper teachplanMediaMapper;

    @Override
    public List<TeachplanDto> findTeachplanTree(Long courseId) {
        return teachplanMapper.selectTreeNodes(courseId);
    }

    /**
     * 传递了课程计划id说明当前是要修改该课程计划，否则是新增一个课程计划
     * @param teachplanDto  课程计划信息
     */
    @Transactional
    @Override
    public void saveTeachplan(SaveTeachplanDto teachplanDto) {
        // 课程计划id
        Long id = teachplanDto.getId();
        if(id != null) {
            // 修改课程计划
            Teachplan teachplan = teachplanMapper.selectById(id);
            BeanUtils.copyProperties(teachplanDto, teachplan);
            teachplanMapper.updateById(teachplan);
        } else {
            // 添加课程计划

//            // 取出同父同级别的课程计划数量
//            int count = getTeachplanCount(teachplanDto.getCourseId(), teachplanDto.getParentid());
//            Teachplan teachplanNew = new Teachplan();
//            // 设置排序号
//            teachplanNew.setOrderby(count + 1);

            // 查出最大的排序号

            Teachplan teachplanNew = new Teachplan();
            int maxOrderBy = teachplanMapper.getMaxOrderBy(teachplanDto.getCourseId());
            // 设置排序号
            teachplanNew.setOrderby(maxOrderBy + 1);
            BeanUtils.copyProperties(teachplanDto, teachplanNew);

            teachplanMapper.insert(teachplanNew);
            // 新增课程之后需要请求
        }
    }

    /**
     * 删除第一级别的大章节时要求大章节下边没有小章节时方可删除。
     * 删除第二级别的小章节的同时需要将teachplan_media表关联的信息也删除。
     */
    @Transactional
    @Override
    public void deleteTeachplan(Long teachplanId) {
        Teachplan teachplan = teachplanMapper.selectById(teachplanId);
        if(teachplan == null) {
            throw new XueChengPlusException("该课程计划不存在");
        }
        // 检查是否存在小章节
        LambdaQueryWrapper<Teachplan> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(Teachplan::getParentid, teachplanId);
        Integer childCount = teachplanMapper.selectCount(queryWrapper);
        if (childCount > 0) {
            throw new BusinessException("120409", "课程计划信息还有子级信息，无法操作");
        }
        // 删除的是大章节
        if(teachplan.getGrade() == 1 && childCount == 0) {
            // 大章节下没有小章节
            teachplanMapper.deleteById(teachplanId);
        }
        // 删除的是小章节
        if(teachplan.getGrade() == 2) {
            teachplanMapper.deleteById(teachplanId);
            teachplanMediaMapper.deleteByTeachplanId(teachplanId);
        }

    }

    /**
     * 将课程计划下移：需要找到下一个排序号更大的课程计划，并交换两个课程计划的排序号
     * @param teachplanId 课程计划id
     */
    @Override
    public void moveDownTeachplan(Long teachplanId) {
        // 1.查询当前课程计划：可能是大章节或小章节
        Teachplan current = teachplanMapper.selectById(teachplanId);
        if (current == null) {
            throw new XueChengPlusException("当前课程计划不存在");
        }
        // 2. 查询相邻节点（上移找prev，下移找next）
        Teachplan adjacent = teachplanMapper.findNext(
                current.getCourseId(),
                current.getGrade(),
                current.getParentid(),
                current.getOrderby()
        );
        // 3. 校验是否可移动
        if (adjacent == null) {
            throw new XueChengPlusException("无法移动");
        }
        // 4. 交换排序号
        swapOrderNum(current, adjacent);
    }

    @Override
    public void moveUpTeachplan(Long teachplanId) {
        // 1.查询当前课程计划：可能是大章节或小章节
        Teachplan current = teachplanMapper.selectById(teachplanId);
        if (current == null) {
            throw new XueChengPlusException("当前课程计划不存在");
        }
        // 2. 查询相邻节点（上移找prev，下移找next）
        Teachplan adjacent = teachplanMapper.findPrev(
                current.getCourseId(),
                current.getGrade(),
                current.getParentid(),
                current.getOrderby()
        );
        // 3. 校验是否可移动
        if (adjacent == null) {
            throw new XueChengPlusException("无法移动");
        }
        // 4. 交换排序号
        swapOrderNum(current, adjacent);
    }

    private void swapOrderNum(Teachplan current, Teachplan adjacent) {
        Integer temp = current.getOrderby();
        current.setOrderby(adjacent.getOrderby());
        adjacent.setOrderby(temp);
        teachplanMapper.updateById(current);
        teachplanMapper.updateById(adjacent);
    }

//    private int getTeachplanCount(long courseId, long parentId) {
//        LambdaQueryWrapper<Teachplan> queryWrapper = new LambdaQueryWrapper<>();
//        queryWrapper.eq(Teachplan::getCourseId, courseId);
//        queryWrapper.eq(Teachplan::getParentid, parentId);
//        Integer count = teachplanMapper.selectCount(queryWrapper);
//        return count;
//    }

    @Transactional
    @Override
    public TeachplanMedia associationMedia(BindTeachplanMediaDto bindTeachplanMediaDto) {
        Long teachplanId = bindTeachplanMediaDto.getTeachplanId();
        Teachplan teachplan = teachplanMapper.selectById(teachplanId);
        if(teachplan == null) {
            XueChengPlusException.cast("教学计划不存在");
        }
        Integer grade = teachplan.getGrade();
        if(grade != 2){
            XueChengPlusException.cast("只允许第二级教学计划绑定媒资文件");
        }
        //课程id
        Long courseId = teachplan.getCourseId();
        //先删除原来该教学计划绑定的媒资
        teachplanMediaMapper.deleteByTeachplanId(teachplanId);

        //再添加教学计划与媒资的绑定关系
        TeachplanMedia teachplanMedia = new TeachplanMedia();
        teachplanMedia.setCourseId(courseId);
        teachplanMedia.setTeachplanId(teachplanId);
        teachplanMedia.setMediaId(bindTeachplanMediaDto.getMediaId());
        teachplanMedia.setMediaFilename(bindTeachplanMediaDto.getFileName());
        teachplanMedia.setCreateDate(LocalDateTime.now());
        teachplanMediaMapper.insert(teachplanMedia);
        return teachplanMedia;
    }

    @Transactional
    @Override
    public void delAssociationMedia(Long teachPlanId, String mediaId) {
        Teachplan teachplan = teachplanMapper.selectById(teachPlanId);
        // 拿到courseId
        Long courseId = teachplan.getCourseId();
        // 构造删除条件
        LambdaQueryWrapper<TeachplanMedia> deleteWrapper = new LambdaQueryWrapper<>();
        deleteWrapper.eq(TeachplanMedia::getTeachplanId, teachPlanId);
        deleteWrapper.eq(TeachplanMedia::getCourseId, courseId);
        deleteWrapper.eq(TeachplanMedia::getMediaId, mediaId);
        teachplanMediaMapper.delete(deleteWrapper);
    }
}
