package com.diary.providers.biz.app;


import com.diary.common.BizException;

public interface UserClothesBiz {


    String buyClothes(Long userId,Long clothesId) throws BizException;

    String sellClothes(Long userId,Long clothesId) throws BizException;


}
