package com.diary.bizImpl.res;

import com.diary.common.BizException;
import com.diary.common.StoreException;
import com.diary.entity.res.ResLuxury;
import com.diary.entity.res.ResLuxuryEffect;
import com.diary.entity.utils.DrdsIDUtils;
import com.diary.entity.utils.DrdsTable;
import com.diary.providers.biz.res.LuxuryBiz;
import com.diary.providers.store.res.ResLuxuryEffectStore;
import com.diary.providers.store.res.ResLuxuryStore;
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

public class LuxuryBizImp extends BaseBiz implements LuxuryBiz {

    @Inject
    private HSFServiceFactory hsfServiceFactory;

    @Override
    public String list(Integer start, Integer limit, String keyword) throws BizException {
        JSONObject resultObj = new JSONObject();
        resultObj.put("result", -1);
        try {
            ResLuxuryStore resLuxuryStore = hsfServiceFactory.consumer(ResLuxuryStore.class);
            if (resLuxuryStore != null) {
                List<Selector> selectorList = new ArrayList<>();
                selectorList.add(SelectorUtils.$order("buyPrice", true));
                Page<ResLuxury> resLuxuryPage = resLuxuryStore.getPageList(start, limit, selectorList);
                JSONArray jobArray = new JSONArray();
                if (resLuxuryPage != null) {
                    List<ResLuxury> jobList = resLuxuryPage.getResultList();
                    if (jobList != null && !jobList.isEmpty()) {
                        for (ResLuxury resLuxury : jobList) {
                            JSONObject jobObj = JsonUtils.formIdEntity(resLuxury);
                            if (jobObj != null) {
                                jobArray.add(jobObj);
                            }
                        }
                    }
                    JSONObject pageObj = buildPage2Obj(resLuxuryPage);
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
    public String add(String title, Integer buyPrice, Integer sellPrice, String remarks) throws BizException {
        JSONObject resultObj = new JSONObject();
        resultObj.put("result", -1);
        try {
            ResLuxuryStore resLuxuryStore = hsfServiceFactory.consumer(ResLuxuryStore.class);
            if (resLuxuryStore != null) {
                ResLuxury resLuxury = new ResLuxury();
                resLuxury.setId(DrdsIDUtils.getID(DrdsTable.RES));
                resLuxury.setTitle(title);
                resLuxury.setBuyPrice(buyPrice);
                resLuxury.setSellPrice(sellPrice);
                resLuxury.setRemarks(remarks);
                bind(resLuxury, 1l);
                resLuxury.setUseYn("Y");
                resLuxuryStore.save(resLuxury, Persistent.SAVE);
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
    public String edit(Long id, String title, Integer buyPrice, Integer sellPrice, String remarks) throws BizException {
        JSONObject resultObj = new JSONObject();
        resultObj.put("result", -1);
        try {
            ResLuxuryStore resLuxuryStore = hsfServiceFactory.consumer(ResLuxuryStore.class);
            if (resLuxuryStore != null) {
                ResLuxury resLuxury = resLuxuryStore.getById(id);
                if (resLuxury != null) {
                    resLuxury.setTitle(title);
                    resLuxury.setBuyPrice(buyPrice);
                    resLuxury.setSellPrice(sellPrice);
                    resLuxury.setRemarks(remarks);
                    bind(resLuxury, 1l);
                    resLuxuryStore.save(resLuxury, Persistent.UPDATE);
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
            ResLuxuryStore resLuxuryStore = hsfServiceFactory.consumer(ResLuxuryStore.class);
            if (resLuxuryStore != null) {
                ResLuxury resLuxury = resLuxuryStore.getById(id);
                if (resLuxury != null) {
                    bind(resLuxury, 1l);
                    resLuxury.setUseYn("Y");
                    resLuxuryStore.save(resLuxury, Persistent.UPDATE);
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
            ResLuxuryStore resLuxuryStore = hsfServiceFactory.consumer(ResLuxuryStore.class);
            if (resLuxuryStore != null) {
                ResLuxury resLuxury = resLuxuryStore.getById(id);
                if (resLuxury != null) {
                    bind(resLuxury, 1l);
                    resLuxury.setUseYn("N");
                    resLuxuryStore.save(resLuxury, Persistent.UPDATE);
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
    public String addEffect(Long jobId, String operation, String attrKey, Integer value) throws BizException {
        JSONObject resultObj = new JSONObject();
        resultObj.put("result", -1);
        try {
            ResLuxuryEffectStore resLuxuryEffectStore = hsfServiceFactory.consumer(ResLuxuryEffectStore.class);
            ResLuxuryStore resLuxuryStore = hsfServiceFactory.consumer(ResLuxuryStore.class);
            if (resLuxuryEffectStore != null && resLuxuryStore != null) {
                ResLuxury resLuxury = resLuxuryStore.getById(jobId);
                if (resLuxury != null) {
                    ResLuxuryEffect resLuxuryEffect = new ResLuxuryEffect();
                    resLuxuryEffect.setId(DrdsIDUtils.getID(DrdsTable.RES));
                    resLuxuryEffect.setLuxuryId(resLuxury);
                    resLuxuryEffect.setOperation(operation);
                    resLuxuryEffect.setAttrKey(attrKey);
                    resLuxuryEffect.setValue(value);
                    bind(resLuxuryEffect, 1l);
                    resLuxuryEffect.setUseYn("Y");
                    resLuxuryEffectStore.save(resLuxuryEffect, Persistent.SAVE);
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
    public String editEffect(Long id, String operation, String attrKey, Integer value) throws BizException {
        JSONObject resultObj = new JSONObject();
        resultObj.put("result", -1);
        try {
            ResLuxuryEffectStore resLuxuryEffectStore = hsfServiceFactory.consumer(ResLuxuryEffectStore.class);
            if (resLuxuryEffectStore != null) {
                ResLuxuryEffect resLuxuryEffect = resLuxuryEffectStore.getById(id);
                if (resLuxuryEffect != null) {
                    resLuxuryEffect.setOperation(operation);
                    resLuxuryEffect.setAttrKey(attrKey);
                    resLuxuryEffect.setValue(value);
                    bind(resLuxuryEffect, 1l);
                    resLuxuryEffectStore.save(resLuxuryEffect, Persistent.UPDATE);
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
    public String deleteEffect(Long id) throws BizException {
        JSONObject resultObj = new JSONObject();
        resultObj.put("result", -1);
        try {
            ResLuxuryEffectStore resLuxuryEffectStore = hsfServiceFactory.consumer(ResLuxuryEffectStore.class);
            if (resLuxuryEffectStore != null) {
                ResLuxuryEffect resLuxuryEffect = resLuxuryEffectStore.getById(id);
                if (resLuxuryEffect != null) {
                    resLuxuryEffectStore.delete(resLuxuryEffect);
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
    public String effectList(Long jobId) throws BizException {
        JSONObject resultObj = new JSONObject();
        resultObj.put("result", -1);
        try {
            ResLuxuryEffectStore resLuxuryEffectStore = hsfServiceFactory.consumer(ResLuxuryEffectStore.class);
            if (resLuxuryEffectStore != null) {
                List<Selector> selectorList = new ArrayList<>();
                selectorList.add(SelectorUtils.$eq("jobId.id", jobId));
                List<ResLuxuryEffect> jobEffectList = resLuxuryEffectStore.getList(selectorList);
                JSONArray jobEffectArray = new JSONArray();
                if (jobEffectList != null && !jobEffectList.isEmpty()) {
                    for (ResLuxuryEffect resLuxuryEffect : jobEffectList) {
                        JSONObject resLuxuryEffectObj = JsonUtils.formIdEntity(resLuxuryEffect);
                        if (resLuxuryEffectObj != null) {
                            jobEffectArray.add(resLuxuryEffectObj);
                        }
                    }
                }
                resultObj.put("list", jobEffectArray);
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

}
