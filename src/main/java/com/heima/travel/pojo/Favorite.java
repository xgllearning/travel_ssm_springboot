package com.heima.travel.pojo;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.Date;

/**
 * 收藏实体类
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@TableName("tab_favorite")
public class Favorite implements Serializable {
    /**
     * 在表的联合主键下不能重复使用@TableId注解
     */
    @TableField
    private Integer rid;
    @TableField
    private Integer uid;
    private Date date;//收藏时间
    @TableField(exist = false)
    private Route route;//旅游线路对象
    @TableField(exist = false)
    private User user;//所属用户
}
