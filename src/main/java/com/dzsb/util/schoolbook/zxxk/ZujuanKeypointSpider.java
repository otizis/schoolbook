package com.dzsb.util.schoolbook.zxxk;

import com.alibaba.fastjson.JSON;
import okhttp3.*;
import org.apache.commons.io.FileUtils;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.safety.Whitelist;
import org.jsoup.select.Elements;

import java.io.File;
import java.io.IOException;
import java.net.CookieManager;
import java.net.InetSocketAddress;
import java.net.Proxy;
import java.util.ArrayList;
import java.util.List;


public class ZujuanKeypointSpider
{
    private static final Whitelist WHITE_LIST = new Whitelist().addTags("ul", "li");

    public static void main(String[] args) throws IOException
    {
        List<KeyPointNode> allList = new ArrayList<>();
        // 高中语文
        allList.add(getTree(getHtmlFromUrl("http://zujuan.xkw.com/gzyw/zsd23177/"),10110,10001));
        // 高中数学
        allList.add(getTree(getHtmlFromUrl("http://zujuan.xkw.com/gzsx/zsd27925/"),10110,10002));
        // 高中英语
        allList.add(getTree(getHtmlFromUrl("http://zujuan.xkw.com/gzyy/zsd29978/"),10110,10003));
        // 高中物理
        allList.add(getTree(getHtmlFromUrl("http://zujuan.xkw.com/gzwl/zsd41934/"),10110,10009));
        // 高中化学
        allList.add(getTree(getHtmlFromUrl("http://zujuan.xkw.com/gzhx/zsd43452/"),10110,10008));
        // 高中生物
        allList.add(getTree(getHtmlFromUrl("http://zujuan.xkw.com/gzsw/zsd44886/"),10110,10007));
        // 高中政治
        allList.add(getTree(getHtmlFromUrl("http://zujuan.xkw.com/gzzz/zsd45411/"),10110,10005));
        // 高中历史
        allList.add(getTree(getHtmlFromUrl("http://zujuan.xkw.com/gzls/zsd46428/"),10110,10004));
        // 高中地理
        allList.add(getTree(getHtmlFromUrl("http://zujuan.xkw.com/gzdl/zsd47135/"),10110,10006));
        // 高中信息技术
        allList.add(getTree(getHtmlFromUrl("http://zujuan.xkw.com/gzxxjs/zsd49210/"),10110,10021));


        //        初中语文
        allList.add(getTree(getHtmlFromUrl("http://zujuan.xkw.com/czyw/zsd1/"),10107,10001));
        //        初中数学
        allList.add(getTree(getHtmlFromUrl("http://zujuan.xkw.com/czsx/zsd4677/"),10107,10002));
        //        初中英语
        allList.add(getTree(getHtmlFromUrl("http://zujuan.xkw.com/czyy/zsd6043/"),10107,10003));
        //        初中物理
        allList.add(getTree(getHtmlFromUrl("http://zujuan.xkw.com/czwl/zsd18194/"),10107,10009));
        //        初中化学
        allList.add(getTree(getHtmlFromUrl("http://zujuan.xkw.com/czhx/zsd19366/"),10107,10008));
        //        初中生物
        allList.add(getTree(getHtmlFromUrl("http://zujuan.xkw.com/czsw/zsd19922/"),10107,10007));
        //        初中地理
        allList.add(getTree(getHtmlFromUrl("http://zujuan.xkw.com/czdl/zsd22298/"),10107,10006));
        //        初中道德与法治
        allList.add(getTree(getHtmlFromUrl("http://zujuan.xkw.com/czzz/zsd20335/"),10107,10016));
        //        初中历史
        allList.add(getTree(getHtmlFromUrl("http://zujuan.xkw.com/czls/zsd21351/"),10107,10004));
        //        初中历史与社会
        allList.add(getTree(getHtmlFromUrl("http://zujuan.xkw.com/czlsysh/zsd129204/"),10107,10004));
        //        初中科学
        allList.add(getTree(getHtmlFromUrl("http://zujuan.xkw.com/czkx/zsd48236/"),10107,10019));
        //        初中信息技术
        allList.add(getTree(getHtmlFromUrl("http://zujuan.xkw.com/czxxjs/zsd49453/"),10107,10021));




        //        小学语文 http://zujuan.xkw.com/xxyw/zsd100987/
        //        小学数学 http://zujuan.xkw.com/xxsx/zsd100875/
        //        小学英语 http://zujuan.xkw.com/xxyy/zsd101088/
        //        小学科学 http://zujuan.xkw.com/xxkx/zsd141821/
        //        小学道德与法治 http://zujuan.xkw.com/xxzz/zsd136871/
        allList.add(getTree(getHtmlFromUrl("http://zujuan.xkw.com/xxyw/zsd100987/"), 10101, 10001));
        allList.add(getTree(getHtmlFromUrl("http://zujuan.xkw.com/xxsx/zsd100875/"), 10101, 10002));
        allList.add(getTree(getHtmlFromUrl("http://zujuan.xkw.com/xxyy/zsd101088/"), 10101, 10003));
        allList.add(getTree(getHtmlFromUrl("http://zujuan.xkw.com/xxkx/zsd141821/"), 10101, 10019));
        allList.add(getTree(getHtmlFromUrl("http://zujuan.xkw.com/xxzz/zsd136871/"), 10101, 10016));


        String s = JSON.toJSONString(allList);
        FileUtils.write(new File("C:\\Users\\jaxer\\Desktop\\zsd.json"),s);

    }

    private static KeyPointNode getTree(String treeHtml,Integer gradeId,Integer subjectId)
    {
        Document parse = Jsoup.parse(Jsoup.clean(treeHtml, WHITE_LIST));

        Element body = parse.select("body").first();
        KeyPointNode root = new KeyPointNode();
        root.setName("小学语文");
        root.setGradeId(gradeId);
        root.setSubjectId(subjectId);
        root.parseChild(root, body.childNodes().toString());

        Thread thread = Thread.currentThread();
        synchronized (thread){
            try
            {
                thread.sleep(5000);
            }
            catch (InterruptedException e)
            {
                e.printStackTrace();
            }
        }
        return root;
    }

    private static String getHtmlFromUrl(String url) throws IOException
    {
        OkHttpClient.Builder builder = new OkHttpClient.Builder();

        CookieManager cookieManager = new CookieManager();
        builder.cookieJar(new JavaNetCookieJar(cookieManager));
        builder.retryOnConnectionFailure(false);
        builder.proxy(new Proxy(Proxy.Type.HTTP, new InetSocketAddress("119.45.5.75", 8881)));
        OkHttpClient client = builder.build();


        Request.Builder reqBuilder = new Request.Builder();
        reqBuilder.url(url);
        reqBuilder.addHeader("User-Agent", "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like" +
                " Gecko) Chrome/83.0.4103.116 Safari/537.36");
        reqBuilder.addHeader("Referer", url);
        reqBuilder.addHeader("Connection", "close");
        reqBuilder.addHeader("Accept", "text/html,application/xhtml+xml,application/xml;q=0.9,image/webp,image/apng," +
                "*/*;q=0.8,application/signed-exchange;v=b3;q=0.9");

        Request request = reqBuilder.build();
        Call call = client.newCall(request);
        Response re = call.execute();
        String html = re.body().string();

        FileUtils.write(new File("C:\\Users\\jaxer\\Desktop\\"+(url.replace(":","").replace("/","_")) + ".html"),html);

        Document doc = Jsoup.parse(html);
        Elements select = doc.select(".tk-tree.tree-root");
        Element first = select.first();
        return first.html();
    }


}