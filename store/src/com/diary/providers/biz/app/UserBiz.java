package com.diary.providers.biz.app;


import com.diary.common.BizException;

public interface UserBiz {


    String login(String code,Long userId,String nickName, String avatarUrl,
                 Integer gender,
                 String city, String province, String country) throws BizException;

    String start(Long userId) throws BizException;

    String refresh(Long userId) throws BizException;

    String updateUserInfo(Long userId, String userNickName, String userAvatarUrl,
                          String userGender,
                          String userCity, String userProvince, String userCountry) throws BizException;

    String resData(Long userId) throws BizException;

    String nextDay(Long userId) throws BizException;

}
