package com.diary.providers.store.res;


import com.diary.common.StoreException;
import com.diary.entity.res.ResWbAccessToken;
import org.guiceside.commons.Page;
import org.guiceside.persistence.hibernate.dao.enums.Persistent;
import org.guiceside.persistence.hibernate.dao.hquery.Selector;

import java.util.List;


public interface ResWbAccessTokenStore {


    ResWbAccessToken getById(Long id, Selector... selectors) throws StoreException;

    Page<ResWbAccessToken> getPageList(int start,
                                     int limit, List<Selector> selectorList) throws StoreException;

    List<ResWbAccessToken> getList(List<Selector> selectorList) throws StoreException;

    void save(ResWbAccessToken resWbAccessToken, Persistent persistent) throws StoreException;

    void delete(ResWbAccessToken resWbAccessToken) throws StoreException;

}
