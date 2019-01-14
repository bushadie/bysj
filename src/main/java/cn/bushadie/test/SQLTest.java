package cn.bushadie.test;

import cn.bushadie.project.system.competition.domain.Competition;
import cn.bushadie.project.system.competition.domain.Info;
import cn.bushadie.project.system.competition.mapper.CompetitionMapper;
import cn.bushadie.project.system.competition.mapper.InfoMapper;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

/**
 * @author jdmy
 * on 2019/1/6.
 **/
@SpringBootTest
@RunWith( SpringRunner.class)
@Transactional(rollbackFor = Exception.class)
public class SQLTest {
    private static Integer id = 24;
    @Autowired
    private CompetitionMapper competitionMapper;
    @Autowired
    private InfoMapper infoMapper;

    /**
     *  更具id 查询 competition
     */
    @Test
    public void competitionMapperTest(){
        Competition item=competitionMapper.selectCompetitionById(1);
        System.out.println(item);
    }

    @Test
    public void infoMapperInsertTest(){
        Info info=new Info();
        info.setK("k").setV("v");
        infoMapper.insertInfo(info);
        System.out.println(info);
//        TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
    }

    @Test
    public void selectInfoTest(){
        Competition item=competitionMapper.selectCompetitionById(id);
        System.out.println(item);
        System.out.println(item.simpleStartTime());
        System.out.println(item.simpleEndTime());
    }

    @Test
    public void deleteCompetition(){
        String [] str ={"20"};
        selectInfoTest();
        competitionMapper.deleteCompetitionByIds(str);
        selectInfoTest();
    }
}
