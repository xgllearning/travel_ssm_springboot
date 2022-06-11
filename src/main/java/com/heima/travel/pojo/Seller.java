package com.heima.travel.pojo;

import com.baomidou.mybatisplus.annotation.TableId;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * 商家实体类
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Seller implements Serializable {
    @TableId
    private int sid;//商家id
    private String sname;//商家名称
    private String consphone;//商家电话
    private String address;//商家地址


}
