package com.xp.test;

import com.xp.ElasticSearchApplication;
import com.xp.entity.Goods;
import com.xp.repository.GoodsRepository;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.elasticsearch.core.ElasticsearchRestTemplate;
import org.springframework.data.elasticsearch.core.ElasticsearchTemplate;
import org.springframework.test.context.junit4.SpringRunner;

@SpringBootTest(classes = ElasticSearchApplication.class)
@RunWith(SpringRunner.class)
public class ESTest {

    @Autowired
    private GoodsRepository goodsRepository;

    private ElasticsearchTemplate elasticsearchTemplate;
    @Test
    public void createIndex(){
        //创建索引库以及映射
        elasticsearchTemplate.createIndex(Goods.class);
        elasticsearchTemplate.putMapping(Goods.class);
    }
}
