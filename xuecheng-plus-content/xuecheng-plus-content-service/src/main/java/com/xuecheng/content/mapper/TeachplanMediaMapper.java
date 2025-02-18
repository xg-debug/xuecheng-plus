package com.xuecheng.content.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.xuecheng.content.model.po.TeachplanMedia;
import org.apache.ibatis.annotations.Delete;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author xg
 */
public interface TeachplanMediaMapper extends BaseMapper<TeachplanMedia> {
    /**
     * 根据课程计划id删除媒资信息
     * @param teachplanId 课程计划id
     * @return
     */
    @Delete("delete from teachplan_media where teachplan_id = #{teachplanId}")
    public int deleteByTeachplanId(Long teachplanId);
}
