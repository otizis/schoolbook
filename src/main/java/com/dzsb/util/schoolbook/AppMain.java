package com.dzsb.util.schoolbook;

import java.io.File;
import java.io.IOException;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.io.FileUtils;

import com.dzsb.util.schoolbook.work.AddOtherChapterWT;
import com.dzsb.util.schoolbook.work.CreateSqlWT;
import com.dzsb.util.schoolbook.work.InitNodeIdWT;
import com.dzsb.util.schoolbook.work.WorkTree;

/**
 * 预处理，将excel考到文本中，替换tab为+号 导入文本，一行一个节点名，子节点增加一个+号,如下： <br/>
 * +开头为节点，数量表示层级，确保教材为第一级 <br/>
 * -开头为注释，不解析 <br/>
 * 没有字符开头的为科目，年级说明
 */
public class AppMain
{
    public static String dbName = "shangxue-db";
    
    public static BookDescBean bookDesc = null;
    
    /**
     * 教材前面的+号数量,可以用来统一下降
     */
    public static int plusNumBeforeBook = 1;
    
    public static void main(String[] args)
        throws IOException
    {
        
        // 读取导入文本
        List<String> readLines = FileUtils.readLines(new File("bsdMath.txt"), Charset.forName("UTF8"));
        List<SchoolBookR> bookList = readFile2Tree(readLines);
        
        // 添加其他固定章节
        WorkTree work = null;
        
        // 初始化nodeid
        work = new InitNodeIdWT();
        for (SchoolBookR r : bookList)
        {
            work.workMeAndChild(r);
        }
        work.end();
        
        work = new AddOtherChapterWT();
        for (SchoolBookR r : bookList)
        {
            work.workMeAndChild(r);
        }
        work = new InitNodeIdWT();
        for (SchoolBookR r : bookList)
        {
            work.workMeAndChild(r);
        }
        // 组成sql语句
        work = new CreateSqlWT("bsdMath");
        for (SchoolBookR r : bookList)
        {
            work.workMeAndChild(r);
        }
        work.end();
        
    }
    
    /**
     * 根据文档解析成数据结构
     * 
     * @param readLines
     * @return [参数说明]
     * 
     * @return List<SchoolBookR> [返回类型说明]
     * @exception throws [违例类型] [违例说明]
     * @see [类、类#方法、类#成员]
     */
    private static List<SchoolBookR> readFile2Tree(List<String> readLines)
    {
        List<SchoolBookR> bookList = new ArrayList<SchoolBookR>();
        SchoolBookR schoolBookR = null;
        for (String string : readLines)
        {
            string = string.trim();
            
            if (string.startsWith("-"))
            {
                // 注释
                System.out.println("跳过注释" + string);
                continue;
            }
            else if (string.startsWith("+"))
            {
                // 用来统一降一级
                string = string.substring(plusNumBeforeBook);
            }
            else
            {
                System.out.println("切入说明" + string);
                bookDesc = new BookDescBean();
                bookDesc.setDesc(string);
                continue;
            }
            
            // 识别到一本书
            if (!string.startsWith("+"))
            {
                // 新书
                schoolBookR = new SchoolBookR();
                
                schoolBookR.setNode_name(string);
                Integer node_id = SeqUtil.getNextByType(Constant.BOOK_NODE_TYPE_BOOK);
                schoolBookR.setNode_id(node_id);
                schoolBookR.setBookDesc(bookDesc);
                schoolBookR.setChildStartwith("+");
                bookList.add(schoolBookR);
                continue;
            }
            // 一本书后面的行都是该书的节点，挨个挂入
            schoolBookR.addChild(string);
        }
        return bookList;
    }
    
}
