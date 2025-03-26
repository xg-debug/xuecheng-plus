package com.xuecheng.learning.service.impl;

import com.xuecheng.base.exception.XueChengPlusException;
import com.xuecheng.base.model.RestResponse;
import com.xuecheng.content.model.po.CoursePublish;
import com.xuecheng.learning.feignclient.ContentServiceClient;
import com.xuecheng.learning.feignclient.MediaServiceClient;
import com.xuecheng.learning.model.dto.XcCourseTablesDto;
import com.xuecheng.learning.service.LearningService;
import com.xuecheng.learning.service.MyCourseTablesService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

@Slf4j
@Service
public class LearningServiceImpl implements LearningService {

    @Resource
    private ContentServiceClient contentServiceClient;

    @Resource
    private MyCourseTablesService myCourseTablesService;

    @Resource
    private MediaServiceClient mediaServiceClient;

    @Override
    public RestResponse<String> getVideo(String userId, Long courseId, Long teachplanId, String mediaId) {
        // 查询课程信息
        CoursePublish coursepublish = contentServiceClient.getCoursePublish(courseId);
        if(coursepublish == null) {
            XueChengPlusException.cast("课程信息不存在");
        }
        // 校验学习资格

        // 如果登录
        if(StringUtils.isNotEmpty(userId)) {
            // 判断是否选课，根据选课情况判断学习资格
            XcCourseTablesDto courseTablesDto = myCourseTablesService.getLearningStatus(userId, courseId);
            String learnStatus = courseTablesDto.getLearnStatus();
            if("702001".equals(learnStatus)) { // 正常学习
                return mediaServiceClient.getPlayUrlByMediaId(mediaId);
            } else if("702003".equals(learnStatus)) {
                RestResponse.validfail("您的选课已过期，需要申请续期或中心支付。");
            }
        }

        // 未登录或未选课：判断是否收费
        String charge = coursepublish.getCharge();
        if("201000".equals(charge)) { // 免费课程可以正常学习
            return mediaServiceClient.getPlayUrlByMediaId(mediaId);
        }

        // 未登录：且课程收费
        return RestResponse.validfail("请购买课程后继续学习!");
    }
}
