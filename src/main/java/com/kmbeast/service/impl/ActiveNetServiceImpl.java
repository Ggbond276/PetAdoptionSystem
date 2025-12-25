package com.kmbeast.service.impl;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.kmbeast.context.LocalThreadHolder;
import com.kmbeast.mapper.ActiveNetMapper;
import com.kmbeast.pojo.api.ApiResult;
import com.kmbeast.pojo.api.Result;
import com.kmbeast.pojo.dto.ActiveNetQueryDto;
import com.kmbeast.pojo.entity.ActiveNet;
import com.kmbeast.service.ActiveNetService;
import com.kmbeast.utils.AssertUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class ActiveNetServiceImpl extends ServiceImpl<ActiveNetMapper, ActiveNet> implements ActiveNetService {

    private Logger log = LoggerFactory.getLogger(PetServiceImpl.class);

    /**
     * 查询互动行为信息列表
     * @param activeNetQueryDto
     * @return
     */
    @Override
    public Result<List<ActiveNet>> query(ActiveNetQueryDto activeNetQueryDto) {
        log.info("开始查询互动行为信息: 参数{}", activeNetQueryDto);
        List<ActiveNet> activeNetList = this.getBaseMapper().query(activeNetQueryDto);
        Integer count = this.getBaseMapper().count(activeNetQueryDto);
        return ApiResult.success(activeNetList,count);
    }

    /**
     * 新增互动行为信息
     * @param activeNet
     * @return
     */
    @Override
    public Result<String> saveEntity(ActiveNet activeNet) {
        log.info("开始新增互动行为信息: 参数{}", activeNet);
        AssertUtils.notNull(activeNet.getContentId(), "内容ID不为空");
        AssertUtils.notNull(activeNet.getType(), "互动类型不为空");
        AssertUtils.notNull(activeNet.getContentType(), "内容类型不为空");
        activeNet.setUserId(LocalThreadHolder.getUserId()); // 设置当前操作者用户ID
        activeNet.setCreateTime(LocalDateTime.now()); // 设置行为互动时间
        return ApiResult.success("互动行为信息新增成功");
    }
}
