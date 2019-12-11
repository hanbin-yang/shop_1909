package com.sifo.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Accessors(chain = true)
public class GoodsImages extends BaseEntity {

    private Integer gid;
    private String info;
    private String url;
    private Integer isfengmian;
}
