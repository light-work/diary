package com.diary.providers.store.res;


import com.diary.common.StoreException;
import com.diary.entity.res.ResHouseEffect;
import org.guiceside.persistence.hibernate.dao.enums.Persistent;
import org.guiceside.persistence.hibernate.dao.hquery.Selector;

import java.util.List;


public interface ResHouseEffectStore {


    ResHouseEffect getById(Long id, Selector... selectors) throws StoreException;

    List<ResHouseEffect> getListByHouseId(Long jobId) throws StoreException;

    void save(ResHouseEffect resHouseEffect, Persistent persistent) throws StoreException;

    void delete(ResHouseEffect resHouseEffect) throws StoreException;

}
