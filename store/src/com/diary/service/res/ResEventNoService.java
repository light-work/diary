package com.diary.service.res;

import com.diary.common.StoreException;
import com.diary.entity.res.ResEventNo;
import com.diary.providers.store.res.ResEventNoStore;
import com.google.inject.Singleton;
import org.guiceside.persistence.TransactionType;
import org.guiceside.persistence.Transactional;
import org.guiceside.persistence.hibernate.dao.enums.Persistent;
import org.guiceside.persistence.hibernate.dao.hquery.HQuery;
import org.guiceside.persistence.hibernate.dao.hquery.Selector;


/**
 * Created by Lara Croft on 2016/12/21.
 */
@Singleton
public class ResEventNoService extends HQuery implements ResEventNoStore {

    @Override
    @Transactional(type = TransactionType.READ_ONLY)
    public ResEventNo getById(Long id, Selector... selectors) throws StoreException {
        return $(id, selectors).get(ResEventNo.class);
    }

    @Override
    @Transactional(type = TransactionType.READ_ONLY)
    public ResEventNo getByEventId(Long eventId) throws StoreException {
        return $($alias("eventId", "eventId"), $eq("eventId.id", eventId)).get(ResEventNo.class);
    }

    
    @Override
    @Transactional(type = TransactionType.READ_WRITE)
    public void save(ResEventNo resEventNo, Persistent persistent) throws StoreException {
        $(resEventNo).save(persistent);
    }

    @Override
    @Transactional(type = TransactionType.READ_WRITE)
    public void delete(ResEventNo resEventNo) throws StoreException {
        $(resEventNo).delete();
    }
}
