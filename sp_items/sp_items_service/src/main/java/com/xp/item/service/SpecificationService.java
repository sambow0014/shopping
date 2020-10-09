package com.xp.item.service;

import com.xp.common.Exception.SPException;
import com.xp.common.enums.ExceptionEnum;
import com.xp.item.entity.SpecGroup;
import com.xp.item.entity.SpecParam;
import com.xp.item.mapper.SpecGroupMapper;
import com.xp.item.mapper.SpecParamMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.List;

/**
 *根据分类id查询规格组
 */
@Service
public class SpecificationService {
    @Autowired
    private SpecGroupMapper specGroupMapper;
    @Autowired
    private SpecParamMapper specParamMapper;

    public List<SpecGroup> findGroupByCid(Long cid) {
        //查询条件
        SpecGroup specGroup = new SpecGroup();
        specGroup.setCid(cid);
        List<SpecGroup> list = specGroupMapper.select(specGroup);
        if (CollectionUtils.isEmpty(list))
        {
            throw new SPException(ExceptionEnum.SPEC_GROUP_NOT_FOND);
        }
        return list;
    }

    public void addGroup(SpecGroup specGroup) {
        specGroupMapper.insert(specGroup);
    }

    public List<SpecParam> findParamList(Long gid, Long cid, Boolean searching) {
        SpecParam specParam = new SpecParam();
        specParam.setGroupId(gid);
        specParam.setCid(cid);
        specParam.setSearching(searching);
        List<SpecParam> list = specParamMapper.select(specParam);
        if (CollectionUtils.isEmpty(list)){
            throw new SPException(ExceptionEnum.SPEC_GROUP_NOT_FOND);
        }
        return list;
    }
}
