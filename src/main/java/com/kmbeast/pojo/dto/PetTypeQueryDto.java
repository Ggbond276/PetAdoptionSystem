package com.kmbeast.pojo.dto;

import lombok.Data;

@Data
public class PetTypeQueryDto extends QueryDto{
    /**
     * 宠物类型主键ID
     */
    private Integer id;
     /**
      * 宠物类型名称
      */
    private String name;
}
