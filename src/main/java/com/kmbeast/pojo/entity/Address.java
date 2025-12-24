package com.kmbeast.pojo.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Address {
    /**
     * 收货地址主键ID
     */
    private Integer id;
    /**
     * 用户ID, 外键, 关联用户表, 标识这条收货地址属于哪个用户
     */
    private Integer userId;
    /**
     * 详细地址
     */
    private String detail;
    /**
     * 收件人
     */
    private String addressee;
    /**
     * 收件电话
     */
    private String concat_phone;
    /**
     * 是否默认收货地址(0:非默认地址; 1:默认地址)
     */
    private Boolean is_default;
    /**
     * 创建时间
     */
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime createTime;
}
