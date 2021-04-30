package com.springboot.elastic.controller;

import com.alibaba.fastjson.JSON;
import com.springboot.elastic.bean.employee;
import org.elasticsearch.action.get.GetRequest;
import org.elasticsearch.action.get.GetResponse;
import org.elasticsearch.action.index.IndexRequest;
import org.elasticsearch.action.index.IndexResponse;
import org.elasticsearch.action.search.SearchRequest;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.common.xcontent.XContentType;
import org.elasticsearch.index.query.BoolQueryBuilder;
import org.elasticsearch.index.query.MatchPhrasePrefixQueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.search.builder.SearchSourceBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.ArrayList;

/**
 * @Classname HighLevelRestController
 * @Date 2021/4/28 23:00
 * @Created by FallingStars
 * @Description
 */
@RestController
@RequestMapping("/api/high")
public class HighLevelRestController {
    String INDEX_NAME = "megacorp";
    String TYPE_NAME = "employee";

    @Autowired
    private RestHighLevelClient restHighLevelClient;

    /*
    获取ES信息
     */
    @GetMapping("es")
    public String getEsInfo() throws IOException {

        SearchSourceBuilder searchSourceBuilder = new SearchSourceBuilder();
        SearchRequest searchRequest = new SearchRequest();
        searchRequest.source(searchSourceBuilder);
        //查询ES
        SearchResponse searchResponse = restHighLevelClient.search(searchRequest, RequestOptions.DEFAULT);
        return searchResponse.toString();
    }

    @PostMapping("saveEmployee")
    public void saveEmployee(@RequestBody employee employee) throws IOException {
        IndexRequest indexRequest = new IndexRequest(INDEX_NAME);
        indexRequest.source(JSON.toJSONString(employee), XContentType.JSON);
        IndexResponse index = restHighLevelClient.index(indexRequest, RequestOptions.DEFAULT);
    }

    /*
   根据name查询
    */
    @GetMapping("getByName")
    public String getByName(String firstName, String lastName) throws IOException {
        SearchRequest searchRequest = new SearchRequest(INDEX_NAME);
        SearchSourceBuilder sourceBuilder = new SearchSourceBuilder();
        BoolQueryBuilder boolQueryBuilder = QueryBuilders.boolQuery();//must表示and的关系
        if (StringUtils.hasLength(firstName)) {
            MatchPhrasePrefixQueryBuilder mppqb = QueryBuilders.matchPhrasePrefixQuery("first_name", firstName);
            boolQueryBuilder.must(mppqb);
        }
        if (StringUtils.hasLength(lastName)) {
            MatchPhrasePrefixQueryBuilder mppqb = QueryBuilders.matchPhrasePrefixQuery("last_name", lastName);
            boolQueryBuilder.must(mppqb);
        }
        sourceBuilder.query(boolQueryBuilder);
        searchRequest.source(sourceBuilder);

        SearchResponse sr = restHighLevelClient.search(searchRequest, RequestOptions.DEFAULT);
        return sr.toString();

    }


    @GetMapping("getOrByName")
    public String getOrByName(String firstName, String lastName) throws IOException {
        SearchRequest searchRequest = new SearchRequest(INDEX_NAME);
        SearchSourceBuilder sourceBuilder = new SearchSourceBuilder();
        BoolQueryBuilder boolQueryBuilder = QueryBuilders.boolQuery();//must表示and的关系
        if (StringUtils.hasLength(firstName)) {
            MatchPhrasePrefixQueryBuilder mppqb = QueryBuilders.matchPhrasePrefixQuery("first_name", firstName);
            boolQueryBuilder.should(mppqb);
        }
        if (StringUtils.hasLength(lastName)) {
            MatchPhrasePrefixQueryBuilder mppqb = QueryBuilders.matchPhrasePrefixQuery("last_name", lastName);
            boolQueryBuilder.should(mppqb);
        }
        sourceBuilder.query(boolQueryBuilder);
        searchRequest.source(sourceBuilder);
        SearchResponse sr = restHighLevelClient.search(searchRequest, RequestOptions.DEFAULT);
        return sr.toString();

    }

    /**
     * 根据ID查询
     *
     * @param id
     * @return
     */
    @GetMapping("getById")
    public String getById(String id) {
        GetRequest getRequest = new GetRequest(INDEX_NAME, TYPE_NAME, id);
        try {
            GetResponse sr = restHighLevelClient.get(getRequest, RequestOptions.DEFAULT);
            return sr.toString();
        } catch (Exception e) {
            e.printStackTrace();
            return e.toString();
        }
    }

}
