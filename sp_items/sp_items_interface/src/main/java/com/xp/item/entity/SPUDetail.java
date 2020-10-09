package com.xp.item.entity;

import lombok.Data;

import javax.persistence.Id;
import javax.persistence.Table;

@Table(name = "tb_spu_detail")
@Data
public class SPUDetail {
    @Id
    private Long spuId;
    private String description;
    private String specifications;
    private String specTemplate;
    private String packingList;
    private String afterService;
}
