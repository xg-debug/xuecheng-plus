package com.xuecheng.learning.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.xuecheng.base.exception.XueChengPlusException;
import com.xuecheng.base.model.PageResult;
import com.xuecheng.content.model.po.CoursePublish;
import com.xuecheng.learning.feignclient.ContentServiceClient;
import com.xuecheng.learning.mapper.XcChooseCourseMapper;
import com.xuecheng.learning.mapper.XcCourseTablesMapper;
import com.xuecheng.learning.model.dto.MyCourseTableParams;
import com.xuecheng.learning.model.dto.XcChooseCourseDto;
import com.xuecheng.learning.model.dto.XcCourseTablesDto;
import com.xuecheng.learning.model.po.XcChooseCourse;
import com.xuecheng.learning.model.po.XcCourseTables;
import com.xuecheng.learning.service.MyCourseTablesService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.time.LocalDateTime;
import java.util.List;

@Slf4j
@Service
public class MyCourseTablesServiceImpl implements MyCourseTablesService {

    @Resource
    private ContentServiceClient contentServiceClient;

    @Resource
    private XcChooseCourseMapper chooseCourseMapper;

    @Resource
    private XcCourseTablesMapper courseTablesMapper;

    @Transactional
    @Override
    public XcChooseCourseDto addChooseCourse(String userId, Long courseId) {
        // 1.查询课程信息
        CoursePublish coursepublish = contentServiceClient.getCoursepublish(courseId);
        // 课程收费标准
        String charge = coursepublish.getCharge();
        // 选课记录
        XcChooseCourse chooseCourse = null;
        if("201000".equals(charge)) { // 免费
            // 添加免费课程
            chooseCourse = addFreeCourse(userId, coursepublish);
            // 添加到我的课程表
            XcCourseTables xcCourseTables = addCourseTables(chooseCourse);
        } else {
            //添加收费课程
            chooseCourse  = addChargeCourse(userId, coursepublish);
        }

        XcChooseCourseDto chooseCourseDto = new XcChooseCourseDto();
        BeanUtils.copyProperties(chooseCourse,chooseCourseDto);
        //获取学习资格
        XcCourseTablesDto xcCourseTablesDto = getLearningStatus(userId, courseId);
        chooseCourseDto.setLearnStatus(xcCourseTablesDto.getLearnStatus());
        return chooseCourseDto;
    }

    @Override
    public XcCourseTablesDto getLearningStatus(String userId, Long courseId) {
        // 1.查询我的课程表
        XcCourseTables courseTables = getCourseTables(userId, courseId);
        if(courseTables == null) {
            XcCourseTablesDto courseTablesDto = new XcCourseTablesDto();
            //没有选课或选课后没有支付
            courseTablesDto.setLearnStatus("702002");
            return courseTablesDto;
        }
        XcCourseTablesDto courseTablesDto = new XcCourseTablesDto();
        BeanUtils.copyProperties(courseTables, courseTablesDto);
        // 2.判断是否过期，true过期，false未过期
        boolean isExpires = courseTables.getValidtimeEnd().isBefore(LocalDateTime.now());
        if(!isExpires) {
            // 正常学习
            courseTablesDto.setLearnStatus("702001");
            return courseTablesDto;
        } else {
            // 已过期
            courseTablesDto.setLearnStatus("702003");
            return courseTablesDto;
        }
    }

    @Override
    public boolean saveChooseCourseSuccess(String chooseCourseId) {
        // 1.根据选课id查询课表
        XcChooseCourse chooseCourse = chooseCourseMapper.selectById(chooseCourseId);
        if(chooseCourse == null) {
            log.debug("接收购买课程的消息,根据选课id从数据库找不到选课记录,选课id:{}", chooseCourseId);
            return false;
        }
        // 选课状态
        String status = chooseCourse.getStatus();
        // 只有未支付才更新为已支付
        if("701002".equals(status)) {
            // 更新选课记录的状态为已支付
            chooseCourse.setStatus("701001");
            int u = chooseCourseMapper.updateById(chooseCourse);
            if(u <= 0) {
                log.debug("添加选课记录失败:{}", chooseCourse);
                XueChengPlusException.cast("添加选课记录失败");
            }
            // 向我的课程表插入记录
            XcCourseTables xcCourseTables = addCourseTables(chooseCourse);
        }
        return true;
    }

    @Override
    public PageResult<XcCourseTables> mycourestabls(MyCourseTableParams params) {
        //页码
        long pageNo = params.getPage();
        //每页记录数,固定为4
        long pageSize = 4;
        //分页条件
        Page<XcCourseTables> page = new Page<>(pageNo, pageSize);
        //根据用户id查询
        String userId = params.getUserId();
        LambdaQueryWrapper<XcCourseTables> queryWrapper = new LambdaQueryWrapper<XcCourseTables>().eq(XcCourseTables::getUserId, userId);
        //分页查询
        Page<XcCourseTables> pageResult = courseTablesMapper.selectPage(page, queryWrapper);
        List<XcCourseTables> records = pageResult.getRecords();

        //记录总数
        long total = pageResult.getTotal();
        PageResult<XcCourseTables> courseTablesResult = new PageResult<>(records, total, pageNo, pageSize);
        return courseTablesResult;
    }

    // 添加免费课程,免费课程加入选课记录表、我的课程表
    public XcChooseCourse addFreeCourse(String userId, CoursePublish coursepublish) {
//        XcChooseCourse xcChooseCourse = chooseCourseMapper.selectOne(new LambdaQueryWrapper<XcChooseCourse>().eq(XcChooseCourse::getUserId, userId).eq(XcChooseCourse::getCourseId, coursepublish.getId()));
//        if(xcChooseCourse != null) {
//            XueChengPlusException.cast("请勿重复选课");
//        }
//        // 选课记录完成且未过期可以添加课程表
//        XcChooseCourse chooseCourse = null;
//        String charge = coursepublish.getCharge();
//        if("201000".equals(charge)) {
//            chooseCourseMapper.insert();
//            courseTablesMapper.insert();
//        }
//        return chooseCourse;
        return null;
    }

    // 添加收费课程
    public XcChooseCourse addChargeCourse(String userId,CoursePublish coursePublish){
        // 如果存在待支付交易记录直接返回
        LambdaQueryWrapper<XcChooseCourse> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(XcChooseCourse::getUserId, userId);
        queryWrapper.eq(XcChooseCourse::getCourseId, coursePublish.getId());
        queryWrapper.eq(XcChooseCourse::getOrderType, "700002"); // 收费订单
        queryWrapper.eq(XcChooseCourse::getStatus, "701002"); // 待支付
        List<XcChooseCourse> chooseCourseList = chooseCourseMapper.selectList(queryWrapper);
        if(chooseCourseList != null && chooseCourseList.size() > 0) {
            return chooseCourseList.get(0);
        }

        // 不存在待支付记录：添加收费课程到选课记录表
        XcChooseCourse xcChooseCourse = new XcChooseCourse();
        xcChooseCourse.setCourseId(coursePublish.getId());
        xcChooseCourse.setCourseName(coursePublish.getName());
        xcChooseCourse.setCoursePrice(coursePublish.getPrice());
        xcChooseCourse.setUserId(userId);
        xcChooseCourse.setCompanyId(coursePublish.getCompanyId());
        xcChooseCourse.setOrderType("700002"); // 收费课程
        xcChooseCourse.setCreateDate(LocalDateTime.now());
        xcChooseCourse.setStatus("701002"); // 待支付
        xcChooseCourse.setValidtimeStart(LocalDateTime.now());
        xcChooseCourse.setValidtimeEnd(LocalDateTime.now().plusDays(coursePublish.getValidDays()));
        chooseCourseMapper.insert(xcChooseCourse);
        return xcChooseCourse;
    }

    /**
     * @description 添加到我的课程表
     * @param xcChooseCourse 选课记录
     * @return
     */
    public XcCourseTables addCourseTables(XcChooseCourse xcChooseCourse) {
        // 选课记录完成且未过期，可以添加到课程表
        String status = xcChooseCourse.getStatus();
        if(!"701001".equals(status)) {
            XueChengPlusException.cast("选课未成功，无法添加到课程表");
        }
        // 查询课表
        XcCourseTables xcCourseTables = getCourseTables(xcChooseCourse.getUserId(), xcChooseCourse.getCourseId());
        if(xcCourseTables != null) {
            return xcCourseTables;
        }

        // 没有则添加
        XcCourseTables xcCourseTablesNew = new XcCourseTables();
        xcCourseTablesNew.setChooseCourseId(xcChooseCourse.getId());
        xcCourseTablesNew.setUserId(xcChooseCourse.getUserId());
        xcCourseTablesNew.setCourseId(xcChooseCourse.getCourseId());
        xcCourseTablesNew.setCompanyId(xcChooseCourse.getCompanyId());
        xcCourseTablesNew.setCourseName(xcChooseCourse.getCourseName());
        xcCourseTablesNew.setCreateDate(xcChooseCourse.getCreateDate());
        xcCourseTablesNew.setValidtimeStart(xcChooseCourse.getValidtimeStart());
        xcCourseTablesNew.setValidtimeEnd(xcChooseCourse.getValidtimeEnd());
        xcCourseTablesNew.setCourseType(xcChooseCourse.getOrderType());


        courseTablesMapper.insert(xcCourseTablesNew);
        return xcCourseTablesNew;
    }

    /**
     * @description 根据课程和用户查询我的课程表中某一门课程
     * @param userId
     * @param courseId
     * @return
     */
    public XcCourseTables getCourseTables(String userId, Long courseId) {
        XcCourseTables xcCourseTables = courseTablesMapper.selectOne(new LambdaQueryWrapper<XcCourseTables>().eq(XcCourseTables::getUserId, userId).eq(XcCourseTables::getCourseId, courseId));
        return xcCourseTables;
    }

}
