package com.diary.providers.biz.res;


import com.diary.common.BizException;

public interface CarBiz {

    String list(Integer start, Integer limit, String keyword) throws BizException;

    String add(String title, Integer price, Integer sellPrice,Integer offsetBuy, Integer offsetSell, String remarks) throws BizException;

    String edit(Long id, String title, Integer buyPrice, Integer sellPrice, Integer offsetBuy, Integer offsetSell,String remarks) throws BizException;

    String enable(Long id) throws BizException;

    String disable(Long id) throws BizException;

    String addEffect(Long planId, String operation, String attrKey, Integer value) throws BizException;

    String editEffect(Long id, String operation, String attrKey, Integer value) throws BizException;

    String deleteEffect(Long id) throws BizException;

    String effectList(Long planId) throws BizException;


}
