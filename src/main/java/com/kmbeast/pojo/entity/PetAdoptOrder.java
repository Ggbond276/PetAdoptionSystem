package com.kmbeast.pojo.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PetAdoptOrder {
    /**
     * 宠物领养表主键ID
     */
    private Integer id;
    /**
     * 用户ID, 外键, 关联用户表, 标识是谁发起的领养申请
     */
    private Integer userId;
    /**
     * 宠物ID, 外键, 关联宠物表, 标识领养的是哪个宠物
     */
    private Integer petId;
    /**
     * 收货地址ID, 外键, 关联地址表, 标识领养者的收货地址
     */
    private Integer addressId;
    /**
     * 领养描述
     */
    private String detail;
    /**
     * 单据状态(0:申请中; 1:已审核; 2:审核未通过; 3:已领养)
     */
    private Integer status;
    /**
     * 审核不通过原因备注
     */
    private String auditErrorDet;
    /**
     * 是否再次提交(0: 初次提交; 1: 再次提交)
     */
    private Integer isAgainPost;
    /**
     * 提交次数
     */
    private Integer postNumber;
    /**
     * 创建时间
     */
     @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime createTime;
}
