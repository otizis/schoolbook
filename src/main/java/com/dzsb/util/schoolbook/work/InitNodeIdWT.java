package com.dzsb.util.schoolbook.work;

import com.dzsb.util.schoolbook.SchoolBookNode;
import com.dzsb.util.schoolbook.SeqUtil;

public class InitNodeIdWT extends WorkTree
{
    
    public void workMe(SchoolBookNode r)
    {
        if (r.getNodeId() == 0)
        {
            if (r.getNodeId() != 0)
            {
                return;
            }
            r.setNodeId(SeqUtil.getNextByType(r.getNodeType()));
        }
    }
    
    @Override
    public void end()
    {
        // 输出序列使用情况
        SeqUtil.printIndex();
    }
    
}
