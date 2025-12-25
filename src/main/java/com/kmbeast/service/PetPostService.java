package com.kmbeast.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.kmbeast.pojo.api.Result;
import com.kmbeast.pojo.dto.PetPostQueryDto;
import com.kmbeast.pojo.entity.PetPost;
import com.kmbeast.pojo.vo.PetPostListItemVO;
import com.kmbeast.pojo.vo.PetPostVO;

import java.util.List;

public interface PetPostService extends IService<PetPost> {

    Result<String> saveEntity(PetPost petPost);

    Result<String> updateEntity(PetPost petPost);

    Result<List<PetPostListItemVO>> list(PetPostQueryDto petPostQueryDto);

    Result<PetPostVO> getById(Integer id);

    Result<String> audit(Integer id);
}
