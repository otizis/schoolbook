package com.dzsb.util.schoolbook.work;

import com.dzsb.util.schoolbook.SchoolBookNode;
import com.dzsb.util.schoolbook.SeqUtil;

import java.util.UUID;

public class InitNodeIdWT extends WorkTree
{
    
    public void workMe(SchoolBookNode r)
    {
        if (r.getNodeId() == null)
        {
            if (r.getNodeId() != null)
            {
                return;
            }
            r.setNodeId(UUID.randomUUID().toString().replace("-",""));
        }
    }
    
    @Override
    public void end()
    {
        // 输出序列使用情况
        SeqUtil.printIndex();
    }
    
}
