package com.diary.providers.store.res;


import com.diary.common.StoreException;
import com.diary.entity.res.ResEventYes;
import org.guiceside.persistence.hibernate.dao.enums.Persistent;
import org.guiceside.persistence.hibernate.dao.hquery.Selector;


public interface ResEventYesStore {


    ResEventYes getById(Long id, Selector... selectors) throws StoreException;

    ResEventYes getByEventId(Long eventId) throws StoreException;

    void save(ResEventYes resEventYes, Persistent persistent) throws StoreException;

    void delete(ResEventYes article) throws StoreException;

}
