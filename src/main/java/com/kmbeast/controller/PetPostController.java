package com.kmbeast.controller;


import com.kmbeast.aop.Pager;
import com.kmbeast.context.LocalThreadHolder;
import com.kmbeast.pojo.api.ApiResult;
import com.kmbeast.pojo.api.Result;
import com.kmbeast.pojo.dto.PetPostQueryDto;
import com.kmbeast.pojo.entity.Pet;
import com.kmbeast.pojo.entity.PetPost;
import com.kmbeast.pojo.vo.PetPostListItemVO;
import com.kmbeast.pojo.vo.PetPostVO;
import com.kmbeast.service.PetPostService;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.awt.print.Paper;
import java.util.List;

@RestController
@RequestMapping("/pet-post")
public class PetPostController {

    @Resource
    private PetPostService petPostService;

    /**
     * 新增宠物经验帖
     * @param petPost
     * @return
     */
    @ResponseBody
    @PostMapping(value ="/save")
    public Result<String> save(@RequestBody PetPost petPost) {
        return petPostService.saveEntity(petPost);
    }

    /**
     * 修改宠物经验帖
     * @param petPost
     * @return
     */
    @ResponseBody
    @PutMapping(value ="/update")
    public Result<String> update(@RequestBody PetPost petPost) {
        return petPostService.updateEntity(petPost);
    }

    /**
     * 删除宠物经验帖
     * @param id
     * @return
     */
    @ResponseBody
    @DeleteMapping(value ="/{id}")
    public Result<String> deleteById(@PathVariable Integer id) {
        petPostService.removeById(id);
        return ApiResult.success("删除成功");
    }

    /**
     * 根据ID查询宠物经验帖
     * @param id
     * @return
     */
    @Pager
    @ResponseBody
    @GetMapping(value = "/getById/{id}")
    public Result<PetPostVO> getById(@PathVariable Integer id) {
        return petPostService.getById(id);
    }

    /**
     * 查询用户发布的宠物经验帖列表
     * @param petPostQueryDto
     * @return
     */
    @Pager
    @ResponseBody
    @PostMapping(value = "/listUser")
    public Result<List<PetPostListItemVO>> listUser(@RequestBody PetPostQueryDto petPostQueryDto) {
         petPostQueryDto.setUserId(LocalThreadHolder.getUserId());
         return petPostService.list(petPostQueryDto);
    }

     /**
      * 查询宠物经验帖列表
      * @param petPostQueryDto
      * @return
      */
    @Pager
    @ResponseBody
    @PostMapping(value = "/list")
    public Result<List<PetPostListItemVO>> list(@RequestBody PetPostQueryDto petPostQueryDto) {
        return petPostService.list(petPostQueryDto);
    }

    /**
     * 宠物经验帖子审核
     *
     * @param id 主键ID
     * @return Result<String> 通用返回封装类
     */
    @ResponseBody
    @PutMapping(value = "/audit/{id}")
    public Result<String> audit(@PathVariable Integer id) {
        return petPostService.audit(id);
    }
}
