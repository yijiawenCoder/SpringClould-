package com.chinasoft.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import org.yaml.snakeyaml.events.Event;

import java.io.Serializable;
import java.util.Date;
import java.util.Set;

/**
 * 系统管理员
 * @TableName sys_user
 */
@TableName(value ="sys_user")
@Data
public class SysUser implements Serializable {
    /**
     * 
     */
    @TableId(type = IdType.ASSIGN_UUID)
    private String userId;

    /**
     * 
     */
    private String userName;

    /**
     * 
     */
    private String userPass;

    /**
     * 
     */
    private String userEmail;

    /**
     * 
     */
    private String mobile;

    /**
     * 
     */
    private String realname;

    /**
     * 
     */
    private Integer state;

    /**
     * 
     */
    private Date optrdate;

    /**
     * 
     */
    private Integer enabled;

    /**
     * 
     */
    private String deptId;

    @TableField(exist = false)
    private static final long serialVersionUID = 1L;

    @TableField(exist = false)
    private Set<String> permissions;

}