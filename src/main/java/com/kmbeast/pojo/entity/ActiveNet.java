package com.kmbeast.pojo.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ActiveNet {
    /**
     * 行为信息表主键ID
     */
    private Integer id;
    /**
     * 用户ID,外键,关联用户表
     */
    private Integer userId;
    /**
     * 内容ID
     */
    private Integer contentId;
    /**
     * 交互行为(1:浏览; 2:点赞; 3:收藏)
     */
    private Integer type;
    /**
     * 内容模块(标识该互动类型属于什么模块)
     */
    private String contentType;
    /**
     * 创建时间
     */
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime createTime;
}
