package com.xuecheng.content.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.xuecheng.content.model.dto.TeachplanDto;
import com.xuecheng.content.model.po.Teachplan;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * <p>
 * 课程计划 Mapper 接口
 * </p>
 *
 * @author xg
 */
public interface TeachplanMapper extends BaseMapper<Teachplan> {

    /**
     * @description 查询某课程的课程计划，组成树型结构
     * @param courseId
     * @return com.xuecheng.content.model.dto.TeachplanDto
     */
    public List<TeachplanDto> selectTreeNodes(Long courseId);

    /**
     * @description 获取某个课程的课程计划的最大排序号
     * @param courseId 课程标识
     * @return
     */
    public int getMaxOrderBy(Long courseId);

    /**
     *
     * @param courseId
     * @param grade
     * @param orderby
     * @return
     */
    public Teachplan findNext(@Param("courseId")Long courseId, @Param("grade")Integer grade, @Param("orderby")Integer orderby);

    /**
     *
     * @param courseId
     * @param grade
     * @param orderby
     * @return
     */
    public Teachplan findPrev(@Param("courseId")Long courseId, @Param("grade")Integer grade, @Param("orderby")Integer orderby);
}
