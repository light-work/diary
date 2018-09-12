package com.diary.providers.store.res;


import com.diary.common.StoreException;
import com.diary.entity.res.ResClothesEffect;
import org.guiceside.persistence.hibernate.dao.enums.Persistent;
import org.guiceside.persistence.hibernate.dao.hquery.Selector;

import java.util.List;


public interface ResClothesEffectStore {


    ResClothesEffect getById(Long id, Selector... selectors) throws StoreException;

    List<ResClothesEffect> getListByClothesId(Long clothesId) throws StoreException;

    List<ResClothesEffect> getList(List<Selector> selectorList) throws StoreException;

    void save(ResClothesEffect resClothesEffect, Persistent persistent) throws StoreException;

    void delete(ResClothesEffect resClothesEffect) throws StoreException;

}
