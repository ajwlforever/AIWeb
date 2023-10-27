package com.yqx.ai.service;

import com.yqx.ai.manager.AiManager;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import javax.annotation.Resource;

@SpringBootTest
public class AIServiceTest {

    @Resource
    private AiManager aiManager;

    @Test
    public void analysisTest() throws Exception {
        String answer =
        aiManager.doDataAnalysis("用户量分析","1号 1人 2号 2人 3号 199人 4号 500人 6号 6000人");

        System.out.println(answer);

    }
}
