package com.yangge.finance.biz.domain;

import com.yangge.mybatis.help.DbField;
import com.yangge.mybatis.help.FieldResult;

import java.util.Collections;

public class MemberBindWxOpenIdField {
    public static DbField Id = new DbField("id","id","BIGINT","java.lang.Long");

    public static DbField AppId = new DbField("app_id","appId","VARCHAR","java.lang.String");

    public static DbField OpenId = new DbField("open_id","openId","VARCHAR","java.lang.String");

    public static DbField MemberId = new DbField("member_id","memberId","BIGINT","java.lang.Long");

    public static DbField Disable = new DbField("disable","disable","BIT","java.lang.Boolean");

    public static DbField CreateTime = new DbField("create_time","createTime","TIMESTAMP","java.util.Date");

    public static DbField UpdateTime = new DbField("update_time","updateTime","TIMESTAMP","java.util.Date");

    public static FieldResult setId(Long id) {
        return new FieldResult(Id, Collections.singletonList(id));
    }

    public static FieldResult setAppId(String appId) {
        return new FieldResult(AppId, Collections.singletonList(appId));
    }

    public static FieldResult setOpenId(String openId) {
        return new FieldResult(OpenId, Collections.singletonList(openId));
    }

    public static FieldResult setMemberId(Long memberId) {
        return new FieldResult(MemberId, Collections.singletonList(memberId));
    }

    public static FieldResult setDisable(Boolean disable) {
        return new FieldResult(Disable, Collections.singletonList(disable));
    }

    public static FieldResult setCreateTime(java.util.Date createTime) {
        return new FieldResult(CreateTime, Collections.singletonList(createTime));
    }

    public static FieldResult setUpdateTime(java.util.Date updateTime) {
        return new FieldResult(UpdateTime, Collections.singletonList(updateTime));
    }
}