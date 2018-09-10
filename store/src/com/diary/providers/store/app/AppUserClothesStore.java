package com.diary.providers.store.app;


import com.diary.common.StoreException;
import com.diary.entity.app.AppUserClothes;
import org.guiceside.commons.Page;
import org.guiceside.persistence.hibernate.dao.enums.Persistent;
import org.guiceside.persistence.hibernate.dao.hquery.Selector;

import java.util.List;


public interface AppUserClothesStore {


    AppUserClothes getById(Long id, Selector... selectors) throws StoreException;


    Page<AppUserClothes> getPageList(int start,
                                 int limit, List<Selector> selectorList) throws StoreException;

    List<AppUserClothes> getByUserId(Long userId) throws StoreException;

    void save(AppUserClothes appUserClothes, Persistent persistent) throws StoreException;

    void delete(AppUserClothes appUserClothes) throws StoreException;

}
