package com.yqx.ai.mapper;

import com.yqx.ai.model.entity.Prompts;
import com.yqx.ai.service.PromptsService;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import javax.annotation.Resource;
import java.util.Date;

@SpringBootTest
public class PromptsTest {

    @Resource
    private PromptsService promptsService;

    @Test
    public void saveTest() throws Exception {
        Prompts prompts = new Prompts();
        prompts.setId(2);
        prompts.setName("数据分析助手");
        prompts.setContent("aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa");
        prompts.setUser_id(1);
        prompts.setCreate_time(new Date());
        promptsService.save(prompts);
    }
}
