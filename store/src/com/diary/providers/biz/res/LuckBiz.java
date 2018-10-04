package com.diary.providers.biz.res;


import com.diary.common.BizException;

public interface LuckBiz {

    String list(Integer start, Integer limit, String keyword) throws BizException;

    String add(String title,  Integer investPrice, Integer gainPrice, Double probability,String remarks) throws BizException;

    String edit(Long id, String title, Integer investPrice, Integer gainPrice, Double probability,String remarks) throws BizException;

    String enable(Long id) throws BizException;

    String disable(Long id) throws BizException;
}
