package com.diary.service.res;

import com.diary.common.StoreException;
import com.diary.entity.res.ResEvent;
import com.diary.entity.res.ResEventNo;
import com.diary.entity.res.ResEventYes;
import com.diary.entity.res.ResHouseEvent;
import com.diary.providers.store.res.ResHouseEventStore;
import com.google.inject.Inject;
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
public class ResHouseEventService extends HQuery implements ResHouseEventStore {

    @Inject
    private ResEventService resEventService;

    @Override
    @Transactional(type = TransactionType.READ_ONLY)
    public ResHouseEvent getById(Long id, Selector... selectors) throws StoreException {
        return $(id, selectors).get(ResHouseEvent.class);
    }


    @Override
    @Transactional(type = TransactionType.READ_ONLY)
    public List<ResHouseEvent> getListByHouseId(Long houseId) throws StoreException {
        return $($alias("houseId", "houseId"), $eq("houseId.id", houseId)).list(ResHouseEvent.class);
    }

    @Override
    @Transactional(type = TransactionType.READ_WRITE)
    public void save(ResHouseEvent resHouseEvent, Persistent persistent) throws StoreException {
        $(resHouseEvent).save(persistent);
    }

    @Override
    @Transactional(type = TransactionType.READ_WRITE)
    public void save(ResHouseEvent resHouseEvent, Persistent persistent, ResEvent resEvent, Persistent resEventPersistent, ResEventYes resEventYes, Persistent resEventYesPersistent, ResEventNo resEventNo, Persistent resEventNoPersistent) throws StoreException {
        $(resHouseEvent).save(persistent);
        resEventService.save(resEvent, persistent, resEventYes, resEventYesPersistent, resEventNo, resEventNoPersistent);
    }

    @Override
    @Transactional(type = TransactionType.READ_WRITE)
    public void delete(ResHouseEvent resHouseEvent) throws StoreException {
        $(resHouseEvent).delete();
    }
}
