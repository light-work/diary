package com.diary.service.app;

import com.diary.common.StoreException;
import com.diary.entity.app.AppUserCar;
import com.diary.entity.app.AppUserLimit;
import com.diary.entity.app.AppUserMan;
import com.diary.providers.store.app.AppUserCarStore;
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
public class AppUserCarService extends HQuery implements AppUserCarStore {

    @Inject
    private AppUserManService appUserManService;

    @Inject
    private AppUserLimitService appUserLimitService;


    @Transactional(type = TransactionType.READ_ONLY)
    public AppUserCar getById(Long id, Selector... selectors) throws StoreException {
        return $(id, selectors).get(AppUserCar.class);
    }

    @Override
    @Transactional(type = TransactionType.READ_ONLY)
    public Page<AppUserCar> getPageList(int start, int limit, List<Selector> selectorList) throws StoreException {
        return $(selectorList).page(AppUserCar.class, start, limit);
    }

    @Override
    @Transactional(type = TransactionType.READ_ONLY)
    public List<AppUserCar> getByUserId(Long userId) throws StoreException {
        return $($alias("userId", "userId"),$alias("carId", "carId"), $eq("userId.id", userId)).list(AppUserCar.class);
    }

    @Override
    @Transactional(type = TransactionType.READ_ONLY)
    public List<AppUserCar> getByUserIdCarId(Long userId, Long carId) throws StoreException {
        return $($alias("userId", "userId"),$alias("carId", "carId"),
                $eq("userId.id", userId),$eq("carId.id", carId)).list(AppUserCar.class);

    }

    @Override
    @Transactional(type = TransactionType.READ_WRITE)
    public void delete(AppUserCar appUserCar) throws StoreException {
        $(appUserCar).delete();
    }

    @Override
    @Transactional(type = TransactionType.READ_WRITE)
    public void save(AppUserCar appUserCar, Persistent persistent) throws StoreException {
        $(appUserCar).save(persistent);
    }

    @Override
    @Transactional(type = TransactionType.READ_WRITE)
    public void buy(AppUserCar appUserCar, Persistent persistent, AppUserMan appUserMan, AppUserLimit appUserLimit) throws StoreException {
        $(appUserCar).save(persistent);
        this.appUserManService.save(appUserMan, Persistent.UPDATE);
        appUserLimitService.save(appUserLimit,Persistent.SAVE);
    }

    @Override
    @Transactional(type = TransactionType.READ_WRITE)
    public void sell(AppUserCar appUserCar, AppUserMan appUserMan,AppUserLimit appUserLimit) throws StoreException {
        $(appUserCar).delete();
        this.appUserManService.save(appUserMan, Persistent.UPDATE);
        appUserLimitService.save(appUserLimit,Persistent.SAVE);
    }
}
