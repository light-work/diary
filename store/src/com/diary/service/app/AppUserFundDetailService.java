package com.diary.service.app;

import com.diary.common.StoreException;
import com.diary.entity.app.AppUserFundDetail;
import com.diary.providers.store.app.AppUserFundDetailStore;
import com.google.inject.Singleton;
import org.guiceside.persistence.TransactionType;
import org.guiceside.persistence.Transactional;
import org.guiceside.persistence.hibernate.dao.enums.Persistent;
import org.guiceside.persistence.hibernate.dao.hquery.HQuery;
import org.guiceside.persistence.hibernate.dao.hquery.Selector;

import java.util.List;

/**
 * Created by Lara Croft on 2016/12/21.
 */
@Singleton
public class AppUserFundDetailService extends HQuery implements AppUserFundDetailStore {


    @Transactional(type = TransactionType.READ_ONLY)
    public AppUserFundDetail getById(Long id, Selector... selectors) throws StoreException {
        return $(id, selectors).get(AppUserFundDetail.class);
    }

    @Override
    @Transactional(type = TransactionType.READ_ONLY)
    public List<AppUserFundDetail> getByUserFundId(Long userFundId) throws StoreException {
        return $($eq("userFundId.id",userFundId)).list(AppUserFundDetail.class);
    }

    @Override
    @Transactional(type = TransactionType.READ_WRITE)
    public void delete(AppUserFundDetail appUserFundDetail) throws StoreException {
        $(appUserFundDetail).delete();
    }

    @Override
    @Transactional(type = TransactionType.READ_WRITE)
    public void save(AppUserFundDetail appUserFundDetail, Persistent persistent) throws StoreException {
        $(appUserFundDetail).save(persistent);

    }

    @Override
    @Transactional(type = TransactionType.READ_WRITE)
    public void delete(List<AppUserFundDetail> appUserFundDetailList) throws StoreException {
        $(appUserFundDetailList).delete();
    }
}
