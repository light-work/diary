package com.diary.service.app;

import com.diary.common.StoreException;
import com.diary.entity.app.AppUser;
import com.diary.providers.store.app.AppUserStore;
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
public class AppUserService extends HQuery implements AppUserStore {

    @Transactional(type = TransactionType.READ_ONLY)
    public AppUser getById(Long id, Selector... selectors) throws StoreException {
        return $(id, selectors).get(AppUser.class);
    }

    @Override
    @Transactional(type = TransactionType.READ_ONLY)
    public Page<AppUser> getPageList(int start, int limit, List<Selector> selectorList) throws StoreException {
        return $(selectorList).page(AppUser.class, start, limit);
    }

    @Override
    @Transactional(type = TransactionType.READ_ONLY)
    public AppUser getByOpenId(String openId) throws StoreException {
        return $($eq("openId", openId)).get(AppUser.class);
    }

    @Override
    @Transactional(type = TransactionType.READ_WRITE)
    public void save(AppUser appUser, Persistent persistent) throws StoreException {
        $(appUser).save(persistent);
    }
}
