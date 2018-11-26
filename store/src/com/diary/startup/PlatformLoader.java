package com.diary.startup;


import com.google.inject.Injector;
import org.apache.log4j.Logger;
import org.guiceside.commons.TimeUtils;
import org.guiceside.commons.lang.StringUtils;
import org.guiceside.support.properties.PropertiesConfig;
import org.guiceside.support.redis.RedisPoolProvider;
import org.guiceside.web.listener.DefaultGuiceSideListener;
import org.quartz.*;
import org.quartz.impl.StdSchedulerFactory;

import javax.servlet.ServletContext;
import javax.servlet.UnavailableException;

import static org.quartz.JobBuilder.newJob;
import static org.quartz.TriggerBuilder.newTrigger;
import static org.quartz.CronScheduleBuilder.cronSchedule;


/**
 * @author zhenjia <a href='mailto:zhenjiaWang@gmail.com'>email</a>
 * @version 1.0 2008-12-1
 * @since JDK1.5
 */
public class PlatformLoader {
    private static final Logger log = Logger.getLogger(PlatformLoader.class);
    private Scheduler scheduler;

    public void init(ServletContext servletContext) throws Exception {
        long tStart = System.currentTimeMillis();
        long tEnd = System.currentTimeMillis();
        if (log.isDebugEnabled()) {
            log.debug("PlatformLoader done! time:" + TimeUtils.getTimeDiff(tStart, tEnd));
        }
        PropertiesConfig webConfig = new PropertiesConfig("webconfig.properties");
        servletContext.setAttribute("webConfig", webConfig);

        Injector injector = (Injector) servletContext
                .getAttribute(Injector.class.getName());
        if (injector == null) {
            log.error("Guice Injector not found", new UnavailableException(
                    "Guice Injector not found (did you forget to register a "
                            + DefaultGuiceSideListener.class.getSimpleName()
                            + "?)"));
            throw new Exception(
                    "Guice Injector not found (did you forget to register a "
                            + DefaultGuiceSideListener.class.getSimpleName()
                            + "?)");
        }
        injector = (Injector) servletContext.getAttribute(Injector.class
                .getName());
        if (injector == null) {
            log.error("Guice Injector not found", new UnavailableException(
                    "Guice Injector not found (did you forget to register a "
                            + DefaultGuiceSideListener.class.getSimpleName()
                            + "?)"));
        }

        String releaseEnvironment = webConfig.getString("releaseEnvironment");
        if (StringUtils.isNotBlank(releaseEnvironment)) {
            if(releaseEnvironment.equals("DIS")){
                RedisPoolProvider.init(webConfig);
            }
        }

        try {
            // Grab the Scheduler instance from the Factory
            scheduler = StdSchedulerFactory.getDefaultScheduler();
            JobDataMap jobDataMap = new JobDataMap();
            jobDataMap.put("injector", injector);

            String timeStart="0 0/2 * * * ?";

            JobDetail jobAccessTokenTask= newJob(AccessTokenTask.class).withIdentity("jobAccessTokenTask", "groupJobAccessTokenTask")
                    .usingJobData(jobDataMap).build();
            CronTrigger triggerJobAccessTokenTask = newTrigger()
                    .withIdentity("triggerAccessTokenTask", "groupJobAccessTokenTask")
                    .withSchedule(cronSchedule(timeStart))
                    .build();

            scheduler.scheduleJob(jobAccessTokenTask, triggerJobAccessTokenTask);
            scheduler.start();
            System.out.println("启动 task");
        }catch (SchedulerException se) {
            se.printStackTrace();
        }


    }


    public void destroyed(ServletContext servletContext) throws Exception {

    }
}