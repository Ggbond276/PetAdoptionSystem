package com.kmbeast.pojo.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ProposalFeedback {
    /**
     * 用户与反馈表主键ID
     */
    private Integer id;
    /**
     * 用户ID,外键,关联用户表
     */
    private Integer user_Id;
    /**
     * 反馈或建议内容
     */
    private String detail;
    /**
     * 是否回复(0: 未回复; 1: 已回复)
     */
    private Boolean isReply;
    /**
     * 回复内容
     */
    private String replyContent;
    /**
     * 是否是精华帖(0: 不是; 1: 是)
     */
    private Boolean top;
    /**
     * 创建时间
     */
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime createTime;
    /**
     * 回复时间
     */
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime replayTime;
}
