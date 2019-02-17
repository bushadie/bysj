package cn.bushadie.sqltest;

import cn.bushadie.project.system.competition.domain.Competition;
import cn.bushadie.project.system.competition.mapper.CompetitionMapper;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.List;

import static org.junit.Assert.*;

/**
 * @author jdmy
 * on 2019/2/13.
 **/
@SpringBootTest
@RunWith( SpringRunner.class)
@Transactional(rollbackFor = Exception.class)
public class CompetitionTest{
    private static Long id = 28L;
    @Resource
    private CompetitionMapper competitionMapper;
    @Test
    public void selectCompetitionList(){
        List<Competition> competitions=competitionMapper.selectCompetitionList(null);
        System.out.println(competitions.size());
        assertNotEquals(0,competitions.size());
    }

    @Test
    public void selectCompetitionById(){
        Competition competition=competitionMapper.selectCompetitionById(id);
        System.out.println(competition);
        assertNotNull(competition);
    }

    @Test
    public void updateCompetition(){
        Competition competition=new Competition().setId(28L).setUid(10086).setTitle("tittle");
        competitionMapper.updateCompetition(competition);
        competition=competitionMapper.selectCompetitionById(28L);
        assertEquals(Long.valueOf(10086),Long.valueOf(competition.getUid()));
    }

    @Test
    public void deleteCompetitionByIds(){
        competitionMapper.deleteCompetitionByIds(new String [] {"38","39"});
    }
}
