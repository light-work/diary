package com.diary.service.app;

import com.diary.common.StoreException;
import com.diary.entity.app.AppUserCouple;
import com.diary.entity.app.AppUserLady;
import com.diary.entity.app.AppUserLimit;
import com.diary.entity.app.AppUserMan;
import com.diary.providers.store.app.AppUserCoupleStore;
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
public class AppUserCoupleService extends HQuery implements AppUserCoupleStore {

    @Inject
    private AppUserManService appUserManService;

    @Inject
    private AppUserLadyService appUserLadyService;

    @Inject
    private AppUserLimitService appUserLimitService;

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
    public AppUserCouple getByUserId(Long userId) throws StoreException {
        return $($alias("coupleId","coupleId"),$eq("userId.id", userId)).get(AppUserCouple.class);
    }

    @Override
    @Transactional(type = TransactionType.READ_ONLY)
    public AppUserCouple getByCoupleId(Long coupleId) throws StoreException {
        return $($alias("userId","userId"),$eq("coupleId.id", coupleId)).get(AppUserCouple.class);
    }

    @Override
    @Transactional(type = TransactionType.READ_WRITE)
    public void delete(AppUserCouple appUserCouple) throws StoreException {
        $(appUserCouple).delete();
    }

    @Override
    @Transactional(type = TransactionType.READ_WRITE)
    public void delete(AppUserCouple appUserCouple, AppUserMan appUserMan, AppUserLimit appUserLimit) throws StoreException {
        $(appUserCouple).delete();
        this.appUserManService.save(appUserMan,Persistent.UPDATE);
        if(appUserLimit!=null){
            this.appUserLimitService.save(appUserLimit, Persistent.SAVE);
        }
    }

    @Override
    @Transactional(type = TransactionType.READ_WRITE)
    public void delete(AppUserCouple appUserCouple, AppUserLady appUserLady, AppUserLimit appUserLimit) throws StoreException {
        $(appUserCouple).delete();
        this.appUserLadyService.save(appUserLady,Persistent.UPDATE);
        if(appUserLimit!=null){
            this.appUserLimitService.save(appUserLimit, Persistent.SAVE);
        }
    }

    @Override
    @Transactional(type = TransactionType.READ_WRITE)
    public void save(AppUserCouple appUserCouple, Persistent persistent, AppUserMan appUserMan, AppUserLimit appUserLimit) throws StoreException {
        $(appUserCouple).save(persistent);
        this.appUserManService.save(appUserMan,Persistent.UPDATE);
        if(appUserLimit!=null){
            this.appUserLimitService.save(appUserLimit, Persistent.SAVE);
        }
    }


    @Override
    @Transactional(type = TransactionType.READ_WRITE)
    public void save(AppUserCouple appUserCouple, Persistent persistent, AppUserLady appUserLady, AppUserLimit appUserLimit) throws StoreException {
        $(appUserCouple).save(persistent);
        this.appUserLadyService.save(appUserLady,Persistent.UPDATE);
        if(appUserLimit!=null){
            this.appUserLimitService.save(appUserLimit, Persistent.SAVE);
        }
    }
}
