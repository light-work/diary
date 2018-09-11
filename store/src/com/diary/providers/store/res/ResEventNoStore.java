package com.diary.providers.store.res;


import com.diary.common.StoreException;
import com.diary.entity.res.ResEventNo;
import org.guiceside.persistence.hibernate.dao.enums.Persistent;
import org.guiceside.persistence.hibernate.dao.hquery.Selector;


public interface ResEventNoStore {


    ResEventNo getById(Long id, Selector... selectors) throws StoreException;

    ResEventNo getByEventId(Long eventId) throws StoreException;

    void save(ResEventNo resEventNo, Persistent persistent) throws StoreException;

    void delete(ResEventNo article) throws StoreException;

}
