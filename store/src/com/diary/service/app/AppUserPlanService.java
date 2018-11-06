package com.diary.service.app;

import com.diary.common.StoreException;
import com.diary.entity.app.AppUserLady;
import com.diary.entity.app.AppUserLimit;
import com.diary.entity.app.AppUserMan;
import com.diary.entity.app.AppUserPlan;
import com.diary.providers.store.app.AppUserPlanStore;
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
public class AppUserPlanService extends HQuery implements AppUserPlanStore {

    @Inject
    private AppUserManService appUserManService;

    @Inject
    private AppUserLadyService appUserLadyService;


    @Transactional(type = TransactionType.READ_ONLY)
    public AppUserPlan getById(Long id, Selector... selectors) throws StoreException {
        return $(id, selectors).get(AppUserPlan.class);
    }

    @Override
    @Transactional(type = TransactionType.READ_ONLY)
    public Page<AppUserPlan> getPageList(int start, int limit, List<Selector> selectorList) throws StoreException {
        return $(selectorList).page(AppUserPlan.class, start, limit);
    }

    @Override
    @Transactional(type = TransactionType.READ_ONLY)
    public List<AppUserPlan> getByUserId(Long userId) throws StoreException {
        return $($alias("userId", "userId"), $eq("userId.id", userId)).list(AppUserPlan.class);
    }

    @Override
    @Transactional(type = TransactionType.READ_WRITE)
    public void delete(AppUserPlan appUserPlan) throws StoreException {
        $(appUserPlan).delete();
    }

    @Override
    @Transactional(type = TransactionType.READ_WRITE)
    public void delete(List<AppUserPlan> appUserPlanList) throws StoreException {
        $(appUserPlanList).delete();
    }

    @Override
    @Transactional(type = TransactionType.READ_WRITE)
    public void save(AppUserPlan appUserPlan, Persistent persistent, AppUserMan appUserMan) throws StoreException {
        $(appUserPlan).save(persistent);
        appUserManService.save(appUserMan,Persistent.UPDATE);

    }


    @Override
    @Transactional(type = TransactionType.READ_WRITE)
    public void save(AppUserPlan appUserPlan, Persistent persistent, AppUserLady appUserLady) throws StoreException {
        $(appUserPlan).save(persistent);
        appUserLadyService.save(appUserLady,Persistent.UPDATE);

    }
}
