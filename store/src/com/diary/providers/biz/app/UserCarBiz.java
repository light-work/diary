package com.diary.providers.biz.app;


import com.diary.common.BizException;

public interface UserCarBiz {

    String buyCar(Long userId,Long carId) throws BizException;

    String sellCar(Long userId,Long carId) throws BizException;


}
