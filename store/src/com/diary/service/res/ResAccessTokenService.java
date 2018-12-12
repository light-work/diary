package com.diary.service.res;

import com.diary.common.StoreException;
import com.diary.entity.res.ResAccessToken;
import com.diary.providers.store.res.ResAccessTokenStore;
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
public class ResAccessTokenService extends HQuery implements ResAccessTokenStore {

    @Override
    @Transactional(type = TransactionType.READ_ONLY)

    public ResAccessToken getById(Long id, Selector... selectors) throws StoreException {
        return $(id, selectors).get(ResAccessToken.class);
    }

    @Override
    @Transactional(type = TransactionType.READ_ONLY)
    public Page<ResAccessToken> getPageList(int start, int limit, List<Selector> selectorList) throws StoreException {
        return $(selectorList).page(ResAccessToken.class, start, limit);
    }

    @Override
    @Transactional(type = TransactionType.READ_ONLY)
    public List<ResAccessToken> getList(List<Selector> selectorList) throws StoreException {
        return $(selectorList).list(ResAccessToken.class);
    }

    @Override
    @Transactional(type = TransactionType.READ_WRITE)
    public void save(ResAccessToken resAccessToken, Persistent persistent) throws StoreException {
        $(resAccessToken).save(persistent);
    }

    @Override
    @Transactional(type = TransactionType.READ_WRITE)
    public void delete(ResAccessToken resAccessToken) throws StoreException {
        $(resAccessToken).delete();
    }
}
