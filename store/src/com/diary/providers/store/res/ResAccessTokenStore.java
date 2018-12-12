package com.diary.providers.store.res;


import com.diary.common.StoreException;
import com.diary.entity.res.ResAccessToken;
import org.guiceside.commons.Page;
import org.guiceside.persistence.hibernate.dao.enums.Persistent;
import org.guiceside.persistence.hibernate.dao.hquery.Selector;

import java.util.List;


public interface ResAccessTokenStore {


    ResAccessToken getById(Long id, Selector... selectors) throws StoreException;

    Page<ResAccessToken> getPageList(int start,
                             int limit, List<Selector> selectorList) throws StoreException;

    List<ResAccessToken> getList(List<Selector> selectorList) throws StoreException;

    void save(ResAccessToken resAccessToken, Persistent persistent) throws StoreException;

    void delete(ResAccessToken resAccessToken) throws StoreException;

}
