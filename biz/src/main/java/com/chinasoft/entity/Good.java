package com.chinasoft.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * 商品信息表
 * @TableName good
 */
@TableName(value ="good")
@Data
public class Good implements Serializable {
    /**
     * 
     */
    @TableId(type = IdType.ASSIGN_UUID)
    private String gid;

    /**
     * 
     */
    private String gname;

    /**
     * 
     */
    private Integer state;

    /**
     * 
     */
    private Date optrdate;

    @TableField(exist = false)
    private static final long serialVersionUID = 1L;
}