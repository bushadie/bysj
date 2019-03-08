package cn.bushadie.sqltest;

import cn.bushadie.project.system.user.domain.User;
import cn.bushadie.project.system.user.mapper.UserMapper;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;

/**
 * @author jdmy
 * on 2019/2/16.
 **/
@SpringBootTest
@RunWith( SpringRunner.class)
@Transactional(rollbackFor = Exception.class)
public class UserTest {
    @Resource
    private UserMapper userMapper;

    @Test
    public void selectUserById(){
        User user=userMapper.selectUserById((long)1);
        System.out.println(user);
    }
}
