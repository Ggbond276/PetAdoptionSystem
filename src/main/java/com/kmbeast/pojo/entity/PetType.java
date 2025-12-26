package com.kmbeast.pojo.entity;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PetType {
    /**
     * 宠物类型主键ID
     */
    private Integer id;
     /**
      * 宠物类型名称
      */
    private String name;
}
