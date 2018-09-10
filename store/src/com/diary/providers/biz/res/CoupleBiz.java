package com.diary.providers.biz.res;


import com.diary.common.BizException;

public interface CoupleBiz {

    String list(Integer start, Integer limit, String keyword) throws BizException;

    String add(String title, Long price, Integer gender, String desc) throws BizException;

    String edit(Long id, String title, Long price, Integer gender, String desc) throws BizException;

    String enable(Long id) throws BizException;

    String disable(Long id) throws BizException;

    String addEffect(Long coupleId, String operation, String attrKey, Long value) throws BizException;

    String editEffect(Long id, String operation, String attrKey, Long value) throws BizException;

    String deleteEffect(Long id) throws BizException;

    String effectList(Long coupleId) throws BizException;

    String addRequire(Long coupleId, String attrKey, Long value) throws BizException;

    String editRequire(Long id, String attrKey, Long value) throws BizException;

    String deleteRequire(Long id) throws BizException;

    String requireList(Long coupleId) throws BizException;
}
