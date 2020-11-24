package com.xp.client;

import com.xp.item.api.CategoryApi;
import org.springframework.cloud.openfeign.FeignClient;

@FeignClient(value = "item-service")
public interface CategoryClient extends CategoryApi {
}
