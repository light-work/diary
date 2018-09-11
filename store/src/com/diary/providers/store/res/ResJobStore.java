package com.diary.providers.store.res;


import com.diary.common.StoreException;
import com.diary.entity.res.ResJob;
import org.guiceside.commons.Page;
import org.guiceside.persistence.hibernate.dao.enums.Persistent;
import org.guiceside.persistence.hibernate.dao.hquery.Selector;

import java.util.List;


public interface ResJobStore {


    ResJob getById(Long id, Selector... selectors) throws StoreException;

    Page<ResJob> getPageList(int start,
                              int limit, List<Selector> selectorList) throws StoreException;

    void save(ResJob resJob, Persistent persistent) throws StoreException;

    void delete(ResJob resJob) throws StoreException;

}
