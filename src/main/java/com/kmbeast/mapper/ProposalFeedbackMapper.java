package com.kmbeast.mapper;


import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.kmbeast.pojo.dto.ProposalFeedbackQueryDto;
import com.kmbeast.pojo.entity.ProposalFeedback;
import com.kmbeast.pojo.vo.ProposalFeedbackVO;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface ProposalFeedbackMapper extends BaseMapper<ProposalFeedback> {
    /**
     * 意见与反馈查询
     * @param proposalFeedBackQueryDto
     * @return
     */
    List<ProposalFeedbackVO> list(ProposalFeedbackQueryDto proposalFeedBackQueryDto);

    /**
     * 意见与反馈查询总数
     * @param proposalFeedBackQueryDto
     * @return
     */
    Integer listCount(ProposalFeedbackQueryDto proposalFeedBackQueryDto);
}
