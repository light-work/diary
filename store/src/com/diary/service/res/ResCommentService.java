package com.diary.service.res;

import com.diary.common.StoreException;
import com.diary.entity.res.ResComment;
import com.diary.providers.store.res.ResCommentStore;
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
public class ResCommentService extends HQuery implements ResCommentStore {

    @Override
    @Transactional(type = TransactionType.READ_ONLY)

    public ResComment getById(Long id, Selector... selectors) throws StoreException {
        return $(id, selectors).get(ResComment.class);
    }

    @Override
    @Transactional(type = TransactionType.READ_ONLY)
    public Page<ResComment> getPageList(int start, int limit, List<Selector> selectorList) throws StoreException {
        return $(selectorList).page(ResComment.class, start, limit);
    }

    @Override
    @Transactional(type = TransactionType.READ_ONLY)
    public List<ResComment> getList(Integer gender) throws StoreException {
        return $($eq("gender",gender)).list(ResComment.class);
    }



    @Override
    @Transactional(type = TransactionType.READ_WRITE)
    public void save(ResComment resComment, Persistent persistent) throws StoreException {
        $(resComment).save(persistent);
    }

    @Override
    @Transactional(type = TransactionType.READ_WRITE)
    public void delete(ResComment resComment) throws StoreException {
        $(resComment).delete();
    }
}
