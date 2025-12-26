package com.kmbeast.controller;


import com.kmbeast.pojo.api.ApiResult;
import com.kmbeast.pojo.api.Result;
import com.kmbeast.pojo.dto.ProposalFeedBackQueryDto;
import com.kmbeast.pojo.entity.ProposalFeedback;
import com.kmbeast.pojo.vo.ProposalFeedbackVO;
import com.kmbeast.service.ProposalFeedbackService;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.List;

@RestController
@RequestMapping("/proposal-feedback")
public class ProposalFeedbackController {

    @Resource
    private ProposalFeedbackService proposalFeedbackService;

    /**
     * add
     */
    public Result<String> save(@RequestBody ProposalFeedback proposalFeedback) {
        return proposalFeedbackService.saveEntity(proposalFeedback);
    }

    /**
     * update
     */
    public Result<String> update(@RequestBody ProposalFeedback proposalFeedback) {
        return proposalFeedbackService.update(proposalFeedback);
    }

    /**
     * delete
     */
    public Result<String> deleteById(@PathVariable Integer id) {
        proposalFeedbackService.removeById(id);
        return ApiResult.success("删除成功");
    }

    /**
     * query
     */
    public Result<List<ProposalFeedbackVO>> query(@RequestBody ProposalFeedBackQueryDto proposalFeedBackQueryDto) {
        return proposalFeedbackService.query(proposalFeedBackQueryDto);
    }
}
