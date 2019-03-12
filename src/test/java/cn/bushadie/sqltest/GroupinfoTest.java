package cn.bushadie.sqltest;

import cn.bushadie.project.system.competition.domain.Groupinfo;
import cn.bushadie.project.system.competition.mapper.GroupMapper;
import cn.bushadie.project.system.competition.mapper.GroupinfoMapper;
import cn.bushadie.project.system.competition.service.GroupinfoService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

/**
 * @author jdmy
 * on 2019/3/3.
 **/
@SpringBootTest
@RunWith( SpringRunner.class)
@Transactional(rollbackFor = Exception.class)
public class GroupinfoTest {
    @Autowired
    private GroupinfoService groupinfoService;
    @Autowired
    private GroupinfoMapper groupinfoMapper;

    @Test
    public void countMemberNumber(){
//        System.out.println(groupinfoService.countMemberNumber(51L));
    }

    @Test
    public void remainTeamNum(){
        int num=0;
        try {
            num=groupinfoService.remainTeamNum(51L,1L);
        }catch(Exception e) {
            e.printStackTrace();
        }
        System.out.println("num = "+num);
        try {
            num=groupinfoService.remainTeamNum(1L,51L);
        }catch(Exception e) {
            e.printStackTrace();
        }
        System.out.println("num = "+num);
    }

    @Test
    public void changeLeaderId(){
        groupinfoMapper.changeLeaderId(51L,2L);
    }

    @Test
    public void selectGroupinfoList(){
        groupinfoMapper.selectGroupinfoList(new Groupinfo());
    }
}
