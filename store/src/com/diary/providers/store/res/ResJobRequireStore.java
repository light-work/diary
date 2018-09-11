package com.diary.providers.store.res;


import com.diary.common.StoreException;
import com.diary.entity.res.ResJobRequire;
import org.guiceside.persistence.hibernate.dao.enums.Persistent;
import org.guiceside.persistence.hibernate.dao.hquery.Selector;

import java.util.List;


public interface ResJobRequireStore {


    ResJobRequire getById(Long id, Selector... selectors) throws StoreException;

    List<ResJobRequire> getListByJobId(Long jobId) throws StoreException;

    List<ResJobRequire> getList(List<Selector> selectorList) throws StoreException;

    void save(ResJobRequire resJobRequire, Persistent persistent) throws StoreException;

    void delete(ResJobRequire resJobRequire) throws StoreException;

}
