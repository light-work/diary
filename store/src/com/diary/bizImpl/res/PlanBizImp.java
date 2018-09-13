package com.diary.bizImpl.res;

import com.diary.common.BizException;
import com.diary.common.StoreException;
import com.diary.entity.res.ResPlan;
import com.diary.entity.res.ResPlanEffect;
import com.diary.entity.utils.DrdsIDUtils;
import com.diary.entity.utils.DrdsTable;
import com.diary.providers.biz.res.PlanBiz;
import com.diary.providers.store.res.ResPlanEffectStore;
import com.diary.providers.store.res.ResPlanStore;
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

public class PlanBizImp extends BaseBiz implements PlanBiz {

    @Inject
    private HSFServiceFactory hsfServiceFactory;

    @Override
    public String list(Integer start, Integer limit, String keyword) throws BizException {
        JSONObject resultObj = new JSONObject();
        resultObj.put("result", -1);
        try {
            ResPlanStore resPlanStore = hsfServiceFactory.consumer(ResPlanStore.class);
            if (resPlanStore != null) {
                List<Selector> selectorList = new ArrayList<>();
                selectorList.add(SelectorUtils.$order("displayOrder", true));
                Page<ResPlan> resPlanPage = resPlanStore.getPageList(start, limit, selectorList);
                JSONArray jobArray = new JSONArray();
                if (resPlanPage != null) {
                    List<ResPlan> jobList = resPlanPage.getResultList();
                    if (jobList != null && !jobList.isEmpty()) {
                        for (ResPlan resPlan : jobList) {
                            JSONObject jobObj = JsonUtils.formIdEntity(resPlan);
                            if (jobObj != null) {
                                jobArray.add(jobObj);
                            }
                        }
                    }
                    JSONObject pageObj = buildPage2Obj(resPlanPage);
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
    public String add(String title,  Integer gender, String remarks) throws BizException {
        JSONObject resultObj = new JSONObject();
        resultObj.put("result", -1);
        try {
            ResPlanStore resPlanStore = hsfServiceFactory.consumer(ResPlanStore.class);
            if (resPlanStore != null) {
                Integer currentOrder = resPlanStore.getMaxOrder();
                if (currentOrder == null) {
                    currentOrder = 0;
                }
                ResPlan resPlan = new ResPlan();
                resPlan.setId(DrdsIDUtils.getID(DrdsTable.RES));
                resPlan.setTitle(title);
                resPlan.setGender(gender);
                resPlan.setDisplayOrder(currentOrder + 1);
                resPlan.setRemarks(remarks);
                bind(resPlan, 1l);
                resPlan.setUseYn("Y");
                resPlanStore.save(resPlan, Persistent.SAVE);
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
    public String edit(Long id, String title,  Integer gender, String remarks) throws BizException {
        JSONObject resultObj = new JSONObject();
        resultObj.put("result", -1);
        try {
            ResPlanStore resPlanStore = hsfServiceFactory.consumer(ResPlanStore.class);
            if (resPlanStore != null) {
                ResPlan resPlan = resPlanStore.getById(id);
                if (resPlan != null) {
                    resPlan.setTitle(title);
                    resPlan.setGender(gender);
                    resPlan.setRemarks(remarks);
                    bind(resPlan, 1l);
                    resPlanStore.save(resPlan, Persistent.UPDATE);
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
            ResPlanStore resPlanStore = hsfServiceFactory.consumer(ResPlanStore.class);
            if (resPlanStore != null) {
                ResPlan resPlan = resPlanStore.getById(id);
                if (resPlan != null) {
                    bind(resPlan, 1l);
                    resPlan.setUseYn("Y");
                    resPlanStore.save(resPlan, Persistent.UPDATE);
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
            ResPlanStore resPlanStore = hsfServiceFactory.consumer(ResPlanStore.class);
            if (resPlanStore != null) {
                ResPlan resPlan = resPlanStore.getById(id);
                if (resPlan != null) {
                    bind(resPlan, 1l);
                    resPlan.setUseYn("N");
                    resPlanStore.save(resPlan, Persistent.UPDATE);
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
    public String up(Long id) throws BizException {
        JSONObject resultObj = new JSONObject();
        resultObj.put("result", -1);
        try {
            ResPlanStore resPlanStore = hsfServiceFactory.consumer(ResPlanStore.class);
            if (resPlanStore != null) {
                ResPlan resPlan = resPlanStore.getById(id);
                if (resPlan != null) {
                    Integer currentOrder = resPlan.getDisplayOrder();
                    if (currentOrder > 1) {
                        currentOrder = currentOrder - 1;
                        resPlan.setDisplayOrder(currentOrder);
                    }
                    bind(resPlan, 1l);
                    resPlanStore.save(resPlan, Persistent.UPDATE);
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
    public String down(Long id) throws BizException {
        JSONObject resultObj = new JSONObject();
        resultObj.put("result", -1);
        try {
            ResPlanStore resPlanStore = hsfServiceFactory.consumer(ResPlanStore.class);
            if (resPlanStore != null) {
                ResPlan resPlan = resPlanStore.getById(id);
                if (resPlan != null) {
                    Integer maxOrder = resPlanStore.getMaxOrder();
                    Integer currentOrder = resPlan.getDisplayOrder();
                    if (currentOrder < maxOrder) {
                        currentOrder = currentOrder + 1;
                        resPlan.setDisplayOrder(currentOrder);
                    }
                    bind(resPlan, 1l);
                    resPlanStore.save(resPlan, Persistent.UPDATE);
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
            ResPlanEffectStore resPlanEffectStore = hsfServiceFactory.consumer(ResPlanEffectStore.class);
            ResPlanStore resPlanStore = hsfServiceFactory.consumer(ResPlanStore.class);
            if (resPlanEffectStore != null && resPlanStore != null) {
                ResPlan resPlan = resPlanStore.getById(jobId);
                if (resPlan != null) {
                    ResPlanEffect resPlanEffect = new ResPlanEffect();
                    resPlanEffect.setId(DrdsIDUtils.getID(DrdsTable.RES));
                    resPlanEffect.setPlanId(resPlan);
                    resPlanEffect.setOperation(operation);
                    resPlanEffect.setAttrKey(attrKey);
                    resPlanEffect.setValue(value);
                    bind(resPlanEffect, 1l);
                    resPlanEffect.setUseYn("Y");
                    resPlanEffectStore.save(resPlanEffect, Persistent.SAVE);
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
            ResPlanEffectStore resPlanEffectStore = hsfServiceFactory.consumer(ResPlanEffectStore.class);
            if (resPlanEffectStore != null) {
                ResPlanEffect resPlanEffect = resPlanEffectStore.getById(id);
                if (resPlanEffect != null) {
                    resPlanEffect.setOperation(operation);
                    resPlanEffect.setAttrKey(attrKey);
                    resPlanEffect.setValue(value);
                    bind(resPlanEffect, 1l);
                    resPlanEffectStore.save(resPlanEffect, Persistent.UPDATE);
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
            ResPlanEffectStore resPlanEffectStore = hsfServiceFactory.consumer(ResPlanEffectStore.class);
            if (resPlanEffectStore != null) {
                ResPlanEffect resPlanEffect = resPlanEffectStore.getById(id);
                if (resPlanEffect != null) {
                    resPlanEffectStore.delete(resPlanEffect);
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
            ResPlanEffectStore resPlanEffectStore = hsfServiceFactory.consumer(ResPlanEffectStore.class);
            if (resPlanEffectStore != null) {
                List<Selector> selectorList = new ArrayList<>();
                selectorList.add(SelectorUtils.$eq("jobId.id", jobId));
                List<ResPlanEffect> jobEffectList = resPlanEffectStore.getList(selectorList);
                JSONArray jobEffectArray = new JSONArray();
                if (jobEffectList != null && !jobEffectList.isEmpty()) {
                    for (ResPlanEffect resPlanEffect : jobEffectList) {
                        JSONObject resPlanEffectObj = JsonUtils.formIdEntity(resPlanEffect);
                        if (resPlanEffectObj != null) {
                            jobEffectArray.add(resPlanEffectObj);
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
