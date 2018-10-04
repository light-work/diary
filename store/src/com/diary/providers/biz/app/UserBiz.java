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

    String applyPlan(Long userId,Long planId) throws BizException;

    String applyLuck(Long userId,Long luckId) throws BizException;

    String applyCouple(Long userId,Long coupleId) throws BizException;

    String buyHouse(Long userId,Long houseId) throws BizException;

    String sellHouse(Long userId,Long houseId) throws BizException;

    String buyCar(Long userId,Long carId) throws BizException;

    String sellCar(Long userId,Long carId) throws BizException;

    String buyClothes(Long userId,Long clothesId) throws BizException;

    String sellClothes(Long userId,Long clothesId) throws BizException;

    String buyLuxury(Long userId,Long luxuryId) throws BizException;

    String sellLuxury(Long userId,Long luxuryId) throws BizException;

    String nextDay(Long userId) throws BizException;

}
