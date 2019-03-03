package cn.bushadie.project.system.competition.service;

import cn.bushadie.common.support.Convert;
import cn.bushadie.project.system.competition.domain.Competition;
import cn.bushadie.project.system.competition.domain.Group;
import cn.bushadie.project.system.competition.domain.Groupinfo;
import cn.bushadie.project.system.competition.domain.Info;
import cn.bushadie.project.system.competition.mapper.CompetitionMapper;
import cn.bushadie.project.system.competition.mapper.GroupMapper;
import cn.bushadie.project.system.competition.mapper.GroupinfoMapper;
import cn.bushadie.project.system.competition.mapper.InfoMapper;
import cn.bushadie.project.system.role.domain.Role;
import cn.bushadie.project.system.user.domain.User;
import cn.bushadie.project.system.user.mapper.UserMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
    @Autowired
    private GroupinfoMapper groupinfoMapper;

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

    /**
     * 在竞赛中单独创建一个组
     * @param groupId 组id
     * @param userId 用户id
     * @return
     *          0 成功
     *          1 该组数达到上限
     *          2 已经参加过这个事务了
     */
    @Transactional
    public int createLeader( Long groupId,Long userId){
        // 判断组数是否达到上限
        Group group=groupMapper.selectGroupById(groupId);
        if(group.getNum().equals(group.getNowNum())){
            return 1;
        }
        // 判断是否已经参加了这个事务
        if( competitionMapper.checkHasJoinCompetition(group.getCompetitionid(),userId) > 0 ){
            return 2;
        }
        // 成功当上组长
        Groupinfo groupinfo=new Groupinfo().setLeaderid(userId).setGroupid(groupId).setUid(userId);
        groupinfoMapper.insertGroupinfo(groupinfo);
        groupMapper.increaseGroupNum(groupId);
        return 0;
    }

    /**
     * 辞去队长
     * @param groupId group
     * @param userId userid
     * @return 0 正常辞去
     *          1 不是队长
     */
    @Transactional
    public int leaveLeader(Long groupId,Long userId) {
        int count=groupinfoMapper.deleteGroupinfoByGroupIdAndUserId(groupId,userId);
        if( count!=0 ){
            groupMapper.decreaseGroupNum(groupId);
            return 0;
        }
        return 1;
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

    /**
     * 从list中删除不含name的结点
     * @param list list
     * @param name 删除不含name的内容
     * @return list
     */
    public List<Competition> delCompetitionFormListByName(List<Competition> list,String name){
        list.removeIf(i ->{
            try {
                return !i.getUser().getUserName().contains(name);
            }catch(Exception e) {
                return true;
            }
        });
        return list;
    }

    /**
     * 从list中删除不是uid创建的结点
     * @param list list
     * @param uid 删除不是uid创建的competition
     * @return list
     */
    public List<Competition> delCompetitionFromListByUid(List<Competition> list,Long uid){
        list.removeIf(i->{
            return !i.getUser().getUserId().equals( uid );
        });
        return list;
    }

    /**
     * 查询竞赛管理树
     *
     * @return 所有符合条件的competition
     *
     */
    public List<Map<String,Object>> selectCompetitionTree() {
        List<Competition> competitionList=selectCompetitionList(new Competition());
        ArrayList<Map<String,Object>> trees=new ArrayList<>(competitionList.size());
        trees=getTrees(competitionList,false,null);
        return trees;
    }

    private ArrayList<Map<String ,Object>> getTrees(List<Competition> competitionList,boolean isCheck,List<String> roleDeptList){
        ArrayList<Map<String,Object>> trees=new ArrayList<>(competitionList.size());
        // 标记是否有选中的,  没有则默认第一个为选中状态
        int flag=0;
        for(Competition competition: competitionList) {
            HashMap<String,Object> competitionMap=new HashMap<>(5);
            competitionMap.put("id",competition.getId());
            competitionMap.put("pId","0");
            competitionMap.put("name",competition.getTitle());
            competitionMap.put("title",competition.getTitle());
            if( isCheck ){
                if( roleDeptList.contains(competition.getId()+competition.getTitle()) ){
                    flag++;
                    competitionMap.put("checked",true);
                }else {
                    competitionMap.put("checked",false);
                }
            }else {
                competitionMap.put("check",false);
            }
            trees.add(competitionMap);
        }
        if( flag==0 ){
            trees.get(0).put("checked",true);
        }
        return trees;
    }
}
