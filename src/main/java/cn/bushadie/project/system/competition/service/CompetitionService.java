package cn.bushadie.project.system.competition.service;

import cn.bushadie.common.support.Convert;
import cn.bushadie.common.utils.DateUtils;
import cn.bushadie.project.system.competition.domain.*;
import cn.bushadie.project.system.competition.mapper.*;
import cn.bushadie.project.system.dept.domain.Dept;
import cn.bushadie.project.system.dept.service.DeptServiceImpl;
import cn.bushadie.project.system.role.domain.Role;
import cn.bushadie.project.system.user.domain.User;
import cn.bushadie.project.system.user.mapper.UserMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;
import java.util.stream.Collectors;

/**
 * 竞赛 服务层实现
 *
 * @author jdmy
 * @date 2018-12-21
 */
@Service("competition")
public class CompetitionService {

    @Autowired
    private DeptServiceImpl deptService;

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
    @Autowired
    private LimitMapper limitMapper;
    @Autowired
    private LimitService limitService;

    /**
     * 查询竞赛信息
     *
     * @param id 竞赛ID
     * @return 不携带limit的competition信息
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
     * 查询 开放的竞赛列表 (signUpStatus=1 正常开放     0 关闭)
     * @return
     */
    public List<Competition> selectCompetitionListOpen(){
        return competitionMapper.selectCompetitionListOpen();
    }


    /**
     * 新增竞赛
     *
     * @param competition 竞赛信息
     * @return 结果
     */
    @Transactional
    public int insertCompetition(Competition competition,String[] limitGroupIds) {
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
        // 保存限制
        limitService.insertLimit(competition.getId(),limitGroupIds);

        return result;
    }

    /**
     * 修改竞赛
     *
     * @param competition 竞赛信息
     * @return 结果
     */
    @Transactional
    public int updateCompetition(Competition competition,String[] limits) {
        infoMapper.deleteInfoByCompetitionId(competition.getId());
        if( competition.getInfos().size()>0 ){
            infoMapper.insertInfos(competition.getInfos());
        }

        groupMapper.deleteGroupByCompetitionId(competition.getId());
        if( competition.getGroups().size()>0 ){
            groupMapper.insertGroups(competition.getGroups());
        }

        limitMapper.deleteLimitByCompetitionId(competition.getId());
        if( limits.length >0 ){
            limitService.insertLimit(competition.getId(),limits);
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
        if( checkHasJoinCompetition(group.getCompetitionid(),userId)){
            return 2;
        }
        // 成功当上组长
        Groupinfo groupinfo=new Groupinfo().setLeaderid(userId).setGroupid(groupId).setUid(userId);
        groupinfoMapper.insertGroupinfo(groupinfo);
        groupMapper.increaseGroupNum(groupId);
        competitionMapper.increaseCompetitionNum(group.getCompetitionid());
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
            Group group=groupMapper.selectGroupById(groupId);
            competitionMapper.decreaseCompetitionNum(group.getCompetitionid());
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
        list.removeIf(i->!i.getUser().getUserId().equals( uid ));
        return list;
    }



    /**
     * 手动排序
     * @param list
     * @param orderByColumn
     * @param isAsc
     * @return
     */
    public List<Competition> sortList(List<Competition> list,String orderByColumn,String isAsc){
        if( "asc".equals(isAsc) ){
            switch(orderByColumn){
                case "startTime": list = list.stream().sorted(Comparator.comparing(Competition::getStartTime)).collect(Collectors.toList()); break;
                case "endTime": list = list.stream().sorted(Comparator.comparing(Competition::getEndTime)).collect(Collectors.toList()); break;
                case "user.userName": list = list.stream().sorted(Comparator.comparing(i->i.getUser().getUserName())).collect(Collectors.toList()); break;
                case "title": list = list.stream().sorted(Comparator.comparing(Competition::getTitle)).collect(Collectors.toList()); break;
                case "num": list = list.stream().sorted(Comparator.comparing(Competition::getNum)).collect(Collectors.toList()); break;
                case "signUpStatus": list = list.stream().sorted(Comparator.comparing(Competition::getSignUpStatus)).collect(Collectors.toList()); break;
                default:;
            }
        }else {
            switch(orderByColumn){
                case "startTime": list = list.stream().sorted(Comparator.comparing(Competition::getStartTime).reversed()).collect(Collectors.toList()); break;
                case "endTime": list = list.stream().sorted(Comparator.comparing(Competition::getEndTime).reversed()).collect(Collectors.toList()); break;
                case "user.userName": list = list.stream().sorted(Comparator.comparing(Competition::getUserName).reversed()).collect(Collectors.toList()); break;
                case "title": list = list.stream().sorted(Comparator.comparing(Competition::getTitle).reversed()).collect(Collectors.toList()); break;
                case "num": list = list.stream().sorted(Comparator.comparing(Competition::getNum).reversed()).collect(Collectors.toList()); break;
                case "signUpStatus": list = list.stream().sorted(Comparator.comparing(Competition::getSignUpStatus).reversed()).collect(Collectors.toList()); break;
                default:;
            }
        }
        return list;
    }

    /**
     * 手动分页
     * @param list
     * @param pageSize 每页大小
     * @param pageNum 页数
     * @return
     */
    public List<Competition> delCompetitionFormListByPage(List<Competition> list,Integer pageSize,Integer pageNum){
        return list.subList((pageNum-1)*pageSize,pageSize );
    }

    /**
     * 删除未开始的competition
     * @param list
     * @return
     */
    public List<Competition> delCompetitionFormListNotStart(List<Competition> list) {
        list.removeIf(i->{
            int flag = DateUtils.isBetween(i.getStartTime(),i.getEndTime());
            if( flag == 0 ){
                return false;
            }else if( flag >0 ){
                setCompetitionLate(i);
            }else {
                setCompetitionEarly(i);
            }
            return  true;
        });
        return list;
    }

    /**
     * 设置状态为  未开始2
     * @param competition
     */
    @Async
    public void setCompetitionEarly(Competition competition){
        if( !"0".equals(competition.getSignUpStatus())){
            competitionMapper.setCompetitionEarly(competition.getId());
        }
    }

    /**
     * 设置状态为  已结束3
     * @param competition
     */
    @Async
    public void setCompetitionLate(Competition competition){
        if( !"0".equals(competition.getSignUpStatus())){
            competitionMapper.setCompetitionLate(competition.getId());
        }
    }

    /**
     * dept权限,  删除不能报名的competition
     * @param list
     * @param user
     * @return
     */
    public List<Competition> delCompetitionFormListByDeptId(List<Competition> list,User user) {
        List<Dept> deptList=deptService.selectAllDeptByIds(user.getDeptId());
        list.removeIf(i->{
            for(Dept dept: deptList) {
                for(Limit limit: i.getLimits()) {
                    if( dept.getDeptId().equals(limit.getDeptid()) ){
                        return false;
                    }
                }
            }
           return true;
        });
        return list;
    }

    /**
     * 当前user可参加的competition列表
     *
     * @return 所有符合条件的competition
     *
     */
    public List<Map<String,Object>> selectCompetitionTree(User user) {
        List<Competition> list=selectCompetitionListOpen();
        delCompetitionFormListNotStart(list);
        delCompetitionFormListByDeptId(list,user);
        ArrayList<Map<String,Object>> trees=new ArrayList<>(list.size());
        trees=getTrees(list,false,null);
        return trees;
    }

    private ArrayList<Map<String ,Object>> getTrees(List<Competition> competitionList,boolean isCheck,List<String> roleDeptList){
        ArrayList<Map<String,Object>> trees=new ArrayList<>(competitionList.size());
        // 标记是否有选中的,  没有则默认第一个为选中状态
        int flag=0;
//        初始化三个 根级目录
        HashMap<String,Object> before=new HashMap<>(5); before.put("pId",0L); before.put("checked",false);
        HashMap<String,Object> running=new HashMap<>(5); running.put("pId",0L); running.put("checked",false);
        HashMap<String,Object> after=new HashMap<>(5); after.put("pId",0L); after.put("checked",false);
        trees.add(running);trees.add(before);trees.add(after);
        before.put("name","未开始");
        before.put("title","未开始");
        running.put("name","进行中");
        running.put("title","进行中");
        after.put("name","已结束");
        after.put("title","已结束");
        long beforeId=0L,runningId=0L,afterId=0L;
        for(int i=0;i<competitionList.size();i++) {
            Competition competition=competitionList.get(i);
            int timeFlag=DateUtils.isBetween(competition.getStartTime(),competition.getEndTime());
            Long pId =0L;
            if( timeFlag < 0 ){
                if( beforeId == 0 ){
                    beforeId = -competition.getId();
                    before.put("id",-competition.getId());

                }
                pId = beforeId;
            }else if( timeFlag == 0 ){
                if( runningId == 0 ){
                    runningId = -competition.getId();
                    running.put("id",-competition.getId());
                }
                pId = runningId;
            }else if( timeFlag >0 ){
                if(afterId == 0 ){
                    afterId = -competition.getId();
                    after.put("id",-competition.getId());
                }
                pId =afterId;
            }
            HashMap<String,Object> competitionMap=new HashMap<>(5);
            competitionMap.put("id",competition.getId());
            competitionMap.put("pId",pId);
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
        if(trees.size()==0){
           return  trees;
        }
        if( flag==0 ){
            trees.get(0).put("checked",true);
        }
        return trees;
    }

    /**
     * 加入队伍
     * @param groupinfoId
     * @param leaderid
     * @param userId
     * @return
     *          0  成功
     *          1 已经加入,请先退出
     *          2 人数已满
     *          3 未知异常
     */
    @Transactional
    public int addteam(Long groupinfoId,Long leaderid,Long userId,Long competitionid,Long groupid) {
        // 检查是否已经加入其他组
        if( checkHasJoinCompetition(competitionid,userId) ){
            return 1;
        }
        // 检查该组人数是否达到上限
        Integer num=groupinfoMapper.remainTeamNum(leaderid,groupid);
        if(num==null) {
            return 3;
        }
        if( num <=0 ){
            return 2;
        }
        Groupinfo groupinfo=new Groupinfo().setUid(userId).setLeaderid(leaderid).setGroupid(groupid);
        groupinfoMapper.insertGroupinfo(groupinfo);
        competitionMapper.increaseCompetitionNum(competitionid);
        return 0;
    }

    public boolean checkHasJoinCompetition(Long competitionid,Long userId){
        return competitionMapper.checkHasJoinCompetition(competitionid,userId) > 0;
    }

    /**
     * 队长职务从 userId  到uid
     * @param groupId groupId
     * @param userId  老user
     * @param uid 新 user
     */
    @Transactional
    public void steppedDown(Long groupId,Long userId,Long uid) {
        List<Groupinfo> groupinfos=groupinfoMapper.selectGroupinfoList(new Groupinfo().setGroupid(groupId).setLeaderid(userId));
        for(Groupinfo groupinfo: groupinfos) {
            groupinfoMapper.changeLeaderId(groupinfo.getId(),uid);
        }
    }
}
