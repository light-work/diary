package com.diary.providers.biz.app;


import com.diary.common.BizException;

public interface UserPlanBiz {


    String applyPlan(Long userId,Long planId) throws BizException;

    String findEvent(Long userId,Long findEventId) throws BizException;

}
