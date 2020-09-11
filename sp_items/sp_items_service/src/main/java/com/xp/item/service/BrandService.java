package com.xp.item.service;

import com.xp.item.entity.Brand;
import com.xp.item.mapper.BrandMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BrandService {
    @Autowired
    private BrandMapper brandMapper;


}
