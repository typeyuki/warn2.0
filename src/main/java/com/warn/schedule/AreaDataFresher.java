package com.warn.schedule;

import com.warn.dao.SensorDataDao;
import com.warn.entity.SensorData;
import com.warn.mongodb.model.SensorCollection;
import com.warn.service.StatisticService;
import com.warn.util.DynamicDataSourceHolder;
import com.warn.util.Tool.Tool;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.List;
import java.util.TimeZone;

@Component
public class AreaDataFresher {
    @Autowired
    StatisticService statisticService;
    @Autowired
    SensorDataDao sensorDataDao;

    @Scheduled(cron = "0 0 0-12 * * ?")
    public void updateArea(){
        statisticService.getStatisticData(48);
        statisticService.getStatisticData(43);
        statisticService.getStatisticData(42);

    }

    @Scheduled(cron = "0 0 12 * * ?")
    public void check(){
        statisticService.checkStatistic(48,155);
    }

    @Scheduled(cron = "0 1 0-12 * * ?")
    public void updateArea2(){
        statisticService.getStatisticData(48);
        statisticService.getStatisticData(43);
        statisticService.getStatisticData(42);
    }

    @Scheduled(cron = "0 1 16 * * *")
    public void recordForSql(){
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        sdf.setTimeZone(TimeZone.getTimeZone("Asia/Shanghai"));
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.HOUR_OF_DAY,-24);
        String yesterday = sdf.format(calendar.getTime());
        DynamicDataSourceHolder.setDataSource("sensorDataSource");
        SensorCollection sensorCollection = sensorDataDao.getDateEndRecord(yesterday);
        sensorDataDao.addDateRecord(sensorCollection);
    }

    @Scheduled(cron = "0 1 16 * * *")
    public void recordGatewayForSql(){
        DynamicDataSourceHolder.setDataSource("sensorDataSource");
        List<SensorCollection> sensorCollections = sensorDataDao.getDateEndGateway(Tool.getYesDate());
        for(SensorCollection sensorCollection:sensorCollections){
            SensorCollection sensorCollection1 = sensorDataDao.getDateGateway(sensorCollection);
            sensorDataDao.addDateGateway(sensorCollection1);
        }
    }


}
