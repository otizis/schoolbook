package com.dzsb.util.schoolbook;

import java.util.ArrayList;
import java.util.List;

public class SchoolBookNode
{
    // 子节点
    private List<SchoolBookNode> childs = new ArrayList<SchoolBookNode>();

    // 初始化时，设置该节点的直接点应该是什么开头的，是自己的开头加一个+号
    // 比如自己是+号开头的，那应该设置++作为此字段
    private String childStartwith;

    /**
     * 节点id
     */

    private String node_id;

    private int order_num;
    /**
     * 节点名称
     */

    private String node_name;

    /**
     * 额外描述信息，用€€分隔。如果没有分隔符，使用本行内容作为描述<br/>
     * 描述用于最后sql输出时匹配字典项，需要每本书列明，出版社，年级，上下册，科目。一一对应。
     */
    private String desc;

    /**
     * 父节点
     */
    private SchoolBookNode parent;


    public SchoolBookNode()
    {
        super();
    }

    public SchoolBookNode(String nodeName, SchoolBookNode parent)
    {
        super();
        this.node_name = nodeName;
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
            SchoolBookNode schoolBookNode = childs.get(childs.size() - 1);
            schoolBookNode.addChild(string);
        }
        else
        {
            // 新节点
            SchoolBookNode schoolBookNode = new SchoolBookNode();
            schoolBookNode.setNodeName(string);
            schoolBookNode.setChildStartwith(childStartwith + "+");
            schoolBookNode.parent = this;
            childs.add(schoolBookNode);

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
    public String getNodeId()
    {
        return node_id;
    }

    /**
     * @return 返回 node_name
     */
    public String getNodeName()
    {
        return node_name.replaceAll("'", "\\\\'");
    }

    /**
     * @return 返回 node_type 节点类型，只有书本的节点类型是固定的，其他的类型需要根据是否有子节点判断是否是52末端。 否则按照书、单元、章、节下沉
     */
    public int getNodeType()
    {
        if (parent == null)
        {
            return Constant.BOOK_NODE_TYPE_BOOK;
        }
        if (childs.isEmpty())
        {
            return Constant.BOOK_NODE_TYPE_FOOT;
        }
        return parent.getNodeType() + 1;
    }

    /**
     * @return 返回 parent_id
     */
    public String getParentId()
    {
        if (parent == null)
        {
            return null;
        }
        return parent.node_id;
    }

    public int getOrderNum()
    {
        return this.order_num;
    }

    public void setOrderNum(int orderNum)
    {
        this.order_num = orderNum;
    }

    /**
     * @param childStartwith 对childStartwith进行赋值
     */
    public void setChildStartwith(String childStartwith)
    {
        this.childStartwith = childStartwith;
    }

    /**
     * @param nodeId 对node_id进行赋值
     */
    public void setNodeId(String nodeId)
    {
        this.node_id = nodeId;
    }

    /**
     * @param nodeName 对node_name进行赋值
     */
    public void setNodeName(String nodeName)
    {
        nodeName = nodeName.replaceAll("\\+", "").trim();
        this.node_name = nodeName;
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
        builder.append(getParentId());
        builder.append(", node_type=");
        builder.append(getNodeType());
        builder.append(", node_name=");
        builder.append(node_name);
        builder.append(", childStartwith=");
        builder.append(childStartwith);
        builder.append(", child=");
        builder.append(childs);
        builder.append("]");
        return builder.toString();
    }

    public List<SchoolBookNode> getChilds()
    {
        return childs;
    }

    public void setChilds(List<SchoolBookNode> childs)
    {
        this.childs = childs;
    }

    public String getDesc()
    {
        return desc;
    }

    public void setDesc(String desc)
    {
        this.desc = desc;
    }
}
