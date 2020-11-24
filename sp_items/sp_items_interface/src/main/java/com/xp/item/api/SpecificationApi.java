package com.xp.item.api;

import com.xp.item.entity.SpecGroup;
import com.xp.item.entity.SpecParam;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@RequestMapping("spec")
public interface SpecificationApi {

    /**
     *根据分类id查询规格组
     */
    @GetMapping("groups/{cid}")
    public List<SpecGroup> findGroupByCid(@PathVariable("cid") Long cid);


    /**
     * 根据条件查询规格参数
     * @param gid
     * @param cid
     * @param generic
     * @param searching
     * @return
     */
    @GetMapping("params")
    public List<SpecParam> findParamList(
            @RequestParam(value = "gid",required = false) Long gid,
            @RequestParam(value = "cid",required = false) Long cid,
            @RequestParam(value = "generic",required = false)Boolean generic,
            @RequestParam(value = "searching",required = false) Boolean searching);
}
