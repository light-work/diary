package com.diary.providers.store.res;


import com.diary.common.StoreException;
import com.diary.entity.res.ResEventYesResult;
import org.guiceside.persistence.hibernate.dao.enums.Persistent;
import org.guiceside.persistence.hibernate.dao.hquery.Selector;

import java.util.List;


public interface ResEventYesResultStore {


    ResEventYesResult getById(Long id, Selector... selectors) throws StoreException;

    List<ResEventYesResult> getByYesId(Long yesId) throws StoreException;

    List<ResEventYesResult> getByList(List<Selector> selectorList) throws StoreException;

    void save(ResEventYesResult resEventYesResult, Persistent persistent) throws StoreException;

    void delete(ResEventYesResult resEventYesResult) throws StoreException;

}
