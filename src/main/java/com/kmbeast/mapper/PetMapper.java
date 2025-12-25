package com.kmbeast.mapper;

import com.kmbeast.pojo.dto.PetQueryDto;
import com.kmbeast.pojo.entity.Pet;
import com.kmbeast.pojo.vo.PetListItemVO;
import com.kmbeast.pojo.vo.PetVO;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface PetMapper {

    void save(Pet pet);

    void update(Pet pet);

    void deleteById(Integer id);

    PetVO getById(Integer id);

    List<PetListItemVO> queryListItem(PetQueryDto petQueryDto);

    Integer queryCount(PetQueryDto petQueryDto);
}
