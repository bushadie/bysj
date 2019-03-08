package cn.bushadie;

import cn.bushadie.project.system.competition.domain.Competition;
import cn.bushadie.project.system.competition.service.CompetitionService;
import cn.bushadie.project.system.user.service.UserServiceImpl;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * @author jdmy
 * on 2019/3/7.
 **/
@SpringBootTest
@RunWith( SpringRunner.class)
@Transactional(rollbackFor = Exception.class)
public class BaseTest {
    @Autowired
    private CompetitionService competitionService;
    @Autowired
    private UserServiceImpl userService;

    @Test
    public void f1(){
        List<Competition> list=competitionService.selectCompetitionListOpen();
        System.out.println(list.size());
        competitionService.delCompetitionFormListNotStart(list);
        System.out.println(list.size());
        competitionService.delCompetitionFormListByDeptId(list,userService.selectUserById(6L));
        System.out.println(list.size());
    }

    @Test
    public void f2(){
        System.out.println(userService.selectUserById(18L));
    }
}
