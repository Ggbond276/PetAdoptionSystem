package com.kmbeast.pojo.em;


import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum IsReplyEnum {


    NO_REPLY(false,"未回复"),
    REPLY(true,"已回复");

    private final Boolean status;
    private final String name;

}
