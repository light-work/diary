package com.diary.service.app;

import com.diary.common.StoreException;
import com.diary.entity.app.AppUserHouse;
import com.diary.providers.store.app.AppUserHouseStore;
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
public class AppUserHouseService extends HQuery implements AppUserHouseStore {

    @Transactional(type = TransactionType.READ_ONLY)
    public AppUserHouse getById(Long id, Selector... selectors) throws StoreException {
        return $(id, selectors).get(AppUserHouse.class);
    }

    @Override
    @Transactional(type = TransactionType.READ_ONLY)
    public Page<AppUserHouse> getPageList(int start, int limit, List<Selector> selectorList) throws StoreException {
        return $(selectorList).page(AppUserHouse.class, start, limit);
    }

    @Override
    @Transactional(type = TransactionType.READ_ONLY)
    public List<AppUserHouse> getByUserId(Long userId) throws StoreException {
        return $($alias("userId", "userId"),$alias("houseId", "houseId"),  $eq("userId.id", userId)).list(AppUserHouse.class);
    }

    @Override
    @Transactional(type = TransactionType.READ_WRITE)
    public void delete(AppUserHouse appUserHouse) throws StoreException {
        $(appUserHouse).delete();
    }

    @Override
    @Transactional(type = TransactionType.READ_WRITE)
    public void save(AppUserHouse appUserHouse, Persistent persistent) throws StoreException {
        $(appUserHouse).save(persistent);
    }
}
