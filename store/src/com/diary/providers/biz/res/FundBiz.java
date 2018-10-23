package com.diary.providers.biz.res;


import com.diary.common.BizException;

public interface FundBiz {

    String list(Integer start, Integer limit, String keyword) throws BizException;

    String add(String title, Double minNum, Double maxNum, Double probability, String remarks) throws BizException;

    String edit(Long id, String title, Double minNum, Double maxNum, Double probability, String remarks) throws BizException;

    String enable(Long id) throws BizException;

    String disable(Long id) throws BizException;
}
