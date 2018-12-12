package com.diary.service.res;

import com.diary.common.StoreException;
import com.diary.entity.res.ResWbAccessToken;
import com.diary.providers.store.res.ResWbAccessTokenStore;
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
public class ResWbAccessTokenService extends HQuery implements ResWbAccessTokenStore {

    @Override
    @Transactional(type = TransactionType.READ_ONLY)

    public ResWbAccessToken getById(Long id, Selector... selectors) throws StoreException {
        return $(id, selectors).get(ResWbAccessToken.class);
    }

    @Override
    @Transactional(type = TransactionType.READ_ONLY)
    public Page<ResWbAccessToken> getPageList(int start, int limit, List<Selector> selectorList) throws StoreException {
        return $(selectorList).page(ResWbAccessToken.class, start, limit);
    }

    @Override
    @Transactional(type = TransactionType.READ_ONLY)
    public List<ResWbAccessToken> getList(List<Selector> selectorList) throws StoreException {
        return $(selectorList).list(ResWbAccessToken.class);
    }

    @Override
    @Transactional(type = TransactionType.READ_WRITE)
    public void save(ResWbAccessToken resWbAccessToken, Persistent persistent) throws StoreException {
        $(resWbAccessToken).save(persistent);
    }

    @Override
    @Transactional(type = TransactionType.READ_WRITE)
    public void delete(ResWbAccessToken resWbAccessToken) throws StoreException {
        $(resWbAccessToken).delete();
    }
}
