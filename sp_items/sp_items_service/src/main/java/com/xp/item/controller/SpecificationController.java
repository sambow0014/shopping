package com.xp.item.controller;

import com.xp.item.entity.SpecGroup;
import com.xp.item.entity.SpecParam;
import com.xp.item.service.SpecificationService;
import org.apache.ibatis.annotations.Param;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("spec")
public class SpecificationController {

    @Autowired
    private SpecificationService specificationService;

    /**
     *根据分类id查询规格组
     */
    @GetMapping("groups/{cid}")
    public ResponseEntity<List<SpecGroup>> findGroupByCid(@PathVariable("cid") Long cid){
        return ResponseEntity.ok(specificationService.findGroupByCid(cid));
    }

    @PostMapping("group")
    public String addGroup(SpecGroup specGroup){
        specificationService.addGroup(specGroup);
        return "redirect:groups";
    }

    @GetMapping("params")
    public ResponseEntity<List<SpecParam>> findParamByGroupId(@RequestParam("gid") Long gid){
        return ResponseEntity.ok(specificationService.findParamByGroupId(gid));
    }
}
