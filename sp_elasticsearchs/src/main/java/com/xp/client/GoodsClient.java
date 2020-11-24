package com.xp.client;

import com.xp.item.api.GoodsApi;
import com.xp.item.entity.SPUDetail;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(value = "item-service")
public interface GoodsClient extends GoodsApi {
    
}
