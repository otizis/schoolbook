package com.dzsb.util.schoolbook.work;

import com.dzsb.util.schoolbook.Constant;
import com.dzsb.util.schoolbook.SchoolBookR;
import com.dzsb.util.schoolbook.SeqUtil;

public class InitNodeIdWT extends WorkTree
{
    
    public void workMe(SchoolBookR r)
    {
        if (r.getNode_id() == 0)
        {
            if (r.getNode_id() != 0)
            {
                return;
            }
            r.setNode_id(SeqUtil.getNextByType(r.getNode_type()));
        }
    }
    
    @Override
    public void end()
    {
        // 输出序列使用情况
        SeqUtil.printIndex();
    }
    
}
