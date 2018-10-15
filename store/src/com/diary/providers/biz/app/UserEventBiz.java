package com.diary.providers.biz.app;


import com.diary.common.BizException;

public interface UserEventBiz {

    String findEvent(Long userId) throws BizException;

    String loadEvent(Long userId, Long eventId) throws BizException;

    String applyResult(Long userId, Long resultId) throws BizException;

}
