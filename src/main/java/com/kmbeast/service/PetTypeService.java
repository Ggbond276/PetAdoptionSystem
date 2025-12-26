package com.kmbeast.service;

import com.kmbeast.pojo.api.Result;
import com.kmbeast.pojo.dto.PetTypeQueryDto;
import com.kmbeast.pojo.entity.PetType;

import java.util.List;

/**
 * 宠物类型服务接口
 */
public interface PetTypeService {
    /**
     * 保存宠物类型
     * @param petType 宠物类型
     * @return 操作结果
     */
    Result<String> save(PetType petType);

    /**
     * 更新宠物类型
     * @param petType 宠物类型
     * @return 操作结果
     */
    Result<String> update(PetType petType);

    /**
     * 删除宠物类型
     * @param id 宠物类型主键ID
     * @return 操作结果
     */
    Result<String> delete(Integer id);

    /**
     * 查询宠物类型
     * @param petTypeQueryDto 查询参数
     * @return Result<List<PetType>> 返回封装类
     */
    Result<List<PetType>> query(PetTypeQueryDto petTypeQueryDto);


}
