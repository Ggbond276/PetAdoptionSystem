package com.kmbeast.pojo.em;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum IsVaccineEnum {
    NO_VACCINE(false, "未接种"),
    VACCINE(true, "已接种");

    private final Boolean status;
    private final String name;
}

