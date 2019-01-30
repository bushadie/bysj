package cn.bushadie;

import cn.bushadie.project.system.competition.domain.Competition;
import cn.bushadie.project.system.competition.domain.Group;
import cn.bushadie.project.system.competition.domain.Info;
import cn.bushadie.project.system.competition.mapper.CompetitionMapper;
import cn.bushadie.project.system.competition.mapper.GroupMapper;
import cn.bushadie.project.system.competition.mapper.GroupinfoMapper;
import cn.bushadie.project.system.competition.mapper.InfoMapper;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.ArrayList;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;

/**
 * @author jdmy
 * on 2019/1/6.
 **/
@SpringBootTest
@RunWith( SpringRunner.class)
@Transactional(rollbackFor = Exception.class)
public class SQLTest {
    private static Integer id = 27;
    @Resource
    private CompetitionMapper competitionMapper;
    @Resource
    private InfoMapper infoMapper;
    @Autowired
    private GroupinfoMapper groupinfoMapper;
    @Autowired
    private GroupMapper groupMapper;

    /**
     *  更具id 查询 competition
     */
    @Test
    public void competitionMapperTest(){
        Competition item=competitionMapper.selectCompetitionById(id);
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

        assertNotNull(groupMapper.deleteGroupByCompetitionId(id));

        String [] str ={id.toString()};
        Competition item=competitionMapper.selectCompetitionById(id);
        assertNotNull(item);
        competitionMapper.deleteCompetitionByIds(str);
        item=competitionMapper.selectCompetitionById(id);
        assertNull(item);

        assertNull(groupMapper.deleteGroupByCompetitionId(id));
    }

    @Test
    public void insertInfoTest(){
        ArrayList<Info> infos=new ArrayList<>(2);
        infos.add(new Info().setK("k").setV("v").setCompetitionid(1));
        infos.add(new Info().setK("k").setV("v").setCompetitionid(1));
        infoMapper.insertInfos(infos);
    }

    @Test
    public void insertGroupTest(){
        ArrayList<Group> groups=new ArrayList<>(2);
        groups.add(new Group().setCompetitionid(1).setLeast(1).setMost(2).setNum(3));
        groups.add(new Group().setCompetitionid(1).setLeast(1).setMost(2).setNum(3));
        groupMapper.insertGroups(groups);
    }
}
