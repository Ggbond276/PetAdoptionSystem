package com.kmbeast.pojo.em;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum IsAuditEnum {

    NO_AUDIT(false, "未审核"),
    AUDIT(true, "已审核");

    private final Boolean status;
    private final String name;
}
