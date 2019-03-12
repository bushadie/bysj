package cn.bushadie;

import cn.bushadie.common.utils.poi.ExcelUtil;
import cn.bushadie.project.system.competition.domain.Competition;
import cn.hutool.core.collection.CollUtil;
import cn.hutool.poi.excel.ExcelWriter;
import org.junit.Test;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.Map;

/**
 * @author jdmy
 * on 2019/3/10.
 **/
public class ExcelUtilTest {
    @Test
    public void pathTest(){
        ExcelUtil<Competition> util=new ExcelUtil<>(Competition.class);
        System.out.println(util.getAbsoluteFile("123456"));
    }

    @Test
    public void getXls(){
        ExcelWriter writer=cn.hutool.poi.excel.ExcelUtil.getWriter("D:/desk/test.xls");
        ArrayList<String> row=new ArrayList<>();
        row.add("单元格长度测试测测试");
        writer.write(row);
        writer.autoSizeColumn(0);

        row=CollUtil.newArrayList("123","456","789");
        writer.write(row);


        Competition test=new Competition().setTitle("test").setId(1L);
        ArrayList<Competition> competitions=CollUtil.newArrayList(test);
        writer.write(competitions);

        ArrayList<ArrayList<String>> lists=new ArrayList<>();
        ArrayList<String> list=new ArrayList<>(10);
        list.add("list1");list.add("list2");
        lists.add(list);
        writer.write(lists);



//        String [] arr = new String [5];
//        ArrayList<String> strings=CollUtil.newArrayList(arr);
//        writer.write(lists);
        writer.close();
    }

}
