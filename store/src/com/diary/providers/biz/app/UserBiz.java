package com.diary.providers.biz.app;


import com.diary.common.BizException;

public interface UserBiz {


    String login(String code) throws BizException;

    String start(Long userId) throws BizException;

    String refresh(Long userId) throws BizException;

    String updateUserInfo(Long userId, String userNickName, String userAvatarUrl,
                          String userGender,
                          String userCity, String userProvince, String userCountry) throws BizException;

    String resData(Long userId) throws BizException;

    String applyJob(Long userId,Long jobId) throws BizException;

    String callPlan(Long userId,Long planId) throws BizException;

}
