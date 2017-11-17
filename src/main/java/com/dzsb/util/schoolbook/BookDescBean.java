package com.dzsb.util.schoolbook;

/**
 * 分册标记
 * 
 * @author jaxer
 * @version [版本号, 2016年9月7日]
 * @see [相关类/方法]
 * @since [产品/模块版本]
 */
public class BookDescBean
{
    
    String desc;
    
    /**
     * 该年级的课本数量，用来实现分册标记，是上下册还是全一册
     */
    int num;
    
    public String getDesc()
    {
        return desc;
    }
    
    public int getNum()
    {
        return num;
    }
    
    public boolean isOneFac()
    {
        return (num == 1);
    }
    
    public void setDesc(String desc)
    {
        this.desc = desc;
    }
    
    public void setNum(int num)
    {
        this.num = num;
    }
    
    int firstBookId;
    
    /**
     * 设置bookid，第一次设置进来的按顺序认为是上册
     * 
     * @param node_id [参数说明]
     * 
     * @return void [返回类型说明]
     * @exception throws [违例类型] [违例说明]
     * @see [类、类#方法、类#成员]
     */
    public void setFirstBookId(int node_id)
    {
        if (firstBookId != 0)
        {
            return;
        }
        this.firstBookId = node_id;
    }
    
}
