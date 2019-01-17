package com.dzsb.util.schoolbook.zxxk;

import lombok.Data;

import java.util.List;

@Data
public class Node
{
    static String[] string = new String[]{"+", "++", "+++", "++++", "+++++"};

    String NodeID;
    String NodeName;
    String ParentNodeID;
    String DataType;
    Boolean IsRoot;
    Boolean IsHasChildNode;
    String NodeUrl;
    Boolean IsOpen;
    Boolean IsSelected;
    /**
     * 只有字段
     */
    List<Node> child;
    int level = 0;

    StringBuilder fomartAllTree()
    {
        StringBuilder sb = new StringBuilder(256);
        sb.append(string[level]);
        sb.append(NodeName.trim());
        sb.append("\n");
        if (child != null)
        {
            for (Node node : child)
            {
                sb.append(node.fomartAllTree());
            }
        }
        return sb;
    }
}
