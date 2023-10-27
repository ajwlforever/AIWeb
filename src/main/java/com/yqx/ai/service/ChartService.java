package com.yqx.ai.service;

import com.yqx.ai.model.entity.Chart;
import com.baomidou.mybatisplus.extension.service.IService;
import org.springframework.web.multipart.MultipartFile;

/**
* @author ajwlforever
* @description 针对表【chart(图表信息表)】的数据库操作Service
* @createDate 2023-09-13 21:44:18
*/
public interface ChartService extends IService<Chart> {

    public void sendMsgToQueue(String msg);

    public void saveFile(MultipartFile file, String path);
}
