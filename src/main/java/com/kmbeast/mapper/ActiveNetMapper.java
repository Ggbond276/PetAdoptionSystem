package com.kmbeast.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.kmbeast.pojo.dto.ActiveNetQueryDto;
import com.kmbeast.pojo.entity.ActiveNet;

import java.util.List;

public interface ActiveNetMapper extends BaseMapper<ActiveNet> {

    /**
     * 查询互动行为信息列表
     * @param activeNetQueryDto
     * @return
     */
    List<ActiveNet> query(ActiveNetQueryDto activeNetQueryDto);

    /**
     * 查询互动行为信息数量
     * @param activeNetQueryDto
     * @return
     */
    Integer count(ActiveNetQueryDto activeNetQueryDto);
}
