package com.xp.repository;

import com.xp.entity.Goods;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

public interface GoodsRepository extends ElasticsearchRepository<Goods,Long> {
}
