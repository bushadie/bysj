package cn.bushadie;

import cn.hutool.core.date.DateUtil;
import org.apache.commons.lang3.time.DateFormatUtils;
import org.junit.Test;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * @author jdmy
 * on 2019/1/10.
 **/
public class UtilTest {
    @Test
    public void dateTest(){
        Date date=new Date();
        date.setDate(5);
        System.out.println(date);
        System.out.println("1  " +  DateFormat.getDateInstance().format(date));

        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
        System.out.println("2  " + formatter.format(date));
    }

    @Test
    public void f1(){
        System.out.println(new Date());
    }
}
