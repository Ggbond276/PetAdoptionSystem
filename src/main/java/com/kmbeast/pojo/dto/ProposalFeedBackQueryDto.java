package com.kmbeast.pojo.dto;


import lombok.Data;
import lombok.EqualsAndHashCode;

@EqualsAndHashCode(callSuper = true)
@Data
public class ProposalFeedBackQueryDto extends QueryDto {
    /**
     * 用户ID,外键,关联的是用户表
     */
    private Integer userId;
    /**
     * 是否已经回复（0：未回复；1：已回复）
     */
    private Boolean isReply;
}
