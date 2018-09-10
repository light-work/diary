package com.diary.service.res;

import com.diary.common.StoreException;
import com.diary.entity.res.ResCoupleRequire;
import com.diary.providers.store.res.ResCoupleRequireStore;
import com.google.inject.Singleton;
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
public class ResCoupleRequireService extends HQuery implements ResCoupleRequireStore {

    @Override
    @Transactional(type = TransactionType.READ_ONLY)
    public ResCoupleRequire getById(Long id, Selector... selectors) throws StoreException {
        return $(id, selectors).get(ResCoupleRequire.class);
    }

    @Override
    @Transactional(type = TransactionType.READ_ONLY)
    public List<ResCoupleRequire> getListByCoupleId(Long coupleId) throws StoreException {
        return $($alias("coupleId", "coupleId"), $eq("coupleId.id", coupleId)).list(ResCoupleRequire.class);
    }

    @Override
    @Transactional(type = TransactionType.READ_WRITE)
    public void save(ResCoupleRequire resCoupleRequire, Persistent persistent) throws StoreException {
        $(resCoupleRequire).save(persistent);
    }

    @Override
    @Transactional(type = TransactionType.READ_WRITE)
    public void delete(ResCoupleRequire resCoupleRequire) throws StoreException {
        $(resCoupleRequire).delete();
    }
}
