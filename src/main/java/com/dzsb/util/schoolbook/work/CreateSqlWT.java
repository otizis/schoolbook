package com.dzsb.util.schoolbook.work;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.io.FileUtils;

import com.dzsb.util.schoolbook.AppMain;
import com.dzsb.util.schoolbook.Constant;
import com.dzsb.util.schoolbook.Dict;
import com.dzsb.util.schoolbook.SchoolBookR;
import com.dzsb.util.schoolbook.SeqUtil;

public class CreateSqlWT extends WorkTree
{
    private String fileName;

    public CreateSqlWT(String fileName)
    {
        this.fileName = fileName+".sql";

        String sql = "INSERT INTO `" + AppMain.dbName
            + "`.`t_schoolbook_content_r`(`node_id`,`parent_id`,`node_type`,`node_name`,`order_num`)VALUES";
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
    
    public void workMe(SchoolBookR r)
    {
        String sql = "(<{node_id: }>,<{parent_id: }>,<{node_type: }>,'<{node_name: }>',<order_num>),";
        sql = sql.replace("<{node_id: }>", r.getNode_id() + "");
        sql = sql.replace("<{parent_id: }>", r.getParent_id() + "");
        sql = sql.replace("<{node_type: }>", r.getNode_type() + "");
        sql = sql.replace("<{node_name: }>", r.getNode_name());
        sql = sql.replace("<order_num>", SeqUtil.getNextByType(Constant.BOOK_NODE_TYPE_ORDER_NUM)+"");
        addContentRs(sql);
        
        if (r.getNode_type() == Constant.BOOK_NODE_TYPE_BOOK)
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
    public String initBookSql(SchoolBookR r)
    {
        String sql = "INSERT INTO `" + AppMain.dbName
            + "`.`t_schoolbook`(`schoolbook_id`,`schoolbook_name`,`item_publishing_id`,`item_subject_id`,`item_grade_id`,`item_volume_id`)VALUES"
            + "(<{schoolbook_id: }>,'<{schoolbook_name: }>',<{item_publishing_id: }>,<{item_subject_id: }>,<{item_grade_id: }>,<{item_volume_id: }>);";
        sql = sql.replace("<{schoolbook_id: }>", r.getNode_id() + "");
        sql = sql.replace("<{schoolbook_name: }>", r.getNode_name());
        sql = sql.replace("<{item_publishing_id: }>", Dict.getPublishId(r.getNode_name()));
        sql = sql.replace("<{item_subject_id: }>", Dict.getSubjectId(r.getNode_name()));
        sql = sql.replace("<{item_grade_id: }>", Dict.getGradeId(r.getBookDesc()));
        sql = sql.replace("<{item_volume_id: }>", Dict.getFacId(r));
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
