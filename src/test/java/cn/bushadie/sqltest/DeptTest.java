package cn.bushadie.sqltest;

import cn.bushadie.project.system.dept.domain.Dept;
import cn.bushadie.project.system.dept.mapper.DeptMapper;
import cn.bushadie.project.system.dept.service.DeptServiceImpl;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;

import static org.junit.Assert.assertNotEquals;

/**
 * @author jdmy
 * on 2019/2/22.
 **/
@SpringBootTest
@RunWith(SpringRunner.class)
@Transactional(rollbackFor=Exception.class)
public class DeptTest {

    @Autowired
    private DeptMapper deptMapper;
    @Autowired
    private DeptServiceImpl deptService;

    @Test
    public void selectDeptList(){
        List<Dept> depts=deptMapper.selectDeptList(new Dept());
        assertNotEquals(0,depts.size());
    }

    @Test
    public void selectDeptTree(){
        List<Map<String,Object>> tree=deptService.selectDeptTree();
        System.out.println(tree);
    }

    @Test
    public void selectDeptByIds(){
        List<Dept> list1=deptService.selectAllDeptByIds("");
        List<Dept> list2=deptService.selectAllDeptByIds("119");
        List<Dept> list3=deptService.selectAllDeptByIds("119,120");
        List<Dept> list4=deptService.selectAllDeptByIds("115,116,119");
        System.out.println("temp");

    }
}
