package com.kmbeast.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.kmbeast.pojo.dto.PetPostQueryDto;
import com.kmbeast.pojo.entity.Pet;
import com.kmbeast.pojo.entity.PetPost;
import com.kmbeast.pojo.vo.PetPostListItemVO;
import com.kmbeast.pojo.vo.PetPostVO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;


@Mapper
public interface PetPostMapper extends BaseMapper<PetPost> {
     List<PetPostListItemVO> list(PetPostQueryDto petPostQueryDto);

     Integer listCount(PetPostQueryDto petPostQueryDto);

     PetPostVO getById(@Param("id") Integer id);
}
