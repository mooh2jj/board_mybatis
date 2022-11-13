package com.example.board_springboot.common.scheduler;

import org.quartz.*;
import org.quartz.impl.StdSchedulerFactory;

import javax.annotation.PostConstruct;

/*public class TestScheduler {

    private SchedulerFactory schedulerFactory;
    private Scheduler scheduler;

    @PostConstruct
    public void start() throws SchedulerException {

        schedulerFactory = new StdSchedulerFactory();
        scheduler = schedulerFactory.getScheduler();
        scheduler.start();

        JobDetail job = JobBuilder.newJob(TestJob.class).withIdentity("testJob").build();

        // trigger 생성
        Trigger trigger = TriggerBuilder.newTrigger()
                // 매분 15초에 진행
                .withSchedule(CronScheduleBuilder.cronSchedule("15 * * * * ?"))
                .build();
        scheduler.scheduleJob(job, trigger);
    }
}*/
