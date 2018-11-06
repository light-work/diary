package com.diary.providers.store.app;


import com.diary.common.StoreException;
import com.diary.entity.app.AppUserLimit;
import org.guiceside.persistence.hibernate.dao.enums.Persistent;

import java.util.List;


public interface AppUserLimitStore {

    List<AppUserLimit> getListByUserId(Long userId) throws StoreException;

    Integer getCountByUserIdDayAction(Long userId,Integer day,String action) throws StoreException;

    void save(AppUserLimit appUserLimit, Persistent persistent) throws StoreException;

    void delete(AppUserLimit appUserLimit) throws StoreException;

    void delete(List<AppUserLimit> appUserLimitList) throws StoreException;

}
