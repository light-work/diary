package com.diary.service.app;

import com.diary.common.StoreException;
import com.diary.entity.app.AppUserCouple;
import com.diary.providers.store.app.AppUserCoupleStore;
import com.google.inject.Singleton;
import org.guiceside.commons.Page;
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
public class AppUserCoupleService extends HQuery implements AppUserCoupleStore {

    @Transactional(type = TransactionType.READ_ONLY)
    public AppUserCouple getById(Long id, Selector... selectors) throws StoreException {
        return $(id, selectors).get(AppUserCouple.class);
    }

    @Override
    @Transactional(type = TransactionType.READ_ONLY)
    public Page<AppUserCouple> getPageList(int start, int limit, List<Selector> selectorList) throws StoreException {
        return $(selectorList).page(AppUserCouple.class, start, limit);
    }

    @Override
    @Transactional(type = TransactionType.READ_ONLY)
    public List<AppUserCouple> getByUserId(Long userId) throws StoreException {
        return $($alias("userId", "userId"), $eq("userId.id", userId)).list(AppUserCouple.class);
    }

    @Override
    @Transactional(type = TransactionType.READ_WRITE)
    public void delete(AppUserCouple appUserCouple) throws StoreException {
        $(appUserCouple).delete();
    }

    @Override
    @Transactional(type = TransactionType.READ_WRITE)
    public void save(AppUserCouple appUserCouple, Persistent persistent) throws StoreException {
        $(appUserCouple).save(persistent);
    }
}
