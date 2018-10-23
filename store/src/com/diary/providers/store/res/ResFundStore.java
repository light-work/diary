package com.diary.providers.store.res;


import com.diary.common.StoreException;
import com.diary.entity.res.ResFund;
import org.guiceside.commons.Page;
import org.guiceside.persistence.hibernate.dao.enums.Persistent;
import org.guiceside.persistence.hibernate.dao.hquery.Selector;

import java.util.List;


public interface ResFundStore {


    ResFund getById(Long id, Selector... selectors) throws StoreException;

    Page<ResFund> getPageList(int start,
                              int limit, List<Selector> selectorList) throws StoreException;

    List<ResFund> getList(List<Selector> selectorList) throws StoreException;

    void save(ResFund resFund, Persistent persistent) throws StoreException;

    void delete(ResFund resFund) throws StoreException;

}
