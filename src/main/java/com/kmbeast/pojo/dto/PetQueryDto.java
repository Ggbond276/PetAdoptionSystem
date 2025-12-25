package com.kmbeast.pojo.dto;

import lombok.Data;

@Data
public class PetQueryDto extends QueryDto {
    private String name; // 宠物名称

    private Boolean isRecommend; // 是否推荐

    private Integer petTypeId; // 宠物类型主键ID
}
