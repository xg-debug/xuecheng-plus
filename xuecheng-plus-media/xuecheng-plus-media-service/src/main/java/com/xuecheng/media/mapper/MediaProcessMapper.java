package com.xuecheng.media.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.xuecheng.media.model.po.MediaProcess;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import java.util.List;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author itcast
 */
public interface MediaProcessMapper extends BaseMapper<MediaProcess> {
    /**
     * @description 根据分片参数（总分片数 shardTotal 和当前分片索引 shardIndex），查询当前执行器需要处理的待处理任务列表。
     * @param shardTotal  总分片数（等同于存活执行器数量）。
     * @param shardIndex  当前执行器的分片索引（从 0 开始）。
     * @param count 单次拉取的任务数量（避免一次性处理过多任务）。
     * @return java.util.List<com.xuecheng.media.model.po.MediaProcess>
     */
    @Select("select * from media_process mp where mp.id % #{shardTotal} = #{shardIndex} and (mp.status = '1' or mp.status = '3') and mp.fail_count < 3 limit #{count}")
    List<MediaProcess> selectListByShardIndex(@Param("shardTotal") int shardTotal, @Param("shardIndex") int shardIndex, @Param("count") int count);

    /**
     * 开启一个任务
     * @param id 任务id
     * @return 更新记录数
     */
    @Update("update media_process m set m.status='4' where (m.status='1' or m.status='3') and m.fail_count<3 and m.id=#{id}")
    int startTask(@Param("id") long id);
}
