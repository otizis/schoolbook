package com.dzsb.util.schoolbook.work;

import com.dzsb.util.schoolbook.Constant;
import com.dzsb.util.schoolbook.SchoolBookNode;

import java.util.List;

public class AddOtherChapterWT extends WorkTree
{
    @Override
    public void workMe(SchoolBookNode r)
    {
        if (r.getNodeType() == Constant.BOOK_NODE_TYPE_BOOK)
        {
            List<SchoolBookNode> childs = r.getChilds();
            SchoolBookNode fxk = new SchoolBookNode("复习课", r);
            childs.add(fxk);

            List<SchoolBookNode> fxkChild = fxk.getChilds();
            fxkChild.add(new SchoolBookNode("单元复习", fxk));
            fxkChild.add(new SchoolBookNode("阶段性复习", fxk));
            fxkChild.add(new SchoolBookNode("期中复习", fxk));
            fxkChild.add(new SchoolBookNode("期末复习", fxk));
            fxkChild.add(new SchoolBookNode("其他", fxk));
        }
        else if (r.getNodeType() == Constant.BOOK_NODE_TYPE_UNIT)
        {
            List<SchoolBookNode> childs = r.getChilds();
            SchoolBookNode fxk = new SchoolBookNode("单元复习课", r);
            childs.add(fxk);
        }
    }

    @Override
    public void end()
    {
        // TODO Auto-generated method stub

    }
}
