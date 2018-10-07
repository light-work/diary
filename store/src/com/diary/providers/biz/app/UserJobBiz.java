package com.diary.providers.biz.app;


import com.diary.common.BizException;

public interface UserJobBiz {

    String applyJob(Long userId,Long jobId) throws BizException;

}
