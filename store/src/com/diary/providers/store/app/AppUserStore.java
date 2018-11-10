package com.diary.providers.store.app;


import com.diary.common.StoreException;
import com.diary.entity.app.AppUser;
import org.guiceside.commons.Page;
import org.guiceside.persistence.hibernate.dao.enums.Persistent;
import org.guiceside.persistence.hibernate.dao.hquery.Selector;

import java.util.List;


public interface AppUserStore {


    AppUser getById(Long id, Selector... selectors) throws StoreException;

    Page<AppUser> getPageList(int start,
                              int limit, List<Selector> selectorList) throws StoreException;

    AppUser getByOpenId(String openId) throws StoreException;

    void save(AppUser appUser, Persistent persistent) throws StoreException;


}
