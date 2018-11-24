package com.diary.service.app;

import com.diary.common.StoreException;
import com.diary.entity.app.AppUserClothes;
import com.diary.entity.app.AppUserLady;
import com.diary.entity.app.AppUserLimit;
import com.diary.providers.store.app.AppUserClothesStore;
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
public class AppUserClothesService extends HQuery implements AppUserClothesStore {

    @Inject
    private AppUserLadyService appUserLadyService;

    @Inject
    private AppUserLimitService appUserLimitService;


    @Transactional(type = TransactionType.READ_ONLY)
    public AppUserClothes getById(Long id, Selector... selectors) throws StoreException {
        return $(id, selectors).get(AppUserClothes.class);
    }

    @Override
    @Transactional(type = TransactionType.READ_ONLY)
    public Page<AppUserClothes> getPageList(int start, int limit, List<Selector> selectorList) throws StoreException {
        return $(selectorList).page(AppUserClothes.class, start, limit);
    }

    @Override
    @Transactional(type = TransactionType.READ_ONLY)
    public List<AppUserClothes> getByUserId(Long userId) throws StoreException {
        return $($alias("userId", "userId"),$alias("clothesId", "clothesId"), $eq("userId.id", userId),
                $order("clothesId.buyPrice",false)).list(AppUserClothes.class);
    }

    @Override
    @Transactional(type = TransactionType.READ_ONLY)
    public List<AppUserClothes> getByUserIdClothesId(Long userId, Long clothesId) throws StoreException {
        return $($alias("userId", "userId"),$alias("clothesId", "clothesId"),
                $eq("userId.id", userId),
                $eq("clothesId.id", clothesId)).list(AppUserClothes.class);
    }

    @Override
    @Transactional(type = TransactionType.READ_WRITE)
    public void delete(AppUserClothes appUserClothes) throws StoreException {
        $(appUserClothes).delete();
    }

    @Override
    @Transactional(type = TransactionType.READ_WRITE)
    public void save(AppUserClothes appUserClothes, Persistent persistent) throws StoreException {
        $(appUserClothes).save(persistent);
    }

    @Override
    @Transactional(type = TransactionType.READ_WRITE)
    public void buy(AppUserClothes appUserClothes, Persistent persistent, AppUserLady appUserLady, AppUserLimit appUserLimit) throws StoreException {
        $(appUserClothes).save(persistent);
        this.appUserLadyService.save(appUserLady, Persistent.UPDATE);
        appUserLimitService.save(appUserLimit,Persistent.SAVE);
    }

    @Override
    @Transactional(type = TransactionType.READ_WRITE)
    public void sell(AppUserClothes appUserClothes, AppUserLady appUserLady,AppUserLimit appUserLimit) throws StoreException {
        $(appUserClothes).delete();
        this.appUserLadyService.save(appUserLady, Persistent.UPDATE);
        appUserLimitService.save(appUserLimit,Persistent.SAVE);
    }

    @Override
    @Transactional(type = TransactionType.READ_WRITE)
    public void delete(List<AppUserClothes> appUserClothesList) throws StoreException {
        $(appUserClothesList).delete();
    }
}
