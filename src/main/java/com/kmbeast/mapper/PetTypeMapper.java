package com.kmbeast.mapper;

import com.kmbeast.pojo.dto.PetTypeQueryDto;
import com.kmbeast.pojo.entity.PetType;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface PetTypeMapper {
    /**
     * 保存宠物类型
     * @param petType 宠物类型
     * @return 操作结果
     */
    void save(PetType petType);

    /**
     * 更新宠物类型
     * @param petType 宠物类型
     * @return 操作结果
     */
    void update(PetType petType);

    /**
     * 删除宠物类型
     * @param id 宠物类型主键ID
     */
    void deleteById(@Param(value = "id") Integer id);

    /**
     * 查询宠物类型
     * @param petTypeQueryDto
     */
    List<PetType> query(PetTypeQueryDto petTypeQueryDto);

    /**
     * 查询宠物类型数量
     * @param petTypeQueryDto
     * @return 宠物类型数量
     */
    Integer queryCount(PetTypeQueryDto petTypeQueryDto);

    /**
     * 根据名称查询宠物类型
     * @param name 宠物类型名称
     * @return PetType 宠物类型实体
     */
    PetType queryByName(@Param(value = "name") String name);

    PetType queryById(@Param(value = "id") Integer id);
}
