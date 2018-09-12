package com.diary.providers.store.res;


import com.diary.common.StoreException;
import com.diary.entity.res.ResPlan;
import org.guiceside.commons.Page;
import org.guiceside.persistence.hibernate.dao.enums.Persistent;
import org.guiceside.persistence.hibernate.dao.hquery.Selector;

import java.util.List;


public interface ResPlanStore {


    ResPlan getById(Long id, Selector... selectors) throws StoreException;

    Integer getMaxOrder() throws StoreException;

    Page<ResPlan> getPageList(int start,
                             int limit, List<Selector> selectorList) throws StoreException;

    void save(ResPlan resPlan, Persistent persistent) throws StoreException;

    void delete(ResPlan resPlan) throws StoreException;

}
