package com.dzsb.util.schoolbook;

import java.util.ArrayList;
import java.util.List;

public class SchoolBookR
{
    // 子节点
    private List<SchoolBookR> childs = new ArrayList<SchoolBookR>();
    
    // 初始化时，设置该节点的直接点应该是什么开头的，是自己的开头加一个+号
    // 比如自己是+号开头的，那应该设置++作为此字段
    private String childStartwith;
    
    // 节点id
    private int node_id;
    
    private int order_num;
    // 节点名称
    private String node_name;
    
    // 父节点
    private SchoolBookR parent;
    
    // 书本的描述，只存在于书本节点
    private BookDescBean bookDesc = new BookDescBean();
    
    public BookDescBean getBookDesc()
    {
        return bookDesc;
    }

    public void setBookDesc(BookDescBean bookDesc)
    {
        bookDesc.setNum(bookDesc.getNum() + 1);
        bookDesc.setFirstBookId(this.node_id);
        this.bookDesc = bookDesc;
    }

    public SchoolBookR()
    {
        super();
    }

    public SchoolBookR(String node_name,SchoolBookR parent)
    {
        super();
        this.node_name = node_name;
        this.parent = parent;
    }

    /**
     * 添加子节点
     */
    public void addChild(String string)
    {
        if (string.startsWith(childStartwith + "+"))
        {
            if (childs.isEmpty())
            {
                System.err.println("数据异常存在跳级，行：" + string);
                throw new RuntimeException();
            }
            // 如果和自己下一层节点还要深，交给自己的最后一个子节点处理
            SchoolBookR schoolBookR = childs.get(childs.size() - 1);
            schoolBookR.addChild(string);
        }
        else
        {
            // 新节点
            SchoolBookR schoolBookR = new SchoolBookR();
            schoolBookR.setNode_name(string);
            schoolBookR.setChildStartwith(childStartwith + "+");
            schoolBookR.parent = this;
            childs.add(schoolBookR);
            
        }
    }
    
    /**
     * @return 返回 childStartwith
     */
    public String getChildStartwith()
    {
        return childStartwith;
    }
    
    /**
     * @return 返回 node_id
     */
    public int getNode_id()
    {
        return node_id;
    }
    
    /**
     * @return 返回 node_name
     */
    public String getNode_name()
    {
        return node_name.replaceAll("'", "\\\\'");
    }
    
    /**
     * @return 返回 node_type 节点类型，只有书本的节点类型是固定的，其他的类型需要根据是否有子节点判断是否是52末端。 否则按照书、单元、章、节下沉
     */
    public int getNode_type()
    {
        if (parent == null)
        {
            return Constant.BOOK_NODE_TYPE_BOOK;
        }
        if (childs.isEmpty())
        {
            return Constant.BOOK_NODE_TYPE_FOOT;
        }
        return parent.getNode_type() + 1;
    }
    
    /**
     * @return 返回 parent_id
     */
    public int getParent_id()
    {
        if (parent == null)
        {
            return 0;
        }
        return parent.node_id;
    }
    
    public int getOrderNum(){
        return this.order_num;
    }
    public void setOrderNum(int orderNum){
        this.order_num = orderNum;
    }
    
    /**
     * @param 对childStartwith进行赋值
     */
    public void setChildStartwith(String childStartwith)
    {
        this.childStartwith = childStartwith;
    }
    
    /**
     * @param 对node_id进行赋值
     */
    public void setNode_id(int node_id)
    {
        this.node_id = node_id;
    }
    
    /**
     * @param 对node_name进行赋值
     */
    public void setNode_name(String node_name)
    {
        node_name = node_name.replaceAll("\\+", "").trim();
        this.node_name = node_name;
    }
    
    /**
     * @return
     */
    @Override
    public String toString()
    {
        StringBuilder builder = new StringBuilder();
        builder.append("SchoolBookR [node_id=");
        builder.append(node_id);
        builder.append(", parent_id=");
        builder.append(getParent_id());
        builder.append(", node_type=");
        builder.append(getNode_type());
        builder.append(", node_name=");
        builder.append(node_name);
        builder.append(", childStartwith=");
        builder.append(childStartwith);
        builder.append(", child=");
        builder.append(childs);
        builder.append("]");
        return builder.toString();
    }
    
    public List<SchoolBookR> getChilds()
    {
        return childs;
    }
    
    public void setChilds(List<SchoolBookR> childs)
    {
        this.childs = childs;
    }
    
}
