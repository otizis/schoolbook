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