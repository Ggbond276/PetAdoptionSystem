package com.kmbeast.pojo.em;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.omg.CORBA.NO_RESOURCES;

@Getter
@AllArgsConstructor
public enum IsRecommendEnum {

    NO_RECOMMEND(false, "未推荐"),
    RECOMMEND(true, "已推荐");

    private final Boolean status;
    private final String name;
}
