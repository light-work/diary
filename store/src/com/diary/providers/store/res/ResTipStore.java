package com.diary.providers.store.res;


import com.diary.common.StoreException;
import com.diary.entity.res.ResTip;
import org.guiceside.commons.Page;
import org.guiceside.persistence.hibernate.dao.enums.Persistent;
import org.guiceside.persistence.hibernate.dao.hquery.Selector;

import java.util.List;


public interface ResTipStore {


    ResTip getById(Long id, Selector... selectors) throws StoreException;

    Page<ResTip> getPageList(int start,
                             int limit, List<Selector> selectorList) throws StoreException;

    List<ResTip> getList(List<Selector> selectorList) throws StoreException;

    void save(ResTip resTip, Persistent persistent) throws StoreException;

    void delete(ResTip resTip) throws StoreException;

}
