package com.diary.providers.store.res;


import com.diary.common.StoreException;
import com.diary.entity.res.ResCouple;
import org.guiceside.commons.Page;
import org.guiceside.persistence.hibernate.dao.enums.Persistent;
import org.guiceside.persistence.hibernate.dao.hquery.Selector;

import java.util.List;


public interface ResCoupleStore {


    ResCouple getById(Long id, Selector... selectors) throws StoreException;

    Page<ResCouple> getPageList(int start,
                             int limit, List<Selector> selectorList) throws StoreException;


    List<ResCouple> getList(List<Selector> selectorList) throws StoreException;

    void save(ResCouple resCouple, Persistent persistent) throws StoreException;

    void delete(ResCouple resCouple) throws StoreException;

}
