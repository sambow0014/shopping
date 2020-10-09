package com.xp.item.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;
import tk.mybatis.mapper.annotation.KeySql;

import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;
import java.util.Date;
import java.util.List;

@Data
@Table(name = "tb_spu")
public class SPU {
    @Id
    @KeySql(useGeneratedKeys = true)
    private Long id;
    private String title;
    private String subTitle;
    private Long cid1;
    private Long cid2;
    private Long cid3;
    private Long brandId;
    private Boolean saleable;
    @JsonIgnore          //不返回数据
    private Boolean valid;
    private Date createTime;
    @JsonIgnore
    private Date lastUpdateTime;

    @Transient          //不访问数据库
    private String Cname;
    @Transient
    private String Bname;
    @Transient
    private List<SKU> skus;
    @Transient
    private SPUDetail spuDetail;
}
