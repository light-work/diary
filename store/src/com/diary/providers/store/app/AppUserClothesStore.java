package com.diary.providers.store.app;


import com.diary.common.StoreException;
import com.diary.entity.app.AppUserClothes;
import com.diary.entity.app.AppUserLady;
import com.diary.entity.app.AppUserLimit;
import org.guiceside.commons.Page;
import org.guiceside.persistence.hibernate.dao.enums.Persistent;
import org.guiceside.persistence.hibernate.dao.hquery.Selector;

import java.util.List;


public interface AppUserClothesStore {


    AppUserClothes getById(Long id, Selector... selectors) throws StoreException;


    Page<AppUserClothes> getPageList(int start,
                                 int limit, List<Selector> selectorList) throws StoreException;

    List<AppUserClothes> getByUserId(Long userId) throws StoreException;

    List<AppUserClothes> getByUserIdClothesId(Long userId,Long clothesId) throws StoreException;

    void save(AppUserClothes appUserClothes, Persistent persistent) throws StoreException;

    void buy(AppUserClothes appUserClothes, Persistent persistent, AppUserLady appUserLady, AppUserLimit appUserLimit) throws StoreException;

    void sell(AppUserClothes appUserClothes, AppUserLady appUserLady,AppUserLimit appUserLimit) throws StoreException;

    void delete(AppUserClothes appUserClothes) throws StoreException;


    void delete(List<AppUserClothes> appUserClothesList) throws StoreException;

}
