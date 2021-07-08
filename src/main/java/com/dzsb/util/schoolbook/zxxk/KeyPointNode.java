package com.dzsb.util.schoolbook.zxxk;

import lombok.Data;
import org.apache.commons.lang3.StringUtils;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.nodes.Node;

import java.util.ArrayList;
import java.util.List;

@Data
public class KeyPointNode
{
    private String name;
    private Integer subjectId;
    private Integer gradeId;
    private List<KeyPointNode> childList ;

    public void setName(String name)
    {
        this.name = StringUtils.trim(name);
    }

    public void addChild(KeyPointNode node){
        if(childList == null){
            childList = new ArrayList<>();
        }
        childList.add(node);
    }

    public void parseChild(KeyPointNode parent,String subHtml)
    {
        Document parse = Jsoup.parse(subHtml);
        Element ul = parse.selectFirst("ul");
        List<Node> nodes = ul.childNodes();
        for (Node node1 : nodes)
        {
            String s1 = node1.toString();
            if (StringUtils.isBlank(s1))
            {
                continue;
            }
            String s = node1.nodeName();
            System.out.println(s);
            System.out.println(node1.toString());
            Node firstSub = node1.childNode(0);
            String s2 = firstSub.toString();
            KeyPointNode keyPointNode = new KeyPointNode();
            keyPointNode.setName(s2);
            if(node1.childNodeSize() > 1){
                keyPointNode.parseChild(keyPointNode, node1.childNode(1).toString());
            }
            parent.addChild(keyPointNode);

        }
    }
}
