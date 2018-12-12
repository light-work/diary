/*
 * All content copyright Terracotta, Inc., unless otherwise indicated. All rights reserved.
 *
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not
 * use this file except in compliance with the License. You may obtain a copy
 * of the License at
 *
 *   http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS, WITHOUT
 * WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the
 * License for the specific language governing permissions and limitations
 * under the License.
 *
 */

package com.diary.startup;

import com.diary.entity.res.ResAccessToken;
import com.diary.entity.utils.GameUtils;
import com.diary.providers.store.res.ResAccessTokenStore;
import com.google.inject.Injector;
import net.sf.json.JSONObject;
import org.guiceside.commons.OKHttpUtil;
import org.guiceside.commons.Page;
import org.guiceside.commons.lang.DateFormatUtil;
import org.guiceside.commons.lang.StringUtils;
import org.guiceside.persistence.entity.search.SelectorUtils;
import org.guiceside.persistence.hibernate.dao.enums.Persistent;
import org.guiceside.persistence.hibernate.dao.hquery.Selector;
import org.guiceside.support.hsf.HSFServiceFactory;
import org.quartz.*;

import java.util.*;

/**
 * <p>
 * This is just a simple job that says "Hello" to the world.
 * </p>
 *
 * @author Bill Kratzer
 */
@DisallowConcurrentExecution
@PersistJobDataAfterExecution
public class AccessTokenTask implements Job {


    /**
     * <p>
     * Empty constructor for job initilization
     * </p>
     * <p>
     * Quartz requires a public empty constructor so that the
     * scheduler can instantiate the class whenever it needs.
     * </p>
     */
    public AccessTokenTask() {
    }

    private void buildAccessToken(ResAccessTokenStore resAccessTokenStore) throws Exception {
        Map<String, String> parMap = new HashMap<>();
        parMap.put("grant_type", "client_credential");
        parMap.put("appid", GameUtils.appId);
        parMap.put("secret", GameUtils.secret);
        try {
            System.out.println("api.weixin.qq.com/cgi-bin/token ==");
            String r = OKHttpUtil.post("https://api.weixin.qq.com/cgi-bin/token", parMap);
            System.out.println(r);
            if (StringUtils.isNotBlank(r)) {
                JSONObject jsonObject = JSONObject.fromObject(r);
                if (jsonObject != null) {
                    String access_token = jsonObject.getString("access_token");
                    if (StringUtils.isNotBlank(access_token)) {
                        ResAccessToken resAccessTokenNew = new ResAccessToken();
                        resAccessTokenNew.setTokenValue(access_token);
                        Date d = DateFormatUtil.getCurrentDate(true);
                        resAccessTokenNew.setCreated(d);
                        resAccessTokenNew.setCreatedBy("task");
                        resAccessTokenNew.setUpdated(d);
                        resAccessTokenNew.setUpdatedBy("task");
                        resAccessTokenNew.setUseYn("Y");
                        resAccessTokenStore.save(resAccessTokenNew, Persistent.SAVE);
                        System.out.println("create new access_token =" + access_token);
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * <p>
     * Called by the <code>{@link Scheduler}</code> when a
     * <code>{@link Trigger}</code> fires that is associated with
     * the <code>Job</code>.
     * </p>
     *
     * @throws JobExecutionException if there is an exception while executing the job.
     */
    public void execute(JobExecutionContext context)
            throws JobExecutionException {
        JobDataMap dataMap = context.getJobDetail().getJobDataMap();
        Injector injector = (Injector) dataMap.get("injector");
        System.out.println("启动 AccessTokenTask");
        if (injector != null) {
            HSFServiceFactory hsfServiceFactory = injector.getInstance(HSFServiceFactory.class);
            System.out.println("启动 hsfServiceFactory");
            if (hsfServiceFactory != null) {
                try {
                    ResAccessTokenStore resAccessTokenStore = hsfServiceFactory.consumer(ResAccessTokenStore.class);
                    if (resAccessTokenStore != null) {
                        Date cudDate = DateFormatUtil.getCurrentDate(true);
                        List<Selector> selectorList = new ArrayList<>();
                        selectorList.add(SelectorUtils.$order("id", false));
                        Page<ResAccessToken> accessTokenPage = resAccessTokenStore.getPageList(0, 1, selectorList);
                        if (accessTokenPage != null) {
                            List<ResAccessToken> accessTokenList = accessTokenPage.getResultList();
                            if (accessTokenList != null && !accessTokenList.isEmpty()) {
                                ResAccessToken resAccessToken = accessTokenList.get(0);
                                Date cud=DateFormatUtil.addMinute(resAccessToken.getCreated(),10);
                                long diff = DateFormatUtil.calendarMinutePlus(cudDate,cud);
                                System.out.println("access_token diff="+diff);
                                if (diff <= 5) {
                                    buildAccessToken(resAccessTokenStore);
                                }else{
                                    System.out.println("current  access_token effective");
                                }
                            } else {
                                buildAccessToken(resAccessTokenStore);
                            }
                        } else {
                            buildAccessToken(resAccessTokenStore);
                        }
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
