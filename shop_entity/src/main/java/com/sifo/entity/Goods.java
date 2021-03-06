package com.sifo.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import java.math.BigDecimal;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Accessors(chain = true)
public class Goods extends BaseEntity {
    private String subject;
    private String info;
    private BigDecimal price;
    private Integer save;

    @TableField(exist = false)
    private String fmUrl;

    @TableField(exist = false)
    private List<String> otherImgUrl;

}
