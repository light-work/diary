package com.diary.providers.store.res;


import com.diary.common.StoreException;
import com.diary.entity.res.ResLuxury;
import org.guiceside.commons.Page;
import org.guiceside.persistence.hibernate.dao.enums.Persistent;
import org.guiceside.persistence.hibernate.dao.hquery.Selector;

import java.util.List;


public interface ResLuxuryStore {


    ResLuxury getById(Long id, Selector... selectors) throws StoreException;

    Page<ResLuxury> getPageList(int start,
                             int limit, List<Selector> selectorList) throws StoreException;

    void save(ResLuxury resLuxury, Persistent persistent) throws StoreException;

    void delete(ResLuxury resLuxury) throws StoreException;

}
