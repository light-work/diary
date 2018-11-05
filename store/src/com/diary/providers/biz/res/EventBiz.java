package com.diary.providers.biz.res;


import com.diary.common.BizException;

public interface EventBiz {

    String list(Integer start, Integer limit,Integer gender,String source, String keyword) throws BizException;

    String addEvent(Integer gender,String source,String content) throws BizException;

    String editEvent(Long id, String content) throws BizException;

    String enable(Long id) throws BizException;

    String disable(Long id) throws BizException;

    String upResult(Long id) throws BizException;

    String downResult(Long id) throws BizException;

    String addResult(Long eventId,String resultText, String content) throws BizException;

    String editResult(Long id, String resultText, String content) throws BizException;

    String enableResult(Long resultId) throws BizException;

    String disableResult(Long resultId) throws BizException;

    String addEffect(Long resultId, String operation, String attrKey, Integer value,String percent) throws BizException;

    String editEffect(Long id, String operation, String attrKey, Integer value,String percent) throws BizException;

    String deleteEffect(Long id) throws BizException;


    String setRequire(Long resultId, String compare, String attrKey, Integer value) throws BizException;

    String clearRequire(Long resultId) throws BizException;

}
