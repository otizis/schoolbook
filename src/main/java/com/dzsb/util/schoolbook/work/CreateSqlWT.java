package com.dzsb.util.schoolbook.work;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import org.apache.commons.io.FileUtils;

import com.dzsb.util.schoolbook.AppMain;
import com.dzsb.util.schoolbook.Constant;
import com.dzsb.util.schoolbook.Dict;
import com.dzsb.util.schoolbook.SchoolBookNode;
import com.dzsb.util.schoolbook.SeqUtil;

public class CreateSqlWT extends WorkTree
{
    private String fileName;

    public CreateSqlWT(String fileName)
    {
        this.fileName = fileName;

        String sql = "INSERT INTO `t_permission` "
                + "(`uuid`,`name`, `perm_type`, `group_id`, `parent_uuid`, `root_uuid`, `is_default`,`create_at`, `creator_id`) " +
                "VALUES ";
        t_schoolbook_content_r_rs.add(sql);
    }
    
    private List<String> t_schoolbook_content_r_rs = new ArrayList<String>();
    
    private List<String> t_schoolbook_rs = new ArrayList<String>();
    
    public void addContentRs(String sql)
    {
        t_schoolbook_content_r_rs.add(sql);
    }
    
    public void addBookRs(String sql)
    {
        t_schoolbook_rs.add(sql);
    }

    @Override
    public void workMe(SchoolBookNode r)
    {
        String sql = "('<{uuid: }>','<{name: }>',3,1,'<{parent_uuid: }>','<{root_uuid: }>',1,'2021-05-25 14:49:37',1),";
        sql = sql.replace("<{uuid: }>", r.getNodeId()+"");
        sql = sql.replace("<{name: }>", r.getNodeName());
        sql = sql.replace("<{parent_uuid: }>", r.getParentId() + "");
        sql = sql.replace("<{root_uuid: }>",  "");
        addContentRs(sql);
        
        if (r.getNodeType() == Constant.BOOK_NODE_TYPE_BOOK)
        {
            addBookRs(this.initBookSql(r));
        }
    }
    
    /**
     * 作为书本，初始化schoolbook表 <功能详细描述> [参数说明]
     * 
     * @return void [返回类型说明]
     * @exception throws [违例类型] [违例说明]
     * @see [类、类#方法、类#成员]
     */
    public String initBookSql(SchoolBookNode r)
    {
        String sql = "INSERT INTO `" + AppMain.dbName
            + "`.`t_schoolbook`(`schoolbook_id`,`schoolbook_name`,`item_publishing_id`,`item_subject_id`,`item_grade_id`,`item_volume_id`)VALUES"
            + "(<{schoolbook_id: }>,'<{schoolbook_name: }>',<{item_publishing_id: }>,<{item_subject_id: }>,<{item_grade_id: }>,<{item_volume_id: }>);";
        sql = sql.replace("<{schoolbook_id: }>", r.getNodeId() + "");
        sql = sql.replace("<{schoolbook_name: }>", r.getNodeName());
        sql = sql.replace("<{item_publishing_id: }>", Dict.getPublishId(r.getDesc()));
        sql = sql.replace("<{item_subject_id: }>", Dict.getSubjectId(r.getDesc()));
        sql = sql.replace("<{item_grade_id: }>", Dict.getGradeId(r.getDesc()));
        sql = sql.replace("<{item_volume_id: }>", Dict.getFacId(r.getDesc()));
        return sql;
        
    }
    
    @Override
    public void end()
    {
        // 将最后一行的逗号改为分号结尾
        String string = t_schoolbook_content_r_rs.get(t_schoolbook_content_r_rs.size() - 1);
        StringBuilder end = new StringBuilder(string);
        end.setCharAt(end.length() - 1, ';');
        t_schoolbook_content_r_rs.remove(t_schoolbook_content_r_rs.size() - 1);
        t_schoolbook_content_r_rs.add(end.toString());
        // 输出到文件
        try
        {
            FileUtils.writeLines(new File(fileName), t_schoolbook_content_r_rs);
            FileUtils.writeLines(new File(fileName), t_schoolbook_rs, true);
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }
    }
}
