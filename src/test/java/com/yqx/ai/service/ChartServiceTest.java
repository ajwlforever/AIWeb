package com.yqx.ai.service;


import com.yqx.ai.model.entity.Chart;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import javax.annotation.Resource;
import java.math.BigInteger;
import java.util.Date;

@SpringBootTest
public class ChartServiceTest {
    
    @Resource
    ChartService chartService;

    @Resource
    UserService userService;
    @Test
    public void addChartTest(){
      //  String realPath=req.getSession().getServletContext().getRealPath("/uploadFile/");

        Chart chart = new Chart();
        chart.setGoal("分析数据");
        chart.setName("chart1");
        chart.setDataUrl("localhost:8101...");
        chart.setChartType("地图");
        chart.setGenChart("Sdsda");
        chart.setGetResult("Sdsda");
        String longAsString = "1706478277081608193";
        BigInteger bigInteger = new BigInteger(longAsString);
        long userId = bigInteger.longValue();
        chart.setUserId(userId);
        chart.setCreateTime(new Date());
        chartService.save(chart);

        
    }
}
