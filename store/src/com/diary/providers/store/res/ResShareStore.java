package com.diary.providers.store.res;


import com.diary.common.StoreException;
import com.diary.entity.res.ResShare;
import org.guiceside.commons.Page;
import org.guiceside.persistence.hibernate.dao.enums.Persistent;
import org.guiceside.persistence.hibernate.dao.hquery.Selector;

import java.util.List;


public interface ResShareStore {


    ResShare getById(Long id, Selector... selectors) throws StoreException;

    ResShare getByGenderId(Integer gender) throws StoreException;

    Page<ResShare> getPageList(int start,
                             int limit, List<Selector> selectorList) throws StoreException;

    List<ResShare> getList(List<Selector> selectorList) throws StoreException;

    void save(ResShare resShare, Persistent persistent) throws StoreException;

    void delete(ResShare resShare) throws StoreException;

}
