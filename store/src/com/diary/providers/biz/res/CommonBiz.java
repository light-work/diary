package com.diary.providers.biz.res;


import com.diary.common.BizException;

public interface CommonBiz {

    String getAttr(Integer gender) throws BizException;

    String getOperation() throws BizException;
}
