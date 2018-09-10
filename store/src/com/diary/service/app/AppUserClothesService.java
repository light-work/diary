package com.diary.service.app;

import com.diary.common.StoreException;
import com.diary.entity.app.AppUserClothes;
import com.diary.providers.store.app.AppUserClothesStore;
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
        return $($alias("userId", "userId"), $eq("userId.id", userId)).list(AppUserClothes.class);
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
}
