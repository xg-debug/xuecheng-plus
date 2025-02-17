package com.xuecheng.content.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.xuecheng.content.mapper.TeachplanMapper;
import com.xuecheng.content.model.dto.SaveTeachplanDto;
import com.xuecheng.content.model.dto.TeachplanDto;
import com.xuecheng.content.model.po.Teachplan;
import com.xuecheng.content.service.TeachplanService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Slf4j
@Service
public class TeachplanServiceImpl implements TeachplanService {

    @Autowired
    private TeachplanMapper teachplanMapper;

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

            teachplanNew.setOrderby(maxOrderBy + 1);
            BeanUtils.copyProperties(teachplanDto, teachplanNew);

            teachplanMapper.insert(teachplanNew);
            // 新增课程之后需要请求
        }
    }

//    private int getTeachplanCount(long courseId, long parentId) {
//        LambdaQueryWrapper<Teachplan> queryWrapper = new LambdaQueryWrapper<>();
//        queryWrapper.eq(Teachplan::getCourseId, courseId);
//        queryWrapper.eq(Teachplan::getParentid, parentId);
//        Integer count = teachplanMapper.selectCount(queryWrapper);
//        return count;
//    }
}
