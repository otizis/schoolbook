package com.dzsb.util.schoolbook.zxxk;

import com.alibaba.fastjson.JSONArray;
import okhttp3.*;
import org.apache.commons.io.FileUtils;

import java.io.File;
import java.io.IOException;
import java.util.List;

public class ZxxkSpider
{

    static String url = "http://yw.zxxk.com/WebApi/ChildNodes";
    static OkHttpClient client = new OkHttpClient();


    public static void main(String[] args)
    {

        ZxxkSpider zxxkSpider = new ZxxkSpider();
        zxxkSpider.getBook("人教版语文高中选修系列","Class","4885");

        zxxkSpider.getBook("北师大版数学高中选修一","Class","4923");
        zxxkSpider.getBook("北师大版数学高中选修二","Class","4928");
        zxxkSpider.getBook("北师大版数学高中选修三","Class","4939");
        zxxkSpider.getBook("北师大版数学高中选修四","Class","4937");

        zxxkSpider.getBook("人教版英语高中选修六","Class","1481");
        zxxkSpider.getBook("人教版英语高中选修七","Class","1450");
        zxxkSpider.getBook("人教版英语高中选修八","Class","1451");
        zxxkSpider.getBook("人教版英语高中选修九","Class","1452");
        zxxkSpider.getBook("人教版英语高中选修十","Class","1453");
        zxxkSpider.getBook("人教版英语高中选修十一","Class","1588");

        zxxkSpider.getBook("人教版物理高中选修一","Class","4948");
        zxxkSpider.getBook("人教版物理高中选修二","Class","4952");
        zxxkSpider.getBook("人教版物理高中选修三","Class","4944");

        zxxkSpider.getBook("人教版化学高中选修一 化学与生活","Class","440");
        zxxkSpider.getBook("人教版化学高中选修二 化学与技术","Class","443");
        zxxkSpider.getBook("人教版化学高中选修三 物质结构与性质","Class","446");
        zxxkSpider.getBook("人教版化学高中选修四 化学反应原理","Class","449");
        zxxkSpider.getBook("人教版化学高中选修五 有机化学基础","Class","452");
        zxxkSpider.getBook("人教版化学高中选修六 实验化学","Class","455");

        zxxkSpider.getBook("人教版生物高中选修一 生物技术实践","Class","1249");
        zxxkSpider.getBook("人教版生物高中选修二 生物科学与社会","Class","1250");
        zxxkSpider.getBook("人教版生物高中选修三 现代生物科技专题","Class","1248");

        zxxkSpider.getBook("人教版政治高中必修4 生活与哲学","Class","1061");
        zxxkSpider.getBook("人教版政治高中选修1 科学社会主义常识","Class","1062");
        zxxkSpider.getBook("人教版政治高中选修2 经济学常识","Class","1063");
        zxxkSpider.getBook("人教版政治高中选修3 国家和国际组织常识","Class","1064");
        zxxkSpider.getBook("人教版政治高中选修4 科学思维常识","Class","1065");
        zxxkSpider.getBook("人教版政治高中选修5 生活中的法律常识","Class","1066");
        zxxkSpider.getBook("人教版政治高中选修6 公民道德与伦理常识","Class","1067");

        zxxkSpider.getBook("人教版历史高中选修一 历史上重大改革回眸","Class","957");
        zxxkSpider.getBook("人教版历史高中选修二 近代社会的民主思想与实践","Class","1637");
        zxxkSpider.getBook("人教版历史高中选修三 20世纪的战争与和平","Class","1633");
        zxxkSpider.getBook("人教版历史高中选修四 中外历史人物评说","Class","1641");
        zxxkSpider.getBook("人教版历史高中选修五 探索历史的奥秘","Class","1645");
        zxxkSpider.getBook("人教版历史高中选修六 世界文化遗产荟萃","Class","4989");

        zxxkSpider.getBook("人教版地理高中选修一 宇宙与地球","Class","988");
        zxxkSpider.getBook("人教版地理高中选修二 海洋地理","Class","4026");
        zxxkSpider.getBook("人教版地理高中选修三 旅游地理","Class","1000");
        zxxkSpider.getBook("人教版地理高中选修四 城乡规划","Class","1004");
        zxxkSpider.getBook("人教版地理高中选修五 自然灾害与防治","Class","1016");
        zxxkSpider.getBook("人教版地理高中选修六 环境保护","Class","1012");
        zxxkSpider.getBook("人教版地理高中选修七 地理信息技术应用","Class","1008");

    }

    private void getBook(String name , String dataType, String dataId)
    {
        List<Node> aClass = getChildNodesUntil(dataType, dataId, 0);

        StringBuilder sb = new StringBuilder(1024);
        sb.append("+");
        sb.append(name);
        sb.append("\n");
        for (Node node : aClass)
        {
            sb.append(node.fomartAllTree());
        }
        try
        {
            String data = sb.toString();
            System.out.println(data);
            FileUtils.writeStringToFile(new File(name+".txt"), data,"utf8");
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }
    }

    public List<Node> getChildNodesUntil(String dataType, String dataId, int level)
    {
        List<Node> aClass = getChildNodes(dataType, dataId, level + 1);

        for (Node node : aClass)
        {
            if (node.IsHasChildNode)
            {
                List<Node> childNodes = getChildNodesUntil(node.getDataType(), node.getNodeID(), level + 1);
                node.setChild(childNodes);
            }
        }
        return aClass;
    }

    public List<Node> getChildNodes(String dataType, String dataId, int level)
    {
        System.out.println(dataType+"<-dataType , dataid->"+dataId);
        FormBody.Builder builder = new FormBody.Builder();
        builder.add("dataType", dataType).add("dataId", dataId).add("departmentid", "0").add("channelid", "0");
        FormBody formBody = builder.build();
        Request.Builder post = new Request.Builder().url(url).post(formBody);
        Request build = post.build();
        Call call = client.newCall(build);
        try
        {
            Response execute = call.execute();
            ResponseBody body = execute.body();
            if (body == null)
            {
                return null;
            }
            String string = body.string();
            System.out.println("get content length :" + string.length());
            List<Node> nodes = JSONArray.parseArray(string, Node.class);
            for (Node node : nodes)
            {
                node.setLevel(level);
            }
            return nodes;
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }

        return null;


    }


}