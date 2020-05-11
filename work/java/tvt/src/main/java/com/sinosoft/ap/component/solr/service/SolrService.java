//package com.sinosoft.ap.component.solr.service;
//
//import com.sinosoft.ap.component.page.PageParam;
//import org.apache.solr.client.solrj.SolrClient;
//
//import java.util.Map;
//
///**
// * 用于solr
// * <p>
// * Created by HongyanShan
// * 2017/10/22 19:03
// */
//
//public interface SolrService {
//    /**
//     * 通过去去配置文件获取solr相关配置
//     * 并返回实例化的SolrClient
//     * 可配置多个solr源
//     * key为配置文件中key去“.”后大写
//     *
//     * @return Map
//     * @throws Exception
//     * @author HongyanShan
//     * @date 2017/10/23 17:15
//     */
//    Map<String, SolrClient> returnSolrClient() throws Exception;
//
//    /**
//     * 根据key返回对应solrClient
//     *
//     * @param solrKey
//     * @return SolrClient
//     * @throws Exception
//     * @author HongyanShan
//     * @date 2017/10/24 12:02
//     */
//    SolrClient returnSolrClient(String solrKey) throws Exception;
//
//    /**
//     * 返回solr分页信息
//     *
//     * @param solrClient   solr客户端
//     * @param queryString  solr查询语句
//     * @param pageParam    solr信息分页
//     * @return PageParam
//     * @throws Exception
//     * @author HongyanShan
//     * @date 2017/10/23 19:30
//     */
//    PageParam returnSolrPage(SolrClient solrClient, String queryString, PageParam pageParam) throws Exception;
//
//}
