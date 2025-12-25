package com.kmbeast.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.kmbeast.pojo.api.Result;
import com.kmbeast.pojo.dto.ActiveNetQueryDto;
import com.kmbeast.pojo.entity.ActiveNet;

import java.util.List;

public interface ActiveNetService extends IService<ActiveNet> {
    /**
     * 查询互动行为信息列表
     * @param activeNetQueryDto
     * @return
     */
    Result<List<ActiveNet>> query(ActiveNetQueryDto activeNetQueryDto);

    /**
     * 新增互动行为信息
     * @param activeNet
     * @return
     */
    Result<String> saveEntity(ActiveNet activeNet);
}
