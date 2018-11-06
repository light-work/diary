package com.diary.bizImpl.res;

import com.diary.common.BizException;
import com.diary.common.StoreException;
import com.diary.entity.res.ResFund;
import com.diary.entity.utils.DrdsIDUtils;
import com.diary.entity.utils.DrdsTable;
import com.diary.providers.biz.res.FundBiz;
import com.diary.providers.store.res.ResFundStore;
import com.google.inject.Inject;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import org.guiceside.commons.JsonUtils;
import org.guiceside.commons.Page;
import org.guiceside.persistence.entity.search.SelectorUtils;
import org.guiceside.persistence.hibernate.dao.enums.Persistent;
import org.guiceside.persistence.hibernate.dao.hquery.Selector;
import org.guiceside.support.hsf.BaseBiz;
import org.guiceside.support.hsf.HSFServiceFactory;

import java.util.ArrayList;
import java.util.List;


/**
 * @author zhenjiaWang
 * @version 1.0 2012-05
 * @since JDK1.5
 */

public class FundBizImp extends BaseBiz implements FundBiz {

    @Inject
    private HSFServiceFactory hsfServiceFactory;

    @Override
    public String list(Integer start, Integer limit, String keyword) throws BizException {
        JSONObject resultObj = new JSONObject();
        resultObj.put("result", -1);
        try {
            ResFundStore resFundStore = hsfServiceFactory.consumer(ResFundStore.class);
            if (resFundStore != null) {
                List<Selector> selectorList = new ArrayList<>();
                selectorList.add(SelectorUtils.$order("probability", true));
                Page<ResFund> resFundPage = resFundStore.getPageList(start, limit, selectorList);
                JSONArray jobArray = new JSONArray();
                if (resFundPage != null) {
                    List<ResFund> fundList = resFundPage.getResultList();
                    if (fundList != null && !fundList.isEmpty()) {

                        for (ResFund resFund : fundList) {
                            JSONObject jobObj = JsonUtils.formIdEntity(resFund);
                            if (jobObj != null) {
                                jobArray.add(jobObj);
                            }
                        }
                    }
                    JSONObject pageObj = buildPage2Obj(resFundPage);
                    resultObj.put("pageObj", pageObj);
                }
                resultObj.put("list", jobArray);
                resultObj.put("result", 0);
            }
        } catch (Exception ex) {
            if (ex instanceof StoreException) {
                throw new StoreException(ex);
            } else {
                throw new BizException(ex);
            }
        }
        return resultObj.toString();
    }

    @Override
    public String add(String title, Double minNum, Double maxNum,Double probability, String remarks) throws BizException {
        JSONObject resultObj = new JSONObject();
        resultObj.put("result", -1);
        try {
            ResFundStore resFundStore = hsfServiceFactory.consumer(ResFundStore.class);
            if (resFundStore != null) {
                ResFund resFund = new ResFund();
                resFund.setId(DrdsIDUtils.getID(DrdsTable.RES));
                resFund.setTitle(title);
                resFund.setMinNum(minNum);
                resFund.setMaxNum(maxNum);
                resFund.setProbability(probability);
                resFund.setRemarks(remarks);
                bind(resFund, 1l);
                resFund.setUseYn("Y");
                resFundStore.save(resFund, Persistent.SAVE);
                resultObj.put("result", 0);
            }
        } catch (Exception ex) {
            if (ex instanceof StoreException) {
                throw new StoreException(ex);
            } else {
                throw new BizException(ex);
            }
        }
        return resultObj.toString();
    }

    @Override
    public String edit(Long id, String title, Double minNum, Double maxNum, Double probability,String remarks) throws BizException {
        JSONObject resultObj = new JSONObject();
        resultObj.put("result", -1);
        try {
            ResFundStore resFundStore = hsfServiceFactory.consumer(ResFundStore.class);
            if (resFundStore != null) {
                ResFund resFund = resFundStore.getById(id);
                if (resFund != null) {
                    resFund.setTitle(title);
                    resFund.setMinNum(minNum);
                    resFund.setMaxNum(maxNum);
                    resFund.setProbability(probability);
                    resFund.setRemarks(remarks);
                    bind(resFund, 1l);
                    resFundStore.save(resFund, Persistent.UPDATE);
                    resultObj.put("result", 0);
                }
            }
        } catch (Exception ex) {
            if (ex instanceof StoreException) {
                throw new StoreException(ex);
            } else {
                throw new BizException(ex);
            }
        }
        return resultObj.toString();
    }

    @Override
    public String enable(Long id) throws BizException {
        JSONObject resultObj = new JSONObject();
        resultObj.put("result", -1);
        try {
            ResFundStore resFundStore = hsfServiceFactory.consumer(ResFundStore.class);
            if (resFundStore != null) {
                ResFund resFund = resFundStore.getById(id);
                if (resFund != null) {
                    bind(resFund, 1l);
                    resFund.setUseYn("Y");
                    resFundStore.save(resFund, Persistent.UPDATE);
                    resultObj.put("result", 0);
                }
            }
        } catch (Exception ex) {
            if (ex instanceof StoreException) {
                throw new StoreException(ex);
            } else {
                throw new BizException(ex);
            }
        }
        return resultObj.toString();
    }

    @Override
    public String disable(Long id) throws BizException {
        JSONObject resultObj = new JSONObject();
        resultObj.put("result", -1);
        try {
            ResFundStore resFundStore = hsfServiceFactory.consumer(ResFundStore.class);
            if (resFundStore != null) {
                ResFund resFund = resFundStore.getById(id);
                if (resFund != null) {
                    bind(resFund, 1l);
                    resFund.setUseYn("N");
                    resFundStore.save(resFund, Persistent.UPDATE);
                    resultObj.put("result", 0);
                }
            }
        } catch (Exception ex) {
            if (ex instanceof StoreException) {
                throw new StoreException(ex);
            } else {
                throw new BizException(ex);
            }
        }
        return resultObj.toString();
    }
}
