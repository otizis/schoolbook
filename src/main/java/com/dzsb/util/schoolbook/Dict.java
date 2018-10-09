package com.dzsb.util.schoolbook;

public class Dict
{
    private static String[] subject = new String[] {"语文", "数学", "英语", "历史", "政治", "地理", "生物", "化学", "物理", "思想品德",};
    
    private static String[] subjectId = new String[] {"10001", "10002", "10003", "10004", "10005", "10006", "10007",
        "10008", "10009", "10010",};
    
    private static String[] grade = new String[] {"一年级", "二年级", "三年级", "四年级", "五年级", "六年级", "七年级", "八年级", "九年级", "高一",
        "高二", "高三",};
    
    private static String[] gradeId = new String[] {"10101", "10102", "10103", "10104", "10105", "10106", "10107",
        "10108", "10109", "10110", "10111", "10112",};
    
    private static String[] publish = new String[] {"人教版", "人教pep版", "沪粤版", "川教版", "北师大版",};
    
    private static String[] publishId = new String[] {"10201", "10202", "10203", "10204", "10205",};
    
    private static String[] fac = new String[] {"上册", "下册", "全一册"};
    
    private static String[] facId = new String[] {"10301", "10302", "10303"};
    
    public static String getGradeId(String string)
    {
        return get(grade, gradeId, string);
    }
    public static String getFacId(String string)
    {
        return get(fac, facId, string);
    }
    public static String getSubjectId(String string)
    {
        return get(subject, subjectId, string);
    }
    

    public static String getPublishId(String string)
    {
        return get(publish, publishId, string);
    }
    
    private static String get(String[] name, String[] id, String string)
    {
        if (string == null)
        {
            return "-1";
        }
        for (int i = 0; i < name.length; i++)
        {
            if (string.contains(name[i]))
            {
                return id[i];
            }
        }
        // System.out.println(string + "find null");
        return "-1";
    }
    
    public static void main(String[] args)
    {
        int i = 1002882;
        int y = 110;
        while (y < 152)
        {
            for (int j = 1; j < 17; j++)
            {
                System.out.println("(" + (++i) + "," + y + ",52,'课时"+j+"'),");
            }
            y++;
        }
    }
}
