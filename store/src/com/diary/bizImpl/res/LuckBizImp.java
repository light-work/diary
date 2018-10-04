package com.diary.bizImpl.res;

import com.diary.common.BizException;
import com.diary.common.StoreException;
import com.diary.entity.res.ResLuck;
import com.diary.entity.utils.DrdsIDUtils;
import com.diary.entity.utils.DrdsTable;
import com.diary.providers.biz.res.LuckBiz;
import com.diary.providers.store.res.ResLuckStore;
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

public class LuckBizImp extends BaseBiz implements LuckBiz {

    @Inject
    private HSFServiceFactory hsfServiceFactory;

    @Override
    public String list(Integer start, Integer limit, String keyword) throws BizException {
        JSONObject resultObj = new JSONObject();
        resultObj.put("result", -1);
        try {
            ResLuckStore resLuckStore = hsfServiceFactory.consumer(ResLuckStore.class);
            if (resLuckStore != null) {
                List<Selector> selectorList = new ArrayList<>();
                selectorList.add(SelectorUtils.$order("investPrice", true));
                Page<ResLuck> resLuckPage = resLuckStore.getPageList(start, limit, selectorList);
                JSONArray jobArray = new JSONArray();
                if (resLuckPage != null) {
                    List<ResLuck> jobList = resLuckPage.getResultList();
                    if (jobList != null && !jobList.isEmpty()) {
                        for (ResLuck resLuck : jobList) {
                            JSONObject jobObj = JsonUtils.formIdEntity(resLuck);
                            if (jobObj != null) {
                                jobArray.add(jobObj);
                            }
                        }
                    }
                    JSONObject pageObj = buildPage2Obj(resLuckPage);
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
    public String add(String title, Integer investPrice, Integer gainPrice,Double probability, String remarks) throws BizException {
        JSONObject resultObj = new JSONObject();
        resultObj.put("result", -1);
        try {
            ResLuckStore resLuckStore = hsfServiceFactory.consumer(ResLuckStore.class);
            if (resLuckStore != null) {
                ResLuck resLuck = new ResLuck();
                resLuck.setId(DrdsIDUtils.getID(DrdsTable.RES));
                resLuck.setTitle(title);
                resLuck.setInvestPrice(investPrice);
                resLuck.setGainPrice(gainPrice);
                resLuck.setProbability(probability);
                resLuck.setRemarks(remarks);
                bind(resLuck, 1l);
                resLuck.setUseYn("Y");
                resLuckStore.save(resLuck, Persistent.SAVE);
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
    public String edit(Long id, String title, Integer investPrice, Integer gainPrice, Double probability,String remarks) throws BizException {
        JSONObject resultObj = new JSONObject();
        resultObj.put("result", -1);
        try {
            ResLuckStore resLuckStore = hsfServiceFactory.consumer(ResLuckStore.class);
            if (resLuckStore != null) {
                ResLuck resLuck = resLuckStore.getById(id);
                if (resLuck != null) {
                    resLuck.setTitle(title);
                    resLuck.setInvestPrice(investPrice);
                    resLuck.setGainPrice(gainPrice);
                    resLuck.setProbability(probability);
                    resLuck.setRemarks(remarks);
                    bind(resLuck, 1l);
                    resLuckStore.save(resLuck, Persistent.UPDATE);
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
            ResLuckStore resLuckStore = hsfServiceFactory.consumer(ResLuckStore.class);
            if (resLuckStore != null) {
                ResLuck resLuck = resLuckStore.getById(id);
                if (resLuck != null) {
                    bind(resLuck, 1l);
                    resLuck.setUseYn("Y");
                    resLuckStore.save(resLuck, Persistent.UPDATE);
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
            ResLuckStore resLuckStore = hsfServiceFactory.consumer(ResLuckStore.class);
            if (resLuckStore != null) {
                ResLuck resLuck = resLuckStore.getById(id);
                if (resLuck != null) {
                    bind(resLuck, 1l);
                    resLuck.setUseYn("N");
                    resLuckStore.save(resLuck, Persistent.UPDATE);
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
