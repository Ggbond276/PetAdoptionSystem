package com.kmbeast.service.impl;

import com.kmbeast.mapper.PetMapper;
import com.kmbeast.pojo.api.ApiResult;
import com.kmbeast.pojo.api.Result;
import com.kmbeast.pojo.dto.PetQueryDto;
import com.kmbeast.pojo.em.IsAdoptEnum;
import com.kmbeast.pojo.em.IsRecommendEnum;
import com.kmbeast.pojo.entity.Pet;
import com.kmbeast.pojo.vo.PetListItemVO;
import com.kmbeast.pojo.vo.PetVO;
import com.kmbeast.service.PetService;
import com.kmbeast.utils.AssertUtils;
import org.springframework.stereotype.Service;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.annotation.Resource;
import java.time.LocalDateTime;
import java.util.List;

@Service
public class PetServiceImpl implements PetService {
    /**
     * 日志记录器
     */
    private Logger log = LoggerFactory.getLogger(PetServiceImpl.class);

    @Resource
    private PetMapper petMapper;

    /**
     * 新增宠物
     * @param pet
     * @return
     */
    @Override
    public Result<String> save(Pet pet) {
        log.info("开始新增宠物: 参数{}", pet);
        // 参数校验
        petParamCheck(pet);
        pet.setIsAdopt(IsAdoptEnum.NO_ADOPT.getStatus());
        pet.setCreateTime(LocalDateTime.now());
        petMapper.save(pet);
        return ApiResult.success("宠物信息新增成功");
    }

    /**
     * 更新宠物信息
     * @param pet
     * @return
     */
    @Override
    public Result<String> update(Pet pet) {
        log.info("开始更新宠物: 参数{}", pet);
        // 参数校验
        petParamCheck(pet);
        petMapper.update(pet);
        return ApiResult.success("宠物信息更新成功");
    }

    /**
     * 删除宠物信息
     * @param id
     * @return
     */
    @Override
    public Result<String> deleteById(Integer id) {
        log.info("开始删除宠物: 参数{}", id);
        petMapper.deleteById(id);
        return ApiResult.success("宠物信息删除成功");
    }

    /**
     * 根据ID查询宠物详情
     * @param id
     * @return
     */
    @Override
    public Result<PetVO> getById(Integer id) {
        log.info("开始查询宠物详情: 参数{}", id);
        PetVO petVO = petMapper.getById(id);
        return ApiResult.success(petVO);
    }

    /**
     * 查询宠物列表
     * @param petQueryDto
     * @return
     */
    @Override
    public Result<List<PetListItemVO>> list(PetQueryDto petQueryDto) {
        log.info("开始查询宠物列表: 参数{}", petQueryDto);
        List<PetListItemVO> petListItemVOS = petMapper.queryListItem(petQueryDto);
        Integer count = petMapper.queryCount(petQueryDto);
        return ApiResult.success(petListItemVOS, count);
    }

    /**
     * 查询手动推荐的宠物数据，类似于banner效果
     * @return Result<List<PetListItemVO>>
     */
    @Override
    public Result<List<PetListItemVO>> recommend() {
        PetQueryDto petQueryDto = new PetQueryDto();
        petQueryDto.setIsRecommend(IsRecommendEnum.RECOMMEND.getStatus());
        List<PetListItemVO> petListItemVOS = petMapper.queryListItem(petQueryDto);
        if (!petListItemVOS.isEmpty()) {
            return ApiResult.success(petListItemVOS);
        }
        // 如果系统没有推荐，怎么办?
        PetQueryDto defaultPetQueryDto = new PetQueryDto();
        defaultPetQueryDto.setCurrent(0);
        defaultPetQueryDto.setSize(3);
        List<PetListItemVO> defaultPetListItemVOS = petMapper.queryListItem(defaultPetQueryDto);
        return ApiResult.success(defaultPetListItemVOS);
    }

    /**
     * 校验宠物参数
     * @param pet
     */
    private void petParamCheck(Pet pet) {
        AssertUtils.hasText(pet.getName(), "宠物名不能为空");
        AssertUtils.hasText(pet.getCover(), "请上传一张封面");
        AssertUtils.hasText(pet.getAddress(), "请补充宠物所在地");
        AssertUtils.notNull(pet.getPetTypeId(), "请选择宠物类型");
        AssertUtils.notNull(pet.getAge(), "请填写宠物年龄");
        AssertUtils.notNull(pet.getIsVaccine(), "请选择宠物是否已经接种疫苗");
        AssertUtils.notNull(pet.getIsRecommend(), "请选择是否推荐");
    }
}
