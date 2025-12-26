package com.kmbeast.service.impl;

import com.kmbeast.mapper.PetTypeMapper;
import com.kmbeast.pojo.api.ApiResult;
import com.kmbeast.pojo.api.Result;
import com.kmbeast.pojo.dto.PetTypeQueryDto;
import com.kmbeast.pojo.entity.PetType;
import com.kmbeast.service.PetTypeService;
import com.kmbeast.utils.AssertUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * 宠物类型服务实现类
 */
@Service
public class PetTypeServiceImpl implements PetTypeService {

    private static final Logger log = LoggerFactory.getLogger(PetTypeService.class);
    @Resource
    private PetTypeMapper petTypeMapper;
    /**
     * 保存宠物类型
     * @param petType 宠物类型
     * @return 操作结果
     */
    @Override
    public Result<String> save(PetType petType) {
        log.info("开始保存宠物类型: 参数{}", petType);
        // 确保传入的宠物名字不能为空
        AssertUtils.hasText(petType.getName(), "宠物类型名称不能为空");
        // 如果传入的宠物类名系统已经存在 则不能进行新增
        PetType petTypeEntity = petTypeMapper.queryByName(petType.getName());
        // 确保传入的宠物类型实体不能为空
        AssertUtils.notNull(petTypeEntity!=null , "宠物类型名称已存在");
        petTypeMapper.save(petType);
        return ApiResult.success("宠物类别新增成功");
    }

    @Override
    public Result<String> update(PetType petType) {
        log.info("开始更新宠物类型: 参数{}", petType);
        // 确保要更新的宠物类名不可以为空
        AssertUtils.hasText(petType.getName(), "宠物类型名称不能为空");
        // 确保要更新的宠物类型实体不能为空
        PetType petTypeEntity = petTypeMapper.queryByName(petType.getName());
        AssertUtils.notNull(petTypeEntity!=null , "要更新的宠物类型不存在");
        petTypeMapper.update(petType);
        return ApiResult.success("宠物类别更新成功");
    }

    @Override
    public Result<String> delete(Integer id) {
        log.info("开始删除宠物类型: 参数{}", id);
//        // 确保传入的id不为空
//        AssertUtils.hasText(id.toString(), "宠物类型id不能为空");
//        // 确保要删除的宠物类型实体不能为空
//        PetType petTypeEntity = petTypeMapper.queryById(id);
//        AssertUtils.notNull(petTypeEntity!=null , "要删除的宠物类型不存在");
        petTypeMapper.deleteById(id);
        return ApiResult.success("宠物类别删除成功");
    }

    @Override
    public Result<List<PetType>> query(PetTypeQueryDto petTypeQueryDto) {
        log.info("开始查询宠物类型: 参数{}", petTypeQueryDto);
        // 查询符合条件的总条数 - 前端分页使用
        Integer count = petTypeMapper.queryCount(petTypeQueryDto);
        // 查询符合条件的数据项 - 前端分页使用
        List<PetType> petTypeList = petTypeMapper.query(petTypeQueryDto);
        return ApiResult.success(petTypeList, count);
    }
}
