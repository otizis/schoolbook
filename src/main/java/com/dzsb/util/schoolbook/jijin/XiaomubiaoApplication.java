package com.dzsb.util.schoolbook.jijin;


public class XiaomubiaoApplication
{
    public static void main(String[] args)
    {
        Mubiao mubiao = new Mubiao();
        mubiao.setName("2118");
        mubiao.setCode( new String[]{"001632","008682","011611","160725","501006"});
        mubiao.setPercent(new int[]{20,20,20,20,20});
        mubiao.printToday();


        mubiao = new Mubiao();
        mubiao.setName("快012");
        mubiao.setCode( new String[]{"001549","005224","009052","010696",});
        mubiao.setPercent(new int[]{25,25,25,25});
        mubiao.printToday();

        mubiao = new Mubiao();
        mubiao.setName("快009");
        mubiao.setCode( new String[]{"001549","005224","100032","217017"});
        mubiao.setPercent(new int[]{20,20,20,40});
        mubiao.printToday();

        mubiao = new Mubiao();
        mubiao.setName("快006");
        mubiao.setCode( new String[]{"001632","005064","100032","160716"});
        mubiao.setPercent(new int[]{8951,8071,5047,4787});
        mubiao.printToday();

        mubiao = new Mubiao();
        mubiao.setName("2046");
        mubiao.setCode( new String[]{"001632","004746","005064","100032","161035"});
        mubiao.setPercent(new int[]{2498,3046,2877,3210,2922});
        mubiao.printToday();
    }


}
