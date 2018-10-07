package com.diary.providers.biz.app;


import com.diary.common.BizException;

public interface UserLuxuryBiz {



    String buyLuxury(Long userId,Long luxuryId) throws BizException;

    String sellLuxury(Long userId,Long luxuryId) throws BizException;


}
