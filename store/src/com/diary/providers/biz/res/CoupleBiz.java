package com.diary.providers.biz.res;


import com.diary.common.BizException;

public interface CoupleBiz {

    String list(Integer start, Integer limit, String keyword) throws BizException;

    String add(String title, Integer price, Integer gender, String remarks) throws BizException;

    String edit(Long id, String title, Integer price, Integer gender, String remarks) throws BizException;

    String enable(Long id) throws BizException;

    String disable(Long id) throws BizException;

    String addEffect(Long coupleId, String operation, String attrKey, Integer value) throws BizException;

    String editEffect(Long id, String operation, String attrKey, Integer value) throws BizException;

    String deleteEffect(Long id) throws BizException;

    String effectList(Long coupleId) throws BizException;

    String addRequire(Long coupleId, String attrKey, Integer value) throws BizException;

    String editRequire(Long id, String attrKey, Integer value) throws BizException;

    String deleteRequire(Long id) throws BizException;

    String requireList(Long coupleId) throws BizException;
}
