package com.diary.providers.biz.app;


import com.diary.common.BizException;

public interface UserCoupleBiz {

    String relationship(Long userId,Long coupleId) throws BizException;

    String breakUp(Long userId,Long coupleId) throws BizException;
}
