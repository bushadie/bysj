package cn.bushadie.sqltest;

import cn.bushadie.project.system.competition.mapper.GroupMapper;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

/**
 * @author jdmy
 * on 2019/3/2.
 **/
@SpringBootTest
@RunWith( SpringRunner.class)
@Transactional(rollbackFor = Exception.class)
public class GroupTest {

    @Autowired
    private GroupMapper groupMapper;

    @Test
    public void increaseGroupNum(){
        groupMapper.increaseGroupNum(51L);
    }
}
