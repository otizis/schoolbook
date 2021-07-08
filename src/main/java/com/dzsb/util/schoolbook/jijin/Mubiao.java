package com.dzsb.util.schoolbook.jijin;

import cn.hutool.core.thread.ThreadUtil;
import cn.hutool.core.util.NumberUtil;
import cn.hutool.http.HttpUtil;
import cn.hutool.json.JSONUtil;
import lombok.Data;

@Data
public class Mubiao
{
    String name;
    String[] code;
    int[] percent;

    public void printToday(){
        System.out.println(name + ",查询中。。");
        int length = code.length;
        double tmp = 0;
        int tmpTotal = 0;
        for (int i = 0; i < length; i++)
        {
            Result result = getResult(code[i]);
            Double gszzl = result.getGszzl();
            tmp += (gszzl * percent[i]);
            tmpTotal += percent[i];
            ThreadUtil.safeSleep(100);
        }
        System.out.println(name + " : " + NumberUtil.roundStr(tmp / tmpTotal ,3) + "%");
    }

    private  Result getResult(String code)
    {
        long l = System.currentTimeMillis() / 1000;

        String s = HttpUtil.get("https://fundgz.1234567.com.cn/js/"+code+".js?_="+l);

        s = s.replace("jsonpgz(", "").replace(");", "");

        return JSONUtil.toBean(s, Result.class);
    }
}
