package com.diary.service.res;

import com.diary.common.StoreException;
import com.diary.entity.res.ResEventYesResult;
import com.diary.providers.store.res.ResEventYesResultStore;
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
public class ResEventYesResultService extends HQuery implements ResEventYesResultStore {

    @Override
    @Transactional(type = TransactionType.READ_ONLY)
    public ResEventYesResult getById(Long id, Selector... selectors) throws StoreException {
        return $(id, selectors).get(ResEventYesResult.class);
    }

    @Override
    @Transactional(type = TransactionType.READ_ONLY)
    public List<ResEventYesResult> getByYesId(Long yesId) throws StoreException {
        return $($eq("yesId.id", yesId)).list(ResEventYesResult.class);
    }

    @Override
    @Transactional(type = TransactionType.READ_ONLY)
    public List<ResEventYesResult> getByList(List<Selector> selectorList) throws StoreException {
        return $(selectorList).list(ResEventYesResult.class);
    }

    @Override
    @Transactional(type = TransactionType.READ_WRITE)
    public void save(ResEventYesResult resEventYesResult, Persistent persistent) throws StoreException {
        $(resEventYesResult).save(persistent);
    }

    @Override
    @Transactional(type = TransactionType.READ_WRITE)
    public void delete(ResEventYesResult resEventYesResult) throws StoreException {
        $(resEventYesResult).delete();
    }
}
