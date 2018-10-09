package com.dzsb.util.schoolbook;

import com.dzsb.util.schoolbook.work.AddOtherChapterWT;
import com.dzsb.util.schoolbook.work.CreateSqlWT;
import com.dzsb.util.schoolbook.work.InitNodeIdWT;
import com.dzsb.util.schoolbook.work.WorkTree;
import org.apache.commons.io.FileUtils;

import java.io.File;
import java.io.IOException;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.List;

/**
 * 预处理，将excel考到文本中，替换tab为+号 导入文本，一行一个节点名，子节点增加一个+号,如下： <br/>
 * +开头为节点，数量表示层级，确保教材为第一级 <br/>
 * -开头为注释，不解析 <br/>
 * 没有字符开头的为科目，年级说明
 */
public class AppMain
{
    public static String dbName = "shangxue-db";

    /**
     * 教材前面的+号数量,可以用来统一下降
     */
    public static int plusNumBeforeBook = 1;

    public static void main(String[] args) throws IOException
    {

        // 读取导入文本
        List<String> readLines = FileUtils.readLines(new File("new.txt"), Charset.forName("UTF8"));
        List<SchoolBookNode> bookList = readFile2Tree(readLines);

        // 添加其他固定章节
        WorkTree work = null;

        // 初始化nodeid
        work = new InitNodeIdWT();
        for (SchoolBookNode r : bookList)
        {
            work.workMeAndChild(r);
        }
        work.end();

        work = new AddOtherChapterWT();
        for (SchoolBookNode r : bookList)
        {
            work.workMeAndChild(r);
        }
        work = new InitNodeIdWT();
        for (SchoolBookNode r : bookList)
        {
            work.workMeAndChild(r);
        }
        // 组成sql语句
        work = new CreateSqlWT("new.sql");
        for (SchoolBookNode r : bookList)
        {
            work.workMeAndChild(r);
        }
        work.end();

    }

    /**
     * 根据文档解析成数据结构
     *
     * @param readLines
     * @return List<SchoolBookR> [返回类型说明]
     * @throws throws [违例类型] [违例说明]
     * @see [类、类#方法、类#成员]
     */
    private static List<SchoolBookNode> readFile2Tree(List<String> readLines)
    {
        List<SchoolBookNode> bookList = new ArrayList<SchoolBookNode>();
        SchoolBookNode schoolBookNode = null;
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
                continue;
            }

            // 识别到一本书
            if (!string.startsWith("+"))
            {
                // 新书
                schoolBookNode = new SchoolBookNode();

                String nodeName = null;
                if (string.contains("€€"))
                {
                    nodeName = string.split("€€")[0];
                    schoolBookNode.setNodeName(nodeName);
                    schoolBookNode.setDesc(string.split("€€")[1]);
                }
                else
                {
                    schoolBookNode.setNodeName(string);
                    schoolBookNode.setDesc(string);
                }

                Integer nodeId = SeqUtil.getNextByType(Constant.BOOK_NODE_TYPE_BOOK);
                schoolBookNode.setNodeId(nodeId);
                schoolBookNode.setChildStartwith("+");
                bookList.add(schoolBookNode);
                continue;
            }
            // 一本书后面的行都是该书的节点，挨个挂入
            schoolBookNode.addChild(string);
        }
        return bookList;
    }

}
