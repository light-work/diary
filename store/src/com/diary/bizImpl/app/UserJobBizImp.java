package com.diary.bizImpl.app;

import com.diary.common.BizException;
import com.diary.common.StoreException;
import com.diary.entity.app.*;
import com.diary.entity.res.ResJob;
import com.diary.entity.res.ResJobRequire;
import com.diary.entity.utils.DrdsIDUtils;
import com.diary.entity.utils.DrdsTable;
import com.diary.entity.utils.GameUtils;
import com.diary.providers.biz.app.UserJobBiz;
import com.diary.providers.store.app.*;
import com.diary.providers.store.res.ResJobRequireStore;
import com.diary.providers.store.res.ResJobStore;
import com.google.inject.Inject;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import org.guiceside.persistence.hibernate.dao.enums.Persistent;
import org.guiceside.support.hsf.BaseBiz;
import org.guiceside.support.hsf.HSFServiceFactory;

import java.util.List;


/**
 * @author zhenjiaWang
 * @version 1.0 2012-05
 * @since JDK1.5
 */

public class UserJobBizImp extends BaseBiz implements UserJobBiz {

    @Inject
    private HSFServiceFactory hsfServiceFactory;

    @Override
    public String applyJob(Long userId, Long jobId) throws BizException {
        JSONObject resultObj = new JSONObject();
        resultObj.put("result", -1);
        try {
            AppUserStore appUserStore = hsfServiceFactory.consumer(AppUserStore.class);
            AppUserManStore appUserManStore = hsfServiceFactory.consumer(AppUserManStore.class);
            AppUserLadyStore appUserLadyStore = hsfServiceFactory.consumer(AppUserLadyStore.class);
            ResJobRequireStore resJobRequireStore = hsfServiceFactory.consumer(ResJobRequireStore.class);
            AppUserJobStore appUserJobStore = hsfServiceFactory.consumer(AppUserJobStore.class);
            AppUserLimitStore appUserLimitStore = hsfServiceFactory.consumer(AppUserLimitStore.class);
            ResJobStore resJobStore = hsfServiceFactory.consumer(ResJobStore.class);
            if (appUserManStore != null && appUserLadyStore != null && appUserStore != null
                    && resJobRequireStore != null && appUserJobStore != null && resJobStore != null && appUserLimitStore != null) {
                AppUser appUser = appUserStore.getById(userId);
                if (appUser != null) {
                    List<ResJobRequire> requireList = resJobRequireStore.getListByJobId(jobId);
                    boolean pass = true;
                    Integer jobLimit = 1;
                    Integer day = -1;
                    AppUserMan appUserMan = null;
                    AppUserLady appUserLady = null;
                    AppUserLimit appUserLimit = null;
                    JSONArray resultArray = new JSONArray();
                    if (appUser.getGender() == 1) {
                        appUserMan = appUserManStore.getByUserId(userId);
                        if (appUserMan != null) {
                            day = appUserMan.getDays();
                            jobLimit = appUserLimitStore.getCountByUserIdDayAction(userId, appUserMan.getDays(), "JOB");
                            if (requireList != null && !requireList.isEmpty()) {
                                pass = GameUtils.requirePass(requireList, appUserMan);
                            }
                        }
                    } else if (appUser.getGender() == 2) {
                        appUserLady = appUserLadyStore.getByUserId(userId);
                        if (appUserLady != null) {
                            day = appUserLady.getDays();
                            jobLimit = appUserLimitStore.getCountByUserIdDayAction(userId, appUserLady.getDays(), "JOB");
                            if (requireList != null && !requireList.isEmpty()) {
                                pass = GameUtils.requirePass(requireList, appUserLady);
                            }
                        }
                    }
                    if (jobLimit == 0) {
                        appUserLimit = new AppUserLimit();
                        appUserLimit.setId(DrdsIDUtils.getID(DrdsTable.APP));
                        appUserLimit.setUserId(appUser);
                        appUserLimit.setAction("JOB");
                        appUserLimit.setDay(day);
                        bind(appUserLimit, userId);
                        appUserLimit.setUseYn("Y");
                    }
                    if (pass && jobLimit == 0) {


                        AppUserJob appUserJob = appUserJobStore.getByUserId(userId);
                        ResJob resJob = resJobStore.getById(jobId);
                        if (resJob != null) {
                            Persistent persistent = Persistent.UPDATE;
                            if (appUserJob == null) {
                                appUserJob = new AppUserJob();
                                appUserJob.setId(DrdsIDUtils.getID(DrdsTable.APP));
                                appUserJob.setUserId(appUser);
                                persistent = Persistent.SAVE;
                            }
                            appUserJob.setJobId(resJob);
                            appUserJob.setUseYn("Y");
                            bind(appUserJob, userId);
                            if (appUser.getGender() == 1) {
                                if (appUserMan != null) {
                                    GameUtils.useHour(appUserMan);
                                    appUserJobStore.save(appUserJob, persistent, appUserMan, appUserLimit);
                                }
                            } else if (appUser.getGender() == 2) {
                                if (appUserLady != null) {
                                    GameUtils.useHour(appUserLady);
                                    appUserJobStore.save(appUserJob, persistent, appUserLady, appUserLimit);
                                }
                            }
                            GameUtils.addResultArray(resultArray, "恭喜你轻而易举的得到了面试官的认可，获得了工作！", null);
                            resultObj.put("result", 0);
                            resultObj.put("resultArray", resultArray);
                        }
                    } else {
                        if (jobLimit == 0) {
                            if (appUser.getGender() == 1) {
                                if (appUserMan != null) {
                                    GameUtils.useHour(appUserMan);
                                    appUserManStore.save(appUserMan, Persistent.UPDATE, appUserLimit);
                                }
                            } else if (appUser.getGender() == 2) {
                                if (appUserLady != null) {
                                    GameUtils.useHour(appUserLady);
                                    appUserLadyStore.save(appUserLady, Persistent.UPDATE, appUserLimit);
                                }
                            }
                        }

                        if (jobLimit == 1) {
                            GameUtils.addResultArray(resultArray, "抱歉，每日只能应聘一次工作！", null);
                        } else {
                            JSONArray failAttrName = null;
                            if (appUser.getGender() == 1) {
                                if (appUserMan != null) {
                                    failAttrName = GameUtils.failAttrNames(requireList, appUserMan, appUser.getGender());
                                }
                            } else if (appUser.getGender() == 2) {
                                if (appUserLady != null) {
                                    failAttrName = GameUtils.failAttrNames(requireList, appUserLady, appUser.getGender());
                                }
                            }
                            GameUtils.addResultArray(resultArray, "你卖力的表现了下自己，但是面试官觉得你的能力无法胜任这份工作！", null);
                            GameUtils.addResultArray(resultArray, "原因：", failAttrName);
                        }
                        resultObj.put("result", 1);
                        resultObj.put("resultArray", resultArray);
                    }
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
