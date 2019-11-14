package com.cpren;

import cn.hutool.core.date.DateField;
import cn.hutool.core.date.DateUtil;
import com.cpren.dao.HolidaysDao;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * @author cdxu@iyunwen.com on 2019/10/10
 */
@RunWith(SpringRunner.class)
@SpringBootTest
@Slf4j
public class CheckWeekTest {

    @Autowired
    private HolidaysDao holidaysDao;

    @Test
    public void run() {
        int offset = 3;
        int i = checkWeek(new Date(), offset);
        log.info("实际推移时间为："+ i);
    }

    private int checkWeek(Date dateTime, int offset) {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
        String s = simpleDateFormat.format(dateTime);
        Date date = null;
        try {
            date = simpleDateFormat.parse(s);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        // 判断传入的时间是否是周末或节假日
        if(!weekOrHolidays(date)) {
            // 因为生效时间包含传入的这一天，所以推移时间需要减少一天
            if(offset > 0) {
                offset--;
            }
        }

        // 实际推移天数
        int count = 0;
        // 有效推移天数
        int temp = 0;
        // 循环判断推移的这些天中有几天时周末，不是周末的去假期表判断
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        while(temp < offset) {
            cal.add(DateField.DAY_OF_YEAR.getValue(), 1);
            int i = DateUtil.dayOfWeek(cal.getTime());
            if(i==1||i==7) {
                count++;
                continue;
            }else{
                Integer integer = holidaysDao.checkHolidays(cal.getTime());
                if (!integer.equals(0)) {
                    count++;
                    continue;
                }
            }
            count++;
            temp++;
        }
        return count;
    }

    /**
     * @describe 检测今天是星期几，周日是一个星期中的第一天，返回1，周六是一个星期的最后一天，返回7
     * @author cp.ren
     * @date 2019-10-10 10:19:28
     * @param date
     * @return
     * @version V5.0
     **/
    private boolean weekOrHolidays(Date date) {
        boolean flag = false;
        int i = DateUtil.dayOfWeek(date);
        switch (i){
            case 1:
                log.info("今天是周日！");
                break;
            case 2:
                log.info("今天是周一！");
                break;
            case 3:
                log.info("今天是周二！");
                break;
            case 4:
                log.info("今天是周三！");
                break;
            case 5:
                log.info("今天是周四！");
                break;
            case 6:
                log.info("今天是周五！");
                break;
            case 7:
                log.info("今天是周六！");
                break;
        }
        if(i==1||i==7) {
            flag = true;
        }else{
            Integer integer = holidaysDao.checkHolidays(date);
            if (!integer.equals(0)) {
                flag = true;
            }
        }
        return flag;
    }

}
