package com.yangge.finance.biz.mapper;

import com.yangge.finance.biz.domain.Member;
import com.yangge.mybatis.help.CommonMapper;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface MemberMapper extends CommonMapper<Member> {
}