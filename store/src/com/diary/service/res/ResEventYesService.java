package com.diary.service.res;

import com.diary.common.StoreException;
import com.diary.entity.res.ResEventYes;
import com.diary.providers.store.res.ResEventYesStore;
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
public class ResEventYesService extends HQuery implements ResEventYesStore {

    @Override
    @Transactional(type = TransactionType.READ_ONLY)
    public ResEventYes getById(Long id, Selector... selectors) throws StoreException {
        return $(id, selectors).get(ResEventYes.class);
    }

    @Override
    @Transactional(type = TransactionType.READ_ONLY)
    public ResEventYes getByEventId(Long eventId) throws StoreException {
        return $($alias("eventId", "eventId"), $eq("eventId.id", eventId)).get(ResEventYes.class);
    }


    @Override
    @Transactional(type = TransactionType.READ_WRITE)
    public void save(ResEventYes resEventYes, Persistent persistent) throws StoreException {
        $(resEventYes).save(persistent);
    }

    @Override
    @Transactional(type = TransactionType.READ_WRITE)
    public void delete(ResEventYes resEventYes) throws StoreException {
        $(resEventYes).delete();
    }
}
