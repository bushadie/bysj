package cn.bushadie.project.system.competition.service;

import java.util.List;

import cn.bushadie.project.system.competition.domain.Group;
import cn.bushadie.project.system.competition.domain.Info;
import cn.bushadie.project.system.competition.mapper.GroupMapper;
import cn.bushadie.project.system.competition.mapper.InfoMapper;
import cn.bushadie.project.system.role.domain.Role;
import cn.bushadie.project.system.user.domain.User;
import cn.bushadie.project.system.user.mapper.UserMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import cn.bushadie.project.system.competition.mapper.CompetitionMapper;
import cn.bushadie.project.system.competition.domain.Competition;
import cn.bushadie.common.support.Convert;
import org.springframework.transaction.annotation.Transactional;

/**
 * 竞赛 服务层实现
 *
 * @author jdmy
 * @date 2018-12-21
 */
@Service
public class CompetitionService {
    @Autowired
    private CompetitionMapper competitionMapper;
    @Autowired
    private InfoMapper infoMapper;
    @Autowired
    private GroupMapper groupMapper;
    @Autowired
    private UserMapper userMapper;

    /**
     * 查询竞赛信息
     *
     * @param id 竞赛ID
     * @return 竞赛信息
     */
    public Competition selectCompetitionById(Long id) {
        return competitionMapper.selectCompetitionById(id);
    }

    /**
     * 查询竞赛列表
     *
     * @param competition 竞赛信息
     * @return 竞赛集合
     */
    public List<Competition> selectCompetitionList(Competition competition) {
        return competitionMapper.selectCompetitionList(competition);
    }

    /**
     * 新增竞赛
     *
     * @param competition 竞赛信息
     * @return 结果
     */
    @Transactional
    public int insertCompetition(Competition competition) {
        int result=competitionMapper.insertCompetition(competition);
        // 保存事务其他信息
        for(Info info: competition.getInfos()) {
            info.setCompetitionid(competition.getId());
            infoMapper.insertInfo(info);
        }
        // 保存组数限制
        for(Group group: competition.getGroups()) {
            group.setCompetitionid(competition.getId());
            groupMapper.insertGroup(group);
        }
        return result;
    }

    /**
     * 修改竞赛
     *
     * @param competition 竞赛信息
     * @return 结果
     */
    @Transactional
    public int updateCompetition(Competition competition) {
        infoMapper.deleteInfoByCompetitionId(competition.getId());
        if( competition.getInfos().size()>0 ){
            infoMapper.insertInfos(competition.getInfos());
        }

        groupMapper.deleteGroupByCompetitionId(competition.getId());
        if( competition.getGroups().size()>0 ){
            groupMapper.insertGroups(competition.getGroups());
        }
        return competitionMapper.updateCompetition(competition);
    }

    /**
     * 删除竞赛对象
     *
     * @param ids 需要删除的数据ID
     * @return 结果
     */
    public int deleteCompetitionByIds(String ids) {
        return competitionMapper.deleteCompetitionByIds(Convert.toStrArray(ids));
    }

    public boolean isOnlyTeacher(long id){
        User user=userMapper.selectUserById((long)id);
        boolean isTeacher=false,isAdmin=false;
        for(Role role: user.getRoles()) {
            if("teacher".equals(role.getRoleKey())){
                isTeacher=true;
            }
            if("admin".equals(role.getRoleKey())){
                isAdmin=true;
            }
        }
        return (!isAdmin) && isTeacher;
    }
}
