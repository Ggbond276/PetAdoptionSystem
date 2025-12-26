package com.kmbeast.service;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.kmbeast.context.LocalThreadHolder;
import com.kmbeast.mapper.ProposalFeedbackMapper;
import com.kmbeast.pojo.api.ApiResult;
import com.kmbeast.pojo.api.Result;
import com.kmbeast.pojo.dto.ProposalFeedBackQueryDto;
import com.kmbeast.pojo.em.IsReplyEnum;
import com.kmbeast.pojo.em.IsTopEnum;
import com.kmbeast.pojo.entity.ProposalFeedback;
import com.kmbeast.pojo.vo.ProposalFeedbackVO;
import com.kmbeast.utils.AssertUtils;

import javax.xml.ws.Service;
import java.time.LocalDateTime;
import java.util.List;

public class ProposalFeedbackServiceImpl extends ServiceImpl<ProposalFeedbackMapper, ProposalFeedback> implements ProposalFeedbackService {
    /**
     * 意见与反馈新增
     */
    @Override
    public Result<String> saveEntity(ProposalFeedback proposalFeedback) {
        AssertUtils.hasText(proposalFeedback.getDetail(), "描述不能为空哦");
        AssertUtils.isTrue(proposalFeedback.getDetail().length() < 255, "描述在255个字以内哦");
        proposalFeedback.setUserId(LocalThreadHolder.getUserId()); // 设置反馈者用户ID
        proposalFeedback.setIsReply(IsReplyEnum.NO_REPLY.getStatus()); // 新增的反馈，初始是没有回复的
        proposalFeedback.setIsTop(IsTopEnum.NO_TOP.getStatus()); // 初始是未精华帖
        proposalFeedback.setCreateTime(LocalDateTime.now()); // 设置新增时间 - 当前时间
        save(proposalFeedback);
        return ApiResult.success("反馈成功");
    }

    /**
     * 意见与反馈回复
     * @param proposalFeedback
     * @return
     */
    @Override
    public Result<String> update(ProposalFeedback proposalFeedback) {
        AssertUtils.hasText(proposalFeedback.getReplyContent(), "回复内容不能为空哦");
        proposalFeedback.setReplyTime(LocalDateTime.now()); // 设置回复时间
        proposalFeedback.setIsReply(IsReplyEnum.REPLY.getStatus()); // 设置回复状态 - 已回复
        updateById(proposalFeedback);
        return ApiResult.success("回复成功");
    }

    /**
     * 意见与反馈查询
     * @param proposalFeedBackQueryDto
     * @return
     */
    @Override
    public Result<List<ProposalFeedbackVO>> query(ProposalFeedBackQueryDto proposalFeedBackQueryDto) {
          List<ProposalFeedbackVO> proposalFeedbackVOS = this.baseMapper.list(proposalFeedBackQueryDto);
          Integer count = this.baseMapper.listCount(proposalFeedBackQueryDto);
          return ApiResult.success(proposalFeedbackVOS, count);
    }
}
