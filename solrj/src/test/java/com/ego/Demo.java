package com.ego;

import org.apache.solr.client.solrj.SolrClient;
import org.apache.solr.client.solrj.SolrQuery;
import org.apache.solr.client.solrj.SolrServerException;
import org.apache.solr.client.solrj.impl.CloudSolrClient;
import org.apache.solr.client.solrj.impl.HttpSolrClient;
import org.apache.solr.client.solrj.response.QueryResponse;
import org.apache.solr.common.SolrDocument;
import org.apache.solr.common.SolrDocumentList;
import org.apache.solr.common.SolrInputDocument;
import org.junit.Test;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;

public class Demo {
    @Test
    public void testInsert() throws IOException, SolrServerException {
/*
        HttpSolrClient.Builder builder = new HttpSolrClient.Builder();
        builder.withBaseSolrUrl("http://10.100.13.173:30005/solr/collection1");
        SolrClient client = builder.build();
*/
        List<String> zkServers = new ArrayList<>();
        zkServers.add("10.100.13.173:32181"); zkServers.add("10.100.13.173:32182"); zkServers.add("10.100.13.173:32183");
        CloudSolrClient client = new CloudSolrClient.Builder(zkServers, Optional.empty()).build();
        client.setDefaultCollection("collection2");


        SolrInputDocument doc = new SolrInputDocument();
        doc.addField("id", "004");
        doc.addField("cn_text", "java资深工程师");

        client.add(doc);
        client.commit();
    }

    @Test
    public void testDelete() throws IOException, SolrServerException {
        HttpSolrClient.Builder builder = new HttpSolrClient.Builder();
        builder.withBaseSolrUrl("http://10.100.13.173:30005/solr/collection1");

        SolrClient client = builder.build();
        client.deleteById("001");

        client.commit();
    }

    @Test
    public void testQuery() throws IOException, SolrServerException {
/*
        HttpSolrClient.Builder builder = new HttpSolrClient.Builder();
        builder.withBaseSolrUrl("http://10.100.13.173:30005/solr/collection1");
        SolrClient client = builder.build();
*/
        List<String> solrUrls = new ArrayList<>();
        solrUrls.add("http://10.100.13.173:30005/solr");
        solrUrls.add("http://10.100.13.173:30006/solr");
        solrUrls.add("http://10.100.13.173:30007/solr");
        solrUrls.add("http://10.100.13.173:30008/solr");
        CloudSolrClient client = new CloudSolrClient.Builder(solrUrls).build();
        client.setDefaultCollection("collection2");

        //可视化界面左侧条件
        SolrQuery params = new SolrQuery();
        //设置q
        params.setQuery("cn_text:师");
        //设置分页
        params.setStart(0);
        params.setRows(2);
        //启用高亮
        params.setHighlight(true);
        params.addHighlightField("cn_text");
        params.setHighlightSimplePre("<span style='color:red;'>");
        params.setHighlightSimplePost("</span>");

        //点击查询按钮，向solr web服务器发送请求，接收响应。query对象中包含了json数据
        QueryResponse response = client.query(params);

        Map<String, Map<String, List<String>>> hh = response.getHighlighting();

        //取出docs
        SolrDocumentList solrList = response.getResults();

        for (SolrDocument doc : solrList) {
            System.out.println(doc.getFieldValue("id"));
            System.out.println("未高亮：" + doc.getFieldValue("cn_text"));
            Map<String, List<String>> map = hh.get(doc.getFieldValue("id"));
            List<String> list = map.get("cn_text");
            //可能为空
            if(list != null && !list.isEmpty())
                System.out.println("高亮：" + list.get(0));
            else
                System.out.println("没有高亮内容");
        }
    }
}
