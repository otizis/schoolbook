package com.dzsb.util.schoolbook;

public class SeqUtil
{
    public static void printIndex()
    {
        System.out.println("type1_min:" + type1_min);
        System.out.println("type2_min:" + type2_min);
        System.out.println("type3_min:" + type3_min);
        System.out.println("type52_min:" + type52_min);
    }

    /**
     * 课本的现有最大id
     */
    public static int type1_min = 0;
    
    /**
     * <b> 运行前需要查询最新的值</b>
     * <code>SELECT max(node_id),node_type FROM t_schoolbook_content_r group by node_type;</code>
     * 2类别数据库中最大的值，作为新增用的最小值
     */
    public static int type2_min = 0;
    
    /**
     * <b>运行前需要查询最新的值</b>
     * <code>SELECT max(node_id),node_type FROM t_schoolbook_content_r group by node_type;</code>
     * 3类别数据库中最大的值，作为新增用的最小值
     */
    public static int type3_min = 0;
    
    /**
     * <b> 运行前需要查询最新的值</b>
     * <code>SELECT max(node_id),node_type FROM t_schoolbook_content_r group by node_type;</code>
     * 52类别数据库中最大的值，作为新增用的最小值
     */
    public static int type52_min = 0;
    
    /**
     * <b> 运行前需要查询最新的值</b>
     * <code>SELECT max(order_num) FROM t_schoolbook_content_r ;</code>
     * 排序id
     */
    public static int type_order_num_min = 0;
    
    /**
     * 累加计数
     */
    public static int getNextByType(int nodeType)
    {
        if (nodeType == Constant.BOOK_NODE_TYPE_UNIT)
        {
            type2_min++;
            return type2_min;
        }
        else if (nodeType == Constant.BOOK_NODE_TYPE_CHAPTER)
        {
            type3_min++;
            return type3_min;
        }
        else if (nodeType == Constant.BOOK_NODE_TYPE_FOOT)
        {
            type52_min++;
            return type52_min;
        }
        else if (nodeType == Constant.BOOK_NODE_TYPE_BOOK)
        {
            type1_min++;
            return type1_min;
        }
        else if (nodeType == Constant.BOOK_NODE_TYPE_ORDER_NUM)
        {
            type_order_num_min++;
            return type_order_num_min;
        }
        System.err.println("error getNextByType:" + nodeType);
        return -1;
    }
}
