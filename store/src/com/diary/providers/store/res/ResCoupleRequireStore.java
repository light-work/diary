package com.diary.providers.store.res;


import com.diary.common.StoreException;
import com.diary.entity.res.ResCoupleRequire;
import org.guiceside.persistence.hibernate.dao.enums.Persistent;
import org.guiceside.persistence.hibernate.dao.hquery.Selector;

import java.util.List;


public interface ResCoupleRequireStore {


    ResCoupleRequire getById(Long id, Selector... selectors) throws StoreException;

    List<ResCoupleRequire> getListByCoupleId(Long coupleId) throws StoreException;

    List<ResCoupleRequire> getList(List<Selector> selectorList) throws StoreException;


    void save(ResCoupleRequire resCoupleRequire, Persistent persistent) throws StoreException;

    void delete(ResCoupleRequire resCoupleRequire) throws StoreException;

}
