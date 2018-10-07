package com.diary.providers.biz.app;


import com.diary.common.BizException;

public interface UserLuckBiz {


    String applyLuck(Long userId,Long luckId) throws BizException;


}
