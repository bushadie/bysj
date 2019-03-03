package cn.bushadie.sqltest;

import cn.bushadie.project.system.dept.domain.Dept;
import cn.bushadie.project.system.dept.mapper.DeptMapper;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

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

    @Test
    public void selectDeptList(){
        List<Dept> depts=deptMapper.selectDeptList(new Dept());
        assertNotEquals(0,depts.size());
    }
}
