package com.diary.service.app;

import com.diary.common.StoreException;
import com.diary.entity.app.AppUserLady;
import com.diary.entity.app.AppUserLimit;
import com.diary.entity.app.AppUserLuxury;
import com.diary.providers.store.app.AppUserLuxuryStore;
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
public class AppUserLuxuryService extends HQuery implements AppUserLuxuryStore {

    @Inject
    private AppUserLadyService appUserLadyService;

    @Inject
    private AppUserLimitService appUserLimitService;


    @Transactional(type = TransactionType.READ_ONLY)
    public AppUserLuxury getById(Long id, Selector... selectors) throws StoreException {
        return $(id, selectors).get(AppUserLuxury.class);
    }

    @Override
    @Transactional(type = TransactionType.READ_ONLY)
    public Page<AppUserLuxury> getPageList(int start, int limit, List<Selector> selectorList) throws StoreException {
        return $(selectorList).page(AppUserLuxury.class, start, limit);
    }

    @Override
    @Transactional(type = TransactionType.READ_ONLY)
    public List<AppUserLuxury> getByUserId(Long userId) throws StoreException {
        return $($alias("userId", "userId"),$alias("luxuryId", "luxuryId"), $eq("userId.id", userId)).list(AppUserLuxury.class);
    }

    @Override
    @Transactional(type = TransactionType.READ_ONLY)
    public List<AppUserLuxury> getByUserIdLuxuryId(Long userId, Long luxuryId) throws StoreException {
        return $($alias("userId", "userId"),$alias("luxuryId", "luxuryId"),
                $eq("userId.id", userId),$eq("luxuryId.id", luxuryId)).list(AppUserLuxury.class);
    }

    @Override
    @Transactional(type = TransactionType.READ_WRITE)
    public void delete(AppUserLuxury appUserLuxury) throws StoreException {
        $(appUserLuxury).delete();
    }

    @Override
    @Transactional(type = TransactionType.READ_WRITE)
    public void save(AppUserLuxury appUserLuxury, Persistent persistent) throws StoreException {
        $(appUserLuxury).save(persistent);
    }


    @Override
    @Transactional(type = TransactionType.READ_WRITE)
    public void buy(AppUserLuxury appUserLuxury, Persistent persistent, AppUserLady appUserLady, AppUserLimit appUserLimit) throws StoreException {
        $(appUserLuxury).save(persistent);
        this.appUserLadyService.save(appUserLady, Persistent.UPDATE);
        appUserLimitService.save(appUserLimit,Persistent.SAVE);
    }

    @Override
    @Transactional(type = TransactionType.READ_WRITE)
    public void sell(AppUserLuxury appUserLuxury, AppUserLady appUserLady,AppUserLimit appUserLimit) throws StoreException {
        $(appUserLuxury).delete();
        this.appUserLadyService.save(appUserLady, Persistent.UPDATE);
        appUserLimitService.save(appUserLimit,Persistent.SAVE);
    }
}
