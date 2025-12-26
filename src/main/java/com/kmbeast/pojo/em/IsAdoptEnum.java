package com.kmbeast.pojo.em;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * 宠物领养状态枚举
 */
@Getter
@AllArgsConstructor
public enum IsAdoptEnum {

    NO_ADOPT(false, "未领养"),
    YES_ADOPT(true, "已领养");

    private final Boolean status;
    private final String name;

}
