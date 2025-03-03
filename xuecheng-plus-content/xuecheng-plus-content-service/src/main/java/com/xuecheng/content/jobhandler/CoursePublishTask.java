package com.xuecheng.content.jobhandler;

import com.xuecheng.messagesdk.model.po.MqMessage;
import com.xuecheng.messagesdk.service.MessageProcessAbstract;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class CoursePublishTask extends MessageProcessAbstract {

    // 课程发布任务处理
    @Override
    public boolean execute(MqMessage mqMessage) {
        return false;
    }
}
