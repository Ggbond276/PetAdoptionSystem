package com.kmbeast.pojo.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PetPost {
    /**
     * 宠物经验帖子主键ID
     */
    private Integer id;
    /**
     * 用户ID,外键,关联用户表,标识是谁发布的经验帖子
     */
    private Integer userId;
    /**
     * 宠物类型ID,外键,关联宠物类型表,标识发布的经验帖子是哪种宠物类型
     */
    private Integer petTypeId;
    /**
     * 帖子标题
     */
    private String title;
    /**
     * 帖子封面
     */
    private String cover;
    /**
     * 帖子内容
     */
    private String content;
    /**
     * 是否已经审核(0: 未审核; 1: 已审核)
     */
    private Integer isAudit;
    /**
     * 创建时间
     */
     @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime createTime;
}
