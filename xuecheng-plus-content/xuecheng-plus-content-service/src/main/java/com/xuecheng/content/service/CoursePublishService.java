package com.xuecheng.content.service;

import com.xuecheng.content.model.dto.CoursePreviewDto;

/**
 * @description 课程预览、发布接口
 */
public interface CoursePublishService {

    /**
     * @description 获取课程预览信息
     * @param courseId 课程id
     * @return com.xuecheng.content.model.dto.CoursePreviewDto
     */
    public CoursePreviewDto getCoursePreviewInfo(Long courseId);

    /**
     * @description 提交审核
     * @param courseId  课程id
     * @return void
     */
    public void commitAudit(Long companyId, Long courseId);

    /**
     * 课程发布
     * @param companyId
     * @param courseId
     */
    public void publish(Long companyId,Long courseId);

}
