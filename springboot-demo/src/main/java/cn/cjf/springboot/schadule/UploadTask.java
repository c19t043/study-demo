package cn.cjf.springboot.schadule;

import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.quartz.QuartzJobBean;

import javax.annotation.Resource;

@Configuration
@DisallowConcurrentExecution
public class UploadTask extends QuartzJobBean {
    @Resource
    private TencentYunService tencentYunService;
    @Override
    protected void executeInternal(JobExecutionContext jobExecutionContext) throws JobExecutionException {
        System.out.println("任务开始");
        try {
            Thread.sleep(6000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println("任务结束");
    }
}
