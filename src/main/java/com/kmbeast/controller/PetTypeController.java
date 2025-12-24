package com.kmbeast.controller;


import com.kmbeast.aop.Pager;
import com.kmbeast.pojo.api.Result;
import com.kmbeast.pojo.dto.PetTypeQueryDto;
import com.kmbeast.pojo.entity.PetType;
import com.kmbeast.service.PetTypeService;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;

/**
 * 宠物类型控制器
 */
@RestController
@RequestMapping("/pet-type")
public class PetTypeController {

    @Resource
    private PetTypeService petTypeService;

    /**
     * 保存宠物类型
     * @param petType 宠物类型
     * @return 操作结果
     */
    @ResponseBody
    @PostMapping("/save")
    public Result<String> sava(@RequestBody PetType petType) {
        return petTypeService.save(petType);
    }

    /**
     * 更新宠物类型
     * @param petType 宠物类型
     * @return 操作结果
     */
    @ResponseBody
    @PutMapping("/update")
    public Result<String> update(@RequestBody PetType petType) {
        return petTypeService.update(petType);
    }

    /**
     * 删除宠物类型
     * @param id 宠物类型主键ID
     * @return 操作结果
     */
    @ResponseBody
    @DeleteMapping(value="/{id}")
    public Result<String> delete(@PathVariable Integer id) {
        return petTypeService.delete(id);
    }

     /**
      * 查询宠物类型
      * @param petTypeQueryDto 查询参数
      * @return Result<List<PetType>> 返回封装类
      */
     @Pager
     @ResponseBody
     @PostMapping("/query")
    public Result<List<PetType>> query(@RequestBody PetTypeQueryDto petTypeQueryDto) {
        return petTypeService.query(petTypeQueryDto);
    }



}
