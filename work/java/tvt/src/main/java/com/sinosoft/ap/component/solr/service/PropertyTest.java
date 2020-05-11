//package com.sinosoft.ap.component.solr.service;//package com.sinosoft.ap.component.solr.service;
////
////import java.io.BufferedInputStream;
////import java.io.FileInputStream;
////import java.io.FileOutputStream;
////import java.io.InputStream;
////import java.net.URL;
////import java.util.HashMap;
////import java.util.Iterator;
////import java.util.Map;
////import java.util.Properties;
////
/////**
//// * Created by HongyanShan
//// * 2017/10/22 18:27
//// */
////public class PropertyTest {
////    public static void main(String[] args) {
////        Properties prop = new Properties();
////        try {
////            //读取属性文件application.properties
////            InputStream in = Thread.currentThread().getContextClassLoader().getResourceAsStream("application.properties");
////            prop.load(in);//加载属性列表
////            Map<String, String> solrMap = new HashMap<>();
////            Iterator<String> it = prop.stringPropertyNames().iterator();
////            while (it.hasNext()) {
////                String key = it.next();
////                if (key.startsWith("solr.")) {
////                    solrMap.put(key.replace(".", "").toUpperCase(), prop.getProperty(key));
////                }
////            }
//////            in.close();
//////            ///保存属性到b.properties文件
//////            FileOutputStream oFile = new FileOutputStream("b.properties", true);//true表示追加打开
//////            prop.setProperty("phone", "10086");
//////            prop.store(oFile, "The New properties file");
//////            oFile.close();
////        } catch (Exception e) {
////            System.out.println(e);
////        }
////    }
////}
