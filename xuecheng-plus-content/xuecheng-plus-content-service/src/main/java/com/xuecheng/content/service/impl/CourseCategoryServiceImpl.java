package com.xuecheng.content.service.impl;

import com.xuecheng.content.mapper.CourseCategoryMapper;
import com.xuecheng.content.model.dto.CourseCategoryTreeDto;
import com.xuecheng.content.service.CourseCategoryService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Slf4j
@Service
public class CourseCategoryServiceImpl implements CourseCategoryService {

    @Resource
    CourseCategoryMapper courseCategoryMapper;

    @Override
    public List<CourseCategoryTreeDto> queryTreeNodes(String id) {
        // 调用mapper递归查询出分类信息
        List<CourseCategoryTreeDto> treeNodes = courseCategoryMapper.selectTreeNodes(id);

        // 找到每个结点的子结点，最终封装成List<CourseCategoryTreeDto>
        // 先将List转为Map，key就是结点的id，value就是CourseCategoryTreeDto对象目的就是为了方便从map获取结点
        // filter(item->!id.equals(item.getId()))用于排除根节点
        Map<String, CourseCategoryTreeDto> map = treeNodes.stream().filter(item -> !id.equals(item.getId())).collect(Collectors.toMap(key -> key.getId(), value -> value, (key1, key2) -> key2));

        // 最终返回的list
        List<CourseCategoryTreeDto> categoryTreeDtoList = new ArrayList<>();

        // 依次遍历每个元素,排除根节点
        treeNodes.stream().filter(item -> !id.equals(item.getId())).forEach(item->{
            // 当前结点是第一级结点，将其加入List [id是根节点]
            if(item.getParentid().equals(id)) {
                categoryTreeDtoList.add(item);
            }
            // 当前结点不是第一级结点，则找到当前节点的父节点
            CourseCategoryTreeDto courseCategoryTreeDto = map.get(item.getParentid());
            if(courseCategoryTreeDto != null) {
                // 父节点courseCategoryTreeDto的孩子数组为空，则new一个数组
                if(courseCategoryTreeDto.getChildrenTreeNodes() == null) {
                    courseCategoryTreeDto.setChildrenTreeNodes(new ArrayList<CourseCategoryTreeDto>());
                }
                //下边开始往ChildrenTreeNodes属性中放子节点
                courseCategoryTreeDto.getChildrenTreeNodes().add(item);
            }
        });

        return categoryTreeDtoList;
    }
}
