package com.diary.bizImpl.res;

import com.diary.common.BizException;
import com.diary.common.StoreException;
import com.diary.entity.res.*;
import com.diary.entity.utils.DrdsIDUtils;
import com.diary.entity.utils.DrdsTable;
import com.diary.providers.biz.res.JobBiz;
import com.diary.providers.store.res.*;
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

public class JobBizImp extends BaseBiz implements JobBiz {

    @Inject
    private HSFServiceFactory hsfServiceFactory;

    @Override
    public String list(Integer start, Integer limit, String keyword) throws BizException {
        JSONObject resultObj = new JSONObject();
        resultObj.put("result", -1);
        try {
            ResJobStore resJobStore = hsfServiceFactory.consumer(ResJobStore.class);
            if (resJobStore != null) {
                List<Selector> selectorList = new ArrayList<>();
                selectorList.add(SelectorUtils.$order("price", true));
                Page<ResJob> resJobPage = resJobStore.getPageList(start, limit, selectorList);
                JSONArray jobArray = new JSONArray();
                if (resJobPage != null) {
                    List<ResJob> jobList = resJobPage.getResultList();
                    if (jobList != null && !jobList.isEmpty()) {
                        for (ResJob resJob : jobList) {
                            JSONObject jobObj = JsonUtils.formIdEntity(resJob);
                            if (jobObj != null) {
                                jobArray.add(jobObj);
                            }
                        }
                    }
                    JSONObject pageObj = buildPage2Obj(resJobPage);
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
    public String add(String title, Integer price, Integer gender, String remarks) throws BizException {
        JSONObject resultObj = new JSONObject();
        resultObj.put("result", -1);
        try {
            ResJobStore resJobStore = hsfServiceFactory.consumer(ResJobStore.class);
            if (resJobStore != null) {
                ResJob resJob = new ResJob();
                resJob.setId(DrdsIDUtils.getID(DrdsTable.RES));
                resJob.setTitle(title);
                resJob.setPrice(price);
                resJob.setGender(gender);
                resJob.setRemarks(remarks);
                bind(resJob, 1l);
                resJob.setUseYn("Y");
                resJobStore.save(resJob, Persistent.SAVE);
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
    public String edit(Long id, String title, Integer price, Integer gender, String remarks) throws BizException {
        JSONObject resultObj = new JSONObject();
        resultObj.put("result", -1);
        try {
            ResJobStore resJobStore = hsfServiceFactory.consumer(ResJobStore.class);
            if (resJobStore != null) {
                ResJob resJob = resJobStore.getById(id);
                if (resJob != null) {
                    resJob.setTitle(title);
                    resJob.setPrice(price);
                    resJob.setGender(gender);
                    resJob.setRemarks(remarks);
                    bind(resJob, 1l);
                    resJobStore.save(resJob, Persistent.UPDATE);
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
            ResJobStore resJobStore = hsfServiceFactory.consumer(ResJobStore.class);
            if (resJobStore != null) {
                ResJob resJob = resJobStore.getById(id);
                if (resJob != null) {
                    bind(resJob, 1l);
                    resJob.setUseYn("Y");
                    resJobStore.save(resJob, Persistent.UPDATE);
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
            ResJobStore resJobStore = hsfServiceFactory.consumer(ResJobStore.class);
            if (resJobStore != null) {
                ResJob resJob = resJobStore.getById(id);
                if (resJob != null) {
                    bind(resJob, 1l);
                    resJob.setUseYn("N");
                    resJobStore.save(resJob, Persistent.UPDATE);
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
            ResJobEffectStore resJobEffectStore = hsfServiceFactory.consumer(ResJobEffectStore.class);
            ResJobStore resJobStore = hsfServiceFactory.consumer(ResJobStore.class);
            if (resJobEffectStore != null && resJobStore != null) {
                ResJob resJob = resJobStore.getById(jobId);
                if (resJob != null) {
                    ResJobEffect resJobEffect = new ResJobEffect();
                    resJobEffect.setId(DrdsIDUtils.getID(DrdsTable.RES));
                    resJobEffect.setJobId(resJob);
                    resJobEffect.setOperation(operation);
                    resJobEffect.setAttrKey(attrKey);
                    resJobEffect.setValue(value);
                    bind(resJobEffect, 1l);
                    resJobEffect.setUseYn("Y");
                    resJobEffectStore.save(resJobEffect, Persistent.SAVE);
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
            ResJobEffectStore resJobEffectStore = hsfServiceFactory.consumer(ResJobEffectStore.class);
            if (resJobEffectStore != null) {
                ResJobEffect resJobEffect = resJobEffectStore.getById(id);
                if (resJobEffect != null) {
                    resJobEffect.setOperation(operation);
                    resJobEffect.setAttrKey(attrKey);
                    resJobEffect.setValue(value);
                    bind(resJobEffect, 1l);
                    resJobEffectStore.save(resJobEffect, Persistent.UPDATE);
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
            ResJobEffectStore resJobEffectStore = hsfServiceFactory.consumer(ResJobEffectStore.class);
            if (resJobEffectStore != null) {
                ResJobEffect resJobEffect = resJobEffectStore.getById(id);
                if (resJobEffect != null) {
                    resJobEffectStore.delete(resJobEffect);
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
            ResJobEffectStore resJobEffectStore = hsfServiceFactory.consumer(ResJobEffectStore.class);
            if (resJobEffectStore != null) {
                List<Selector> selectorList = new ArrayList<>();
                selectorList.add(SelectorUtils.$alias("jobId", "jobId"));
                selectorList.add(SelectorUtils.$eq("jobId.id", jobId));
                List<ResJobEffect> jobEffectList = resJobEffectStore.getList(selectorList);
                JSONArray jobEffectArray = new JSONArray();
                if (jobEffectList != null && !jobEffectList.isEmpty()) {
                    for (ResJobEffect resJobEffect : jobEffectList) {
                        JSONObject resJobEffectObj = JsonUtils.formIdEntity(resJobEffect);
                        if (resJobEffectObj != null) {
                            jobEffectArray.add(resJobEffectObj);
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

    @Override
    public String addRequire(Long jobId, String attrKey, Integer value) throws BizException {
        JSONObject resultObj = new JSONObject();
        resultObj.put("result", -1);
        try {
            ResJobRequireStore resJobRequireStore = hsfServiceFactory.consumer(ResJobRequireStore.class);
            ResJobStore resJobStore = hsfServiceFactory.consumer(ResJobStore.class);
            if (resJobRequireStore != null && resJobStore != null) {
                ResJob resJob = resJobStore.getById(jobId);
                if (resJob != null) {
                    ResJobRequire resJobRequire = new ResJobRequire();
                    resJobRequire.setId(DrdsIDUtils.getID(DrdsTable.RES));
                    resJobRequire.setJobId(resJob);
                    resJobRequire.setAttrKey(attrKey);
                    resJobRequire.setValue(value);
                    bind(resJobRequire, 1l);
                    resJobRequire.setUseYn("Y");
                    resJobRequireStore.save(resJobRequire, Persistent.SAVE);
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
    public String editRequire(Long id, String attrKey, Integer value) throws BizException {
        JSONObject resultObj = new JSONObject();
        resultObj.put("result", -1);
        try {
            ResJobRequireStore resJobRequireStore = hsfServiceFactory.consumer(ResJobRequireStore.class);
            if (resJobRequireStore != null) {
                ResJobRequire resJobRequire = resJobRequireStore.getById(id);
                if (resJobRequire != null) {
                    resJobRequire.setAttrKey(attrKey);
                    resJobRequire.setValue(value);
                    bind(resJobRequire, 1l);
                    resJobRequireStore.save(resJobRequire, Persistent.UPDATE);
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
    public String deleteRequire(Long id) throws BizException {
        JSONObject resultObj = new JSONObject();
        resultObj.put("result", -1);
        try {
            ResJobRequireStore resJobRequireStore = hsfServiceFactory.consumer(ResJobRequireStore.class);
            if (resJobRequireStore != null) {
                ResJobRequire resJobRequire = resJobRequireStore.getById(id);
                if (resJobRequire != null) {
                    resJobRequireStore.delete(resJobRequire);
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
    public String requireList(Long jobId) throws BizException {
        JSONObject resultObj = new JSONObject();
        resultObj.put("result", -1);
        try {
            ResJobRequireStore resJobRequireStore = hsfServiceFactory.consumer(ResJobRequireStore.class);
            if (resJobRequireStore != null) {
                List<Selector> selectorList = new ArrayList<>();
                selectorList.add(SelectorUtils.$alias("jobId", "jobId"));
                selectorList.add(SelectorUtils.$eq("jobId.id", jobId));
                List<ResJobRequire> jobRequireList = resJobRequireStore.getList(selectorList);
                JSONArray jobRequireArray = new JSONArray();
                if (jobRequireList != null && !jobRequireList.isEmpty()) {
                    for (ResJobRequire resJobRequire : jobRequireList) {
                        JSONObject resJobRequireObj = JsonUtils.formIdEntity(resJobRequire);
                        if (resJobRequireObj != null) {
                            jobRequireArray.add(resJobRequireObj);
                        }
                    }
                }
                resultObj.put("list", jobRequireArray);
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
    public String addEvent(Long jobId, String content) throws BizException {
        JSONObject resultObj = new JSONObject();
        resultObj.put("result", -1);
        try {
            ResJobEventStore resJobEventStore = hsfServiceFactory.consumer(ResJobEventStore.class);
            ResJobStore resJobStore = hsfServiceFactory.consumer(ResJobStore.class);
            if (resJobEventStore != null && resJobStore != null) {
                ResJob resJob = resJobStore.getById(jobId);
                if (resJob != null) {
                    ResEvent resEvent = new ResEvent();
                    resEvent.setId(DrdsIDUtils.getID(DrdsTable.RES));
                    resEvent.setSource("JOB");
                    resEvent.setContent(content);
                    bind(resEvent, 1l);
                    resEvent.setUseYn("Y");


                    ResJobEvent resJobEvent = new ResJobEvent();
                    resJobEvent.setId(DrdsIDUtils.getID(DrdsTable.RES));
                    resJobEvent.setJobId(resJob);
                    resJobEvent.setEventId(resEvent);
                    bind(resJobEvent, 1l);
                    resJobEvent.setUseYn("Y");

                    resJobEventStore.save(resJobEvent, Persistent.SAVE, resEvent, Persistent.SAVE);
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
    public String eventList(Long jobId) throws BizException {
        JSONObject resultObj = new JSONObject();
        resultObj.put("result", -1);
        try {
            ResJobEventStore resJobEventStore = hsfServiceFactory.consumer(ResJobEventStore.class);
            ResEventResultStore resEventResultStore = hsfServiceFactory.consumer(ResEventResultStore.class);
            ResEventResultEffectStore resEventResultEffectStore = hsfServiceFactory.consumer(ResEventResultEffectStore.class);
            if (resJobEventStore != null && resEventResultStore != null && resEventResultEffectStore != null) {
                List<Selector> selectorList = new ArrayList<>();
                selectorList.add(SelectorUtils.$alias("jobId", "jobId"));
                selectorList.add(SelectorUtils.$alias("eventId", "eventId"));
                selectorList.add(SelectorUtils.$eq("jobId.id", jobId));
                List<ResJobEvent> resJobEventList = resJobEventStore.getList(selectorList);
                JSONArray jobArray = new JSONArray();
                if (resJobEventList != null && !resJobEventList.isEmpty()) {
                    for (ResJobEvent resJobEvent : resJobEventList) {
                        ResEvent resEvent = resJobEvent.getEventId();
                        if (resEvent != null) {
                            selectorList.clear();
                            selectorList.add(SelectorUtils.$eq("eventId.id", resEvent.getId()));
                            selectorList.add(SelectorUtils.$order("displayOrder",true));
                            JSONArray eventResultArray = new JSONArray();
                            List<ResEventResult> eventResultList = resEventResultStore.getList(selectorList);
                            if (eventResultList != null && !eventResultList.isEmpty()) {
                                for (ResEventResult resEventResult : eventResultList) {
                                    selectorList.clear();
                                    selectorList.add(SelectorUtils.$eq("resultId.id", resEventResult.getId()));
                                    JSONArray eventResultEffectArray = new JSONArray();
                                    List<ResEventResultEffect> eventResultEffectList = resEventResultEffectStore.getList(selectorList);
                                    if (eventResultEffectList != null && !eventResultEffectList.isEmpty()) {
                                        for (ResEventResultEffect resEventResultEffect : eventResultEffectList) {
                                            JSONObject resEventResultEffectObj = JsonUtils.formIdEntity(resEventResultEffect);
                                            if (resEventResultEffectObj != null) {
                                                eventResultEffectArray.add(resEventResultEffectObj);
                                            }
                                        }
                                    }
                                    JSONObject resEventResultObj = JsonUtils.formIdEntity(resEventResult);
                                    if (resEventResultObj != null) {
                                        resEventResultObj.put("effectList", eventResultEffectArray);
                                        eventResultArray.add(resEventResultObj);
                                    }
                                }
                            }
                            JSONObject resJobEventObj = JsonUtils.formIdEntity(resJobEvent);
                            if (resJobEventObj != null) {
                                resJobEventObj.put("resultList", eventResultArray);
                                jobArray.add(resJobEventObj);
                            }
                        }
                    }
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
}
