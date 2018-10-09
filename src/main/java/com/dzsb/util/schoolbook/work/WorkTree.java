package com.dzsb.util.schoolbook.work;

import java.util.List;

import com.dzsb.util.schoolbook.SchoolBookNode;

public abstract class WorkTree
{
    public void workMeAndChild(SchoolBookNode r)
    {
        workMe(r);
        List<SchoolBookNode> childs = r.getChilds();
        for (SchoolBookNode schoolBookNode : childs)
        {
            this.workMeAndChild(schoolBookNode);
        }
    }
    
    public abstract void workMe(SchoolBookNode r);
    
    public abstract void end();
}
