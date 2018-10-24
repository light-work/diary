package com.diary.providers.store.app;


import com.diary.common.StoreException;
import com.diary.entity.app.AppUserFundDetail;
import org.guiceside.persistence.hibernate.dao.enums.Persistent;
import org.guiceside.persistence.hibernate.dao.hquery.Selector;

import java.util.List;


public interface AppUserFundDetailStore {

    AppUserFundDetail getById(Long id, Selector... selectors) throws StoreException;

    List<AppUserFundDetail> getByUserFundId(Long userFundId) throws StoreException;


    void save(AppUserFundDetail appUserFundDetail, Persistent persistent) throws StoreException;


    void delete(AppUserFundDetail appUserFundDetail) throws StoreException;

    void delete(List<AppUserFundDetail> appUserFundDetailList) throws StoreException;

}
