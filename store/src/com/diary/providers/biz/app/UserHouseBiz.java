package com.diary.providers.biz.app;


import com.diary.common.BizException;

public interface UserHouseBiz {

    String buyHouse(Long userId,Long houseId) throws BizException;

    String sellHouse(Long userId,Long houseId) throws BizException;


}
