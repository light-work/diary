package com.diary.providers.store.res;


import com.diary.common.StoreException;
import com.diary.entity.res.ResLuxuryEffect;
import org.guiceside.persistence.hibernate.dao.enums.Persistent;
import org.guiceside.persistence.hibernate.dao.hquery.Selector;

import java.util.List;


public interface ResLuxuryEffectStore {


    ResLuxuryEffect getById(Long id, Selector... selectors) throws StoreException;

    List<ResLuxuryEffect> getListByLuxuryId(Long jobId) throws StoreException;

    void save(ResLuxuryEffect resLuxuryEffect, Persistent persistent) throws StoreException;

    void delete(ResLuxuryEffect resLuxuryEffect) throws StoreException;

}
