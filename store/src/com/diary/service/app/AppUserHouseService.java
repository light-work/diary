package com.diary.service.app;

import com.diary.common.StoreException;
import com.diary.entity.app.AppUserHouse;
import com.diary.entity.app.AppUserLimit;
import com.diary.entity.app.AppUserMan;
import com.diary.providers.store.app.AppUserHouseStore;
import com.google.inject.Inject;
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

    @Inject
    private AppUserManService appUserManService;

    @Inject
    private AppUserLimitService appUserLimitService;

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
        return $($alias("userId", "userId"),$alias("houseId", "houseId"),  $eq("userId.id", userId),
                $order("houseId.buyPrice",false)).list(AppUserHouse.class);
    }

    @Override
    @Transactional(type = TransactionType.READ_ONLY)
    public Integer getCountByUserId(Long userId) throws StoreException {
        return $($eq("userId.id", userId),$count("id")).value(AppUserHouse.class,Integer.class);
    }

    @Override
    @Transactional(type = TransactionType.READ_ONLY)
    public List<AppUserHouse> getByUserIdHouseId(Long userId, Long houseId) throws StoreException {
        return $($alias("userId", "userId"),$alias("houseId", "houseId"),
                $eq("userId.id", userId),$eq("houseId.id", houseId)).list(AppUserHouse.class);
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

    @Override
    @Transactional(type = TransactionType.READ_WRITE)
    public void buy(AppUserHouse appUserHouse, Persistent persistent, AppUserMan appUserMan,AppUserLimit appUserLimit) throws StoreException {
        $(appUserHouse).save(persistent);
        appUserManService.save(appUserMan, Persistent.UPDATE);
        appUserLimitService.save(appUserLimit,Persistent.SAVE);
    }

    @Override
    @Transactional(type = TransactionType.READ_WRITE)
    public void sell(AppUserHouse appUserHouse, AppUserMan appUserMan,AppUserLimit appUserLimit) throws StoreException {
        $(appUserHouse).delete();
        appUserManService.save(appUserMan, Persistent.UPDATE);
        appUserLimitService.save(appUserLimit,Persistent.SAVE);
    }
}
