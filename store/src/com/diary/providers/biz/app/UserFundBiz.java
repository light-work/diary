package com.diary.providers.biz.app;


import com.diary.common.BizException;

public interface UserFundBiz {


    String buyFund(Long userId, Long fundId,Integer money) throws BizException;

    String sellFund(Long userId, Long fundId,Integer money) throws BizException;

    String market(Long userId) throws BizException;

    String trade(Long userId,Long fundId) throws BizException;

}
