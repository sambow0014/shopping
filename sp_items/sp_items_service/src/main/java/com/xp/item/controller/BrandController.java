package com.xp.item.controller;

import com.xp.item.service.BrandService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("brand")
public class BrandController {
    @Autowired
    private BrandService brandService;


}
