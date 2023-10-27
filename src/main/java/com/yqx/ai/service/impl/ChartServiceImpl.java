package com.yqx.ai.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.yqx.ai.model.entity.Chart;
import com.yqx.ai.service.ChartService;
import com.yqx.ai.manager.ServiceMessageSender;
import com.yqx.ai.mapper.ChartMapper;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;

/**
* @author ajwlforever
* @description 针对表【chart(图表信息表)】的数据库操作Service实现
* @createDate 2023-09-13 21:44:18
*/
@Service
public class ChartServiceImpl extends ServiceImpl<ChartMapper, Chart>
    implements ChartService {
    @Resource
    private ServiceMessageSender serviceMessageSender; // seder
    public void sendMsgToQueue(String msg){
        serviceMessageSender.sendMessage(msg);
    }

    @Override
    public void saveFile(MultipartFile file, String path) {

    }


}




