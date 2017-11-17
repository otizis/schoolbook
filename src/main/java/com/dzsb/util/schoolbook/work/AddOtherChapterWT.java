package com.dzsb.util.schoolbook.work;

import java.util.List;

import com.dzsb.util.schoolbook.Constant;
import com.dzsb.util.schoolbook.SchoolBookR;

public class AddOtherChapterWT extends WorkTree
{
    @Override
    public void workMe(SchoolBookR r)
    {
        if (r.getNode_type() == Constant.BOOK_NODE_TYPE_BOOK)
        {
            List<SchoolBookR> childs = r.getChilds();
            SchoolBookR fxk = new SchoolBookR("复习课", r);
            childs.add(fxk);
            
            List<SchoolBookR> fxkChild = fxk.getChilds();
            fxkChild.add(new SchoolBookR("单元复习", fxk));
            fxkChild.add(new SchoolBookR("阶段性复习", fxk));
            fxkChild.add(new SchoolBookR("期中复习", fxk));
            fxkChild.add(new SchoolBookR("期末复习", fxk));
            fxkChild.add(new SchoolBookR("其他", fxk));
        }
    }
    
    @Override
    public void end()
    {
        // TODO Auto-generated method stub
        
    }
}
