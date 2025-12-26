package com.kmbeast.pojo.em;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum IsTopEnum {

    NO_TOP(false,"非精华帖"),
    TOP(true,"精华帖");

    private final Boolean status;
    private final String name;
}
