package com.kmbeast.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.kmbeast.context.LocalThreadHolder;
import com.kmbeast.mapper.PetPostMapper;
import com.kmbeast.pojo.api.ApiResult;
import com.kmbeast.pojo.api.Result;
import com.kmbeast.pojo.dto.PetPostQueryDto;
import com.kmbeast.pojo.em.IsAuditEnum;
import com.kmbeast.pojo.entity.PetPost;
import com.kmbeast.pojo.vo.PetPostListItemVO;
import com.kmbeast.pojo.vo.PetPostVO;
import com.kmbeast.service.PetPostService;
import com.kmbeast.utils.AssertUtils;
import org.springframework.cglib.core.Local;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class PetPostServiceImpl extends ServiceImpl<PetPostMapper, PetPost> implements PetPostService {

    /**
     * 新增宠物经验帖子
     * @param petPost
     * @return
     */
    @Override
    public Result<String> saveEntity(PetPost petPost) {
        // 校验参数
        judgeParam(petPost);
        petPost.setUserId(LocalThreadHolder.getUserId());
        petPost.setCreateTime(LocalDateTime.now());
        petPost.setIsAudit(IsAuditEnum.NO_AUDIT.getStatus());
        save(petPost);
        return ApiResult.success("宠物经验帖子新增成功");
    }

    /**
     * 更新宠物经验帖子
     * @param petPost
     * @return
     */
    @Override
    public Result<String> updateEntity(PetPost petPost) {
        // 参数校验
        judgeParam(petPost);
        // 更新宠物经验帖子
        updateById(petPost);
        return ApiResult.success("宠物经验帖子更新成功");
    }

    /**
     * 查询宠物经验帖子信息
     * @param petPostQueryDto
     * @return
     */
    @Override
    public Result<List<PetPostListItemVO>> list(PetPostQueryDto petPostQueryDto) {
        List<PetPostListItemVO> PetPostListItemVOList = this.baseMapper.list(petPostQueryDto);
        Integer count = this.baseMapper.listCount(petPostQueryDto);
        return ApiResult.success(PetPostListItemVOList, count);
    }

    /**
     * 根据ID查询宠物经帖子信息
     * @param id
     * @return
     */
    @Override
    public Result<PetPostVO> getById(Integer id) {
        PetPostVO petPostVO = this.baseMapper.getById(id);
        return ApiResult.success(petPostVO);
    }

    /**
     * 宠物经验帖子审核
     *
     * @param id 主键ID
     * @return Result<String> 通用返回封装类
     */
    @Override
    public Result<String> audit(Integer id) {
        PetPost petPost = new PetPost();
        petPost.setId(id);
        petPost.setIsAudit(IsAuditEnum.AUDIT.getStatus());
        updateById(petPost);
        return ApiResult.success("审核成功");
    }

    /**
     * 宠物经验帖子参数校验
     * @param petPost
     */
    private void judgeParam(PetPost petPost) {
        AssertUtils.notNull(petPost.getPetTypeId(), "宠物类型不为空哦");
        AssertUtils.hasText(petPost.getTitle(), "标题不为空哦");
        AssertUtils.hasText(petPost.getCover(), "封面要上传哦");
        AssertUtils.hasText(petPost.getSummary(), "请补充摘要");
    }
}
