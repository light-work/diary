package com.diary.providers.store.res;


import com.diary.common.StoreException;
import com.diary.entity.res.ResHouse;
import org.guiceside.commons.Page;
import org.guiceside.persistence.hibernate.dao.enums.Persistent;
import org.guiceside.persistence.hibernate.dao.hquery.Selector;

import java.util.List;


public interface ResHouseStore {


    ResHouse getById(Long id, Selector... selectors) throws StoreException;

    Page<ResHouse> getPageList(int start,
                             int limit, List<Selector> selectorList) throws StoreException;

    void save(ResHouse resHouse, Persistent persistent) throws StoreException;

    void delete(ResHouse resHouse) throws StoreException;

}
