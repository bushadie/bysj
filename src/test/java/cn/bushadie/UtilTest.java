package cn.bushadie;

import cn.hutool.core.date.DateUtil;
import cn.hutool.core.util.ReflectUtil;
import org.apache.commons.lang3.time.DateFormatUtils;
import org.junit.Test;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
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


    @Test
    public void f2(){
        for(int i=0;i<150;i++) {
            System.out.println(convertNumberToChina(i));
        }
    }

    public String convertNumberToChina(Integer num) {
        String[] intArr={"0","1","2","3","4","5","6","7","8","9",};
        String[] strArr={"零","一","二","三","四","五","六","七","八","九",};
        String[] Chinese={"","十","百","千","万","十","百","千","亿"};
        char[] tmpArr=num.toString().toCharArray();
        String tmpVal="";
        for(int i=0;i<tmpArr.length;i++) {
            tmpVal+=strArr[tmpArr[i]-48]; //ASCII编码 0为48
            tmpVal+=Chinese[tmpArr.length-1-i];//根据对应的位数插入对应的单位
        }
        if(tmpVal.startsWith("一十")) {
            tmpVal=tmpVal.replace("一十","十");
        }
        while(tmpVal.endsWith("零")) {
            tmpVal=tmpVal.substring(0,tmpVal.length()-1);
        }
        //        return tmpVal.("零".toArray());
        return tmpVal;
    }

    @Test
    public void arrayLength(){
        ArrayList<String> list=new ArrayList<>(10086);
        Object elementData=ReflectUtil.getFieldValue(list,"elementData");
        System.out.println("((Object[])elementData).length = "+((Object[])elementData).length);
        System.out.println(elementData);
    }
}
