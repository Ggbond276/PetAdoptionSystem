package com.kmbeast.pojo.em;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum IsDefaultEnum {
    NO_DEFAULT_ADDRESS(false, "非默认地址"),
    DEFAULT_ADDRESS(true, "默认地址");

    private final Boolean status;
    private final String name;
}
