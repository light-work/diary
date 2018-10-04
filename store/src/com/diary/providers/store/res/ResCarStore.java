package com.diary.providers.store.res;


import com.diary.common.StoreException;
import com.diary.entity.res.ResCar;
import org.guiceside.commons.Page;
import org.guiceside.persistence.hibernate.dao.enums.Persistent;
import org.guiceside.persistence.hibernate.dao.hquery.Selector;

import java.util.List;


public interface ResCarStore {


    ResCar getById(Long id, Selector... selectors) throws StoreException;

    Page<ResCar> getPageList(int start,
                             int limit, List<Selector> selectorList) throws StoreException;

    List<ResCar> getList(List<Selector> selectorList) throws StoreException;

    void save(ResCar resCar, Persistent persistent) throws StoreException;

    void delete(ResCar resCar) throws StoreException;

}
