//package com.sinosoft.ap.component.solr.service;
//
//import com.sinosoft.ap.component.page.PageParam;
//import org.apache.solr.client.solrj.SolrClient;
//import org.apache.solr.client.solrj.SolrQuery;
//import org.apache.solr.client.solrj.impl.LBHttpSolrClient;
//import org.apache.solr.client.solrj.response.QueryResponse;
//import org.apache.solr.common.SolrDocumentList;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.core.env.Environment;
//import org.springframework.stereotype.Service;
//
//import java.io.InputStream;
//import java.util.HashMap;
//import java.util.Iterator;
//import java.util.Map;
//import java.util.Properties;
//
//@Service
//public class SolrServiceImpl implements SolrService {
//    @Autowired
//    private Environment environment;
//
//    @Override
//    public Map<String, SolrClient> returnSolrClient() throws Exception {
//        Properties prop = new Properties();
//        InputStream in = Thread.currentThread().getContextClassLoader().getResourceAsStream("application.properties");
//        prop.load(in);//加载属性列表
//        Map<String, SolrClient> solrMap = new HashMap<>();
//        Iterator<String> it = prop.stringPropertyNames().iterator();
//        while (it.hasNext()) {
//            String key = it.next();
//            if (key.startsWith("solr.")) {
//                String solrHost = environment.getRequiredProperty(key);
//                SolrClient solrClient = new LBHttpSolrClient(solrHost);
//                solrMap.put(key.replace(" ", "").replace(".", "").toUpperCase(), solrClient);
//            }
//        }
//        return solrMap;
//    }
//
//    @Override
//    public SolrClient returnSolrClient(String solrKey) throws Exception {
//        Map<String, SolrClient> solrClientMap = this.returnSolrClient();
//        return solrClientMap.get(solrKey);
//    }
//
//    @Override
//    public PageParam returnSolrPage(SolrClient solrClient, String queryString, PageParam pageParam) throws Exception {
//        SolrQuery solrParams = new SolrQuery();
//        solrParams.setQuery(queryString);
//        QueryResponse queryResponse = solrClient.query(solrParams);
//        SolrDocumentList solrDocumentList = queryResponse.getResults();
//        if (pageParam.getPageIndex() == 0) {
//            pageParam.setPageIndex(1);
//        }
//        pageParam.setTotleInfo(solrDocumentList.getNumFound());
//        if (pageParam.getTotleInfo() % pageParam.getPageSize() != 0) {
//            pageParam.setTotlePage(pageParam.getTotleInfo().intValue() / pageParam.getPageSize().intValue() + 1);
//        } else {
//            pageParam.setTotlePage(pageParam.getTotleInfo().intValue() / pageParam.getPageSize().intValue());
//        }
//        return pageParam;
//    }
//}
