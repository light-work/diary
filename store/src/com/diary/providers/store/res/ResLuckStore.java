package com.diary.providers.store.res;


import com.diary.common.StoreException;
import com.diary.entity.res.ResLuck;
import org.guiceside.commons.Page;
import org.guiceside.persistence.hibernate.dao.enums.Persistent;
import org.guiceside.persistence.hibernate.dao.hquery.Selector;

import java.util.List;


public interface ResLuckStore {


    ResLuck getById(Long id, Selector... selectors) throws StoreException;

    Page<ResLuck> getPageList(int start,
                             int limit, List<Selector> selectorList) throws StoreException;

    List<ResLuck> getList(List<Selector> selectorList) throws StoreException;

    void save(ResLuck resLuck, Persistent persistent) throws StoreException;

    void delete(ResLuck resLuck) throws StoreException;

}
