/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package services;

import java.io.IOException;
import java.lang.reflect.Array;
import java.lang.reflect.Type;
import java.nio.charset.Charset;
import java.util.ArrayList;
import javax.json.bind.Jsonb;
import javax.json.bind.JsonbBuilder;
import org.apache.http.HttpEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;

/**
 *
 * @author hhyy
 */
public class WebClient {
    
    public static String getSite(){
        return "";
    } 
    public static Object postClient(String url ,Object ob )throws Exception{
        CloseableHttpClient client = HttpClients.createDefault();
    HttpPost httpPost = new HttpPost(url);

    String json = WebClient.ObjectToJson(ob);
    StringEntity entitye = new StringEntity(json);
    httpPost.setEntity(entitye);
    httpPost.setHeader("Accept", "application/json");
    httpPost.setHeader("Content-type", "application/json");
    
    CloseableHttpResponse response = client.execute(httpPost);
   try {
                HttpEntity entity = response.getEntity();
                if (entity != null) {
                    String result = EntityUtils.toString(entity, Charset.forName("utf-8"));
                    System.out.println(result);
                   ob = WebClient.JsonInObject(result, ob);
                }
            } finally {
                response.close();
            }
    client.close();
    return ob;
    }
    
    public static Object postClientLogin(String url ,Object ob,Object retour )throws Exception{
        CloseableHttpClient client = HttpClients.createDefault();
    HttpPost httpPost = new HttpPost(url);

    String json = WebClient.ObjectToJson(ob);
    StringEntity entitye = new StringEntity(json);
    httpPost.setEntity(entitye);
    httpPost.setHeader("Accept", "application/json");
    httpPost.setHeader("Content-type", "application/json");
    
    CloseableHttpResponse response = client.execute(httpPost);
   try {
                HttpEntity entity = response.getEntity();
                if (entity != null) {
                    String result = EntityUtils.toString(entity, Charset.forName("utf-8"));
                    System.out.println(result);
                   retour = WebClient.JsonInObject(result, retour);
                }
            } finally {
                response.close();
            }
    client.close();
    return retour;
    }
    
    public static String ObjectToJson(Object ob){
        Jsonb jsonMapper = JsonbBuilder.create();
        String json = jsonMapper.toJson(ob);
        return json;
    }
    
    public static Object[] getListWebClient(String url, Object ob) throws Exception{
        CloseableHttpClient httpClient = HttpClients.createDefault();
        Object[] list = null;
        try {
            HttpGet request = new HttpGet(url);
            CloseableHttpResponse response = httpClient.execute(request);
            try {
                HttpEntity entity = response.getEntity();
                if (entity != null) {
                    String result = EntityUtils.toString(entity, Charset.forName("utf-8"));
                    System.out.println(result);
                    list = WebClient.JsonInArray(result, ob);
                }
            } finally {
                response.close();
            }
        } finally {
            httpClient.close();
        }
        return list;
    }
    
    public static Object[] JsonInArray(String json,Object objet)throws Exception{
     Object[] ob = (Object[]) Array.newInstance(objet.getClass(), 0);
        Jsonb jsonMapper = JsonbBuilder.create();
                 ob =  jsonMapper.fromJson(json,ob.getClass());
        return ob;
    }
    public static Object JsonInObject(String json,Object ob)throws Exception{
        Jsonb jsonMapper = JsonbBuilder.create();
                 ob =  jsonMapper.fromJson(json,ob.getClass());
        return ob;
    }
}
