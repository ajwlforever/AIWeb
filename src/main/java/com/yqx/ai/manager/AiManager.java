package com.yqx.ai.manager;


import com.yqx.ai.common.XfunListener;
import com.yqx.ai.common.ErrorCode;
import com.yqx.ai.config.dto.Xfun.MsgDTO;
import com.yqx.ai.exception.BusinessException;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

/**
 * 提供AI的服务
 */
@Service
public class AiManager {


    @Resource
    private XfunListener xfunListener;  // 实现发送接受消息的websockect

    private String analysisPrompt = "你是一个数据分析师和前端开发专家，接下来我会按照以下固定格式给你提供内容：  1.分析目标： {数据分析的需求或者目标}  2.原始数据：{csv格式的原始数据，用,作为分隔符}  \n" +
            "\n" +
            "你是一个数据分析师和前端开发专家，接下来我会按照以下固定格式给你提供内容：  1.分析目标： {数据分析的需求或者目标}  2.原始数据：{csv格式的原始数据，用,作为分隔符}  \n" +
            "\n" +
            "请多维度，多角度给出一份大于100字的详细数据分析；\n";

    private String promptsPath;
    public String doDataAnalysis(String goal, String csvData)  {
         String question = analysisPrompt + " 1."+ goal + "2. "+ csvData;
         List<MsgDTO> msgs = new ArrayList<>();
         MsgDTO msg = new MsgDTO();
         msg.setContent(question);
         msgs.add(msg);
         msg.setRole("user");
        //8位随机数
        String random = String.valueOf((int)((Math.random()*9+1)*10000000));
         // 发送问题给星火
        // 安排一个新线程处理这个问题
        // 1. 初始化，表明是一个新的对话
        xfunListener.init_chat();
        XfunListener recv = null;
        try {
            recv = xfunListener.sendMsg(random, msgs, xfunListener);
            int cnt = 30;
            //最长等待30S
            while (!recv.isFinished() && cnt > 0){
                Thread.sleep(1000);  //休息1S
                cnt--;
            }
            if(cnt == 0){
                return null;
            }

            String answer = recv.getAnswer();
            //返回答案
            return answer;
        } catch (Exception e) {
            throw new BusinessException(ErrorCode.SYSTEM_ERROR,"数据分析错误");
        }finally {
        }


    }


    public String genPrompt(String charType){
         String res = "\n" +
                "\n" +
                "你是一个数据分析师和前端开发专家，接下来我会按照以下固定格式给你提供内容： \n" +
                "\n" +
                "分析需求：\n" +
                "\n" +
                " {数据分析的需求或者目标} \n" +
                "\n" +
                "原始数据：\n" +
                "\n" +
                " {csv格式的原始数据，用,作为分隔符} \n" +
                "\n" +
                "请根据这两部分内容，按照以下指定格式生成内容（此外不要输出任何多余的开头、结尾、注释：1. {前端 Echarts V5 的 option 配置对象的json格式代码，" +
                "请使用"+charType+","+
                 "合理地将数据进行可视化 ，不要生 成任何多余的内容，比如注释}2. {明确的数据分析结论、越详细越好，不要生成多余的注释}";

         return res;
    }
    public List<String> doChat(String goal, String chartType, String csvData){
        StringBuilder question = new StringBuilder();
        String p = genPrompt(chartType);

        question.append(p).append('\n');
        question.append("分析需求:");
        question.append(goal).append('\n');

        question.append("原始数据：").append('\n');
        question.append(csvData).append('\n');
        //8位随机数
        String random = String.valueOf((int)((Math.random()*9+1)*10000000));

        List<MsgDTO> msgs = new ArrayList<>();
        MsgDTO msgDTO = new MsgDTO( );
        msgDTO.setRole("user");
        msgDTO.setContent(question.toString());
        msgDTO.setIndex(0);
        msgs.add(msgDTO);

        // 发送消息
        try {
            xfunListener.init_chat();
            XfunListener webSocket = xfunListener.sendMsg(random , msgs, xfunListener );
            //等待weSocked返回消息
            int cnt = 30;
            //最长等待30S
            while (!webSocket.isFinished() && cnt > 0){
                Thread.sleep(1000);  //休息1S
                cnt--;
            }
            if(cnt == 0){
                return null;
            }
            String answer = webSocket.getAnswer();
            System.out.println("answer:"+answer);
            //answer 分开
            return parseAnswer(answer);

        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public String testChat(String question){

        //8位随机数
        String random = String.valueOf((int)((Math.random()*9+1)*10000000));
        List<MsgDTO> msgs = new ArrayList<>();
        MsgDTO msgDTO = new MsgDTO( );
        msgDTO.setRole("user");
        msgDTO.setContent(question);
        msgDTO.setIndex(0);
        msgs.add(msgDTO);


        try {
            // 获取接受消息的webSoeckt
            xfunListener.init_chat();
            XfunListener webSocket = xfunListener.sendMsg(random, msgs, xfunListener);
            //等待weSocked返回消息 , 这是一个笨笨的处理方法。
            int cnt = 30;
            //最长等待30S
            while (!webSocket.isFinished() && cnt > 0){
                Thread.sleep(1000);  //休息1S
                cnt--;
            }
            if(cnt == 0){
                return null;
            }

            String answer = webSocket.getAnswer();
            //返回答案
            return answer;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }


    public static List<String> parseAnswer(String answer){
        // answer转化为分析答案+图表代码
        List<String> ans = new ArrayList<>();
        String[] strs = answer.split("```");
        String genResult = "";
        if(strs.length>=3){
            genResult = strs[2];
        }

        String genChart = strs[1];
        //genChart去掉前4个字符
        genChart = genChart.substring(4);
        genChart =  genChart;
        ans.add(genChart);
        ans.add(genResult);
        return ans;
    }

    public static void main(String[] args) {
        AiManager ai = new AiManager();
        ai.xfunListener =  XfunListener.builder()
             .apiKey("c2140c8f6ee2f769a810ec897acaeef9")
               .apiSecret("ODdiMGNiOTQ5MGI4ZTQ1NGJiMGU1Mzc5")
               .appid("d61943f8")
                .hostUrl("https://spark-api.xf-yun.com/v2.1/chat")
               .build();

        System.out.println(ai.testChat("你好啊！"));
    }


}
