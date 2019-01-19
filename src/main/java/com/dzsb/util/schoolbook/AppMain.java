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
 * 处理树形文本，结构如下： <br/>
 * +开头为节点，+号的数量表示层级，一个+号表示书本名称，++表示书本下的子节点 <br/>
 * 不以+开头为注释，不解析跳过 <br/>
 * 每一行可以使用€€最为分隔说明，若书本名称不能包括（出版社，年级，上下册，科目),需要在说明内注明，如 <br/>
 * <code>
 * +数学三年级(上)2018€€人教版，数学，三年级，上册 <br/>
 * ++第一单元：时、分、秒 <br/>
 * +++第1课时：秒的认识 <br/>
 * +++第2课时：时间的计算 <br/>
 * ++第二单元：万以内的加法和减法（一） <br/>
 * </code>
 */
public class AppMain
{
    public static String dbName = "shangxue-db";


    public static void main(String[] args) throws IOException
    {
        String filename = "tiyu.txt";
        //SELECT max(node_id),node_type FROM t_schoolbook_content_r group by node_type;
        SeqUtil.type1_min=224;
        SeqUtil.type2_min=11020;
        SeqUtil.type3_min=100329;
        SeqUtil.type52_min=1007706;
        //SELECT max(order_num) FROM t_schoolbook_content_r
        SeqUtil.type_order_num_min=9261;
        // 读取导入文本
        List<String> readLines = FileUtils.readLines(new File(filename), Charset.forName("UTF8"));
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
        work = new CreateSqlWT(filename+".sql");
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

            if (string.startsWith("+"))
            {
                string = string.substring(1);
            }
            else
            {
                // 注释
                System.out.println("跳过注释" + string);
                continue;
            }

            // 一个+号表示一本书，截取一个+后，没有+号表示识别到一本新书
            if (!string.startsWith("+"))
            {
                schoolBookNode = new SchoolBookNode();
                if (string.contains("€€"))
                {
                    String[] split = string.split("€€");
                    schoolBookNode.setNodeName(split[0]);
                    schoolBookNode.setDesc(split[1]);
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
