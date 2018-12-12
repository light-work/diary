package com.diary.providers.biz.app;


import com.diary.common.BizException;

public interface UserFormBiz {

    String submit(Long userId, String formId,String action) throws BizException;

}
