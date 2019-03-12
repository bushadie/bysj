package cn.bushadie.project.system.competition.service;

import cn.bushadie.common.support.Convert;
import cn.bushadie.project.system.competition.domain.Groupinfo;
import cn.bushadie.project.system.competition.mapper.CompetitionMapper;
import cn.bushadie.project.system.competition.mapper.GroupinfoMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * 每组人员 服务层实现
 *
 * @author jdmy
 * @date 2019-01-12
 */
@Service
public class GroupinfoService {

    @Autowired
    private CompetitionService competitionService;

    @Autowired
    private GroupinfoMapper groupinfoMapper;
    @Autowired
    private CompetitionMapper competitionMapper;
    /**
     * 查询每组人员信息
     *
     * @param id 每组人员ID
     * @return 每组人员信息
     */
    public Groupinfo selectGroupinfoById(Long id) {
        return groupinfoMapper.selectGroupinfoById(id);
    }

    /**
     * 查询每组人员列表
     *
     * @param groupinfo 每组人员信息
     * @return 每组人员集合
     */
    public List<Groupinfo> selectGroupinfoList(Groupinfo groupinfo) {
        return groupinfoMapper.selectGroupinfoList(groupinfo);
    }

    /**
     * 新增每组人员
     *
     * @param groupinfo 每组人员信息
     * @return 结果
     */
    public int insertGroupinfo(Groupinfo groupinfo) {
        return groupinfoMapper.insertGroupinfo(groupinfo);
    }

    /**
     * 修改每组人员
     *
     * @param groupinfo 每组人员信息
     * @return 结果
     */
    public int updateGroupinfo(Groupinfo groupinfo) {
        return groupinfoMapper.updateGroupinfo(groupinfo);
    }

    /**
     * 删除每组人员对象
     *
     * @param ids 需要删除的数据ID
     * @return 结果
     */
    public int deleteGroupinfoByIds(String ids) {
        return groupinfoMapper.deleteGroupinfoByIds(Convert.toStrArray(ids));
    }

    /**
     * 统计该组中有多少人
     *
     * @param groupid groupid
     * @return 结果
     */
    public Long countMemberNumber(Long groupid,Long leaderId){
        return groupinfoMapper.countGroupMemberNumber(groupid,leaderId);
    }
    /**
     * 统计该组中还剩多少名额
     * @param leaderid  队长id
     * @param groupid 队组id
     * @return 剩余数量
     */
    public int remainTeamNum(Long groupid,Long leaderid){
        return groupinfoMapper.remainTeamNum(groupid,leaderid);
    }

    /**
     * 离开队伍
     * @param competitionid
     * @param groupinfoId
     * @return  0正常辞去
     *          1 不是队长
     *          2 有其他成员,请先让贤
     */
    @Transactional
    public int quitTeam(Long competitionid,Long groupinfoId) {
        // 判断是不是队长
        Groupinfo groupinfo=groupinfoMapper.selectGroupinfoById(groupinfoId);
        if( groupinfo.getUid().equals(groupinfo.getLeaderid()) ){
            return competitionService.leaveLeader(groupinfo.getGroupid(),groupinfo.getUid());
        }
        groupinfoMapper.deleteGroupinfoById(groupinfoId);
        competitionMapper.decreaseCompetitionNum(competitionid);
        return 0;
    }
}
