package com.dzsb.util.schoolbook.work;

import java.util.List;

import com.dzsb.util.schoolbook.SchoolBookR;

public abstract class WorkTree
{
    public void workMeAndChild(SchoolBookR r)
    {
        workMe(r);
        List<SchoolBookR> childs = r.getChilds();
        for (SchoolBookR schoolBookR : childs)
        {
            this.workMeAndChild(schoolBookR);
        }
    }
    
    public abstract void workMe(SchoolBookR r);
    
    public abstract void end();
}
