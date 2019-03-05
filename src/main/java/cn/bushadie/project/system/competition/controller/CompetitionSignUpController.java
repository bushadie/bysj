package cn.bushadie.project.system.competition.controller;

import cn.bushadie.common.utils.StringUtils;
import cn.bushadie.framework.web.controller.BaseController;
import cn.bushadie.framework.web.domain.AjaxResult;
import cn.bushadie.framework.web.page.TableDataInfo;
import cn.bushadie.project.system.competition.domain.Competition;
import cn.bushadie.project.system.competition.domain.Group;
import cn.bushadie.project.system.competition.domain.Groupinfo;
import cn.bushadie.project.system.competition.service.CompetitionService;
import cn.bushadie.project.system.competition.service.GroupinfoService;
import cn.bushadie.project.system.user.domain.User;
import cn.bushadie.project.system.user.service.UserServiceImpl;
import lombok.Builder;
import lombok.Data;
import lombok.experimental.Accessors;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * @author jdmy
 * on 2019/2/18.
 **/
@Controller
@RequestMapping("/system/competitionSignUp")
public class CompetitionSignUpController extends BaseController {
    private String prefix="system/competition/data";

    @Autowired
    private CompetitionService competitionService;
    @Autowired
    private UserServiceImpl userService;
    @Autowired
    private GroupinfoService groupinfoService;

    @RequiresPermissions("system:competitionSignUp:view")
    @GetMapping()
    public String competition() {
        return prefix+"/competition";
    }

    /**
     * 查询竞赛列表
     */
    @RequiresPermissions("system:competitionSignUp:list")
    @PostMapping("/list")
    @ResponseBody
    public TableDataInfo list(DataVo dataVo) {
//    public List<TreeVo> list(DataVo dataVo) {
        startPage();
        Competition competition=dataVo.getInstance();
        List<Competition> list=competitionService.selectCompetitionList(competition);
        // 根据姓名查找时需要
        if(StringUtils.isNotEmpty(dataVo.getUsername())){
            competitionService.delCompetitionFormListByName(list,dataVo.getUsername());
        }
//        ArrayList<Competition> result = new ArrayList<>(1);
//        result.add(list.get(0));
        List<TreeVo> treeVoInstances=getTreeVoInstances(list.get(0).getGroups());
        TreeVo treeVo=new TreeVo().setUid(88888L).setLeaderid(0L);
//        treeVoInstances.add(treeVo);
        return getDataTable( treeVoInstances );
//        return treeVoInstances;
    }


    @RequiresPermissions("system:competitionSignUp:list")
    @GetMapping("/listfast/{competitionid}")
    @ResponseBody
    public List<TreeVo> listfast(@PathVariable("competitionid") Long competitionid) {
        //    public List<TreeVo> list(DataVo dataVo) {
        startPage();
        Competition competition=new Competition().setId(competitionid);
        List<Competition> list=competitionService.selectCompetitionList(competition);
        List<TreeVo> treeVoInstances=getTreeVoInstances(list.get(0).getGroups());
        return treeVoInstances;
    }

    @RequiresPermissions("system:competitionSignUp:view")
    @PostMapping("/competitionInfo")
    @ResponseBody
    public AjaxResult competitionInfo(Long id){
        AjaxResult result=new AjaxResult();
        result.put("competition",competitionService.selectCompetitionById(id));
        return result;
    }

    @RequiresPermissions("system:competitionSignUp:view")
    @PostMapping("/getuserid")
    @ResponseBody
    public Long getuserid(){
        return getUserId();
    }

    @RequiresPermissions("system:competitionSignUp:edit")
    @PostMapping("/createLeader")
    @ResponseBody
    public AjaxResult createLeader(Long groupId){
        int flag=competitionService.createLeader(groupId,getSysUser().getUserId());
        if( flag ==0 ){
            return success();
        }else if( flag==1 ){
            return error("组数已达到上限");
        }else if( flag==2 ){
            return error("您已经报名了,请先退出组");
        }else {
            return error();
        }
    }

    @RequiresPermissions("system:competitionSignUp:edit")
    @PostMapping("/leaveLeader")
    @ResponseBody
    public AjaxResult leaveLeader(Long groupId){
        int flag=competitionService.leaveLeader(groupId,getUserId());
        if( flag==0 ){
            return success();
        }
        return error("您不是队长");
    }

    /**
     * 加载竞赛列表
     */
    @RequiresPermissions("system:competitionSignUp:view")
    @GetMapping("/treeData")
    @ResponseBody
    public List<Map<String,Object>> treeData() {
        List<Map<String,Object>> tree=competitionService.selectCompetitionTree();
        return tree;
    }
    @RequiresPermissions("system:competitionSignUp:view")
    @PostMapping("/checkHasJoinCompetition")
    @ResponseBody
    public boolean checkHasJoinCompetition(Long competitionid){
        return competitionService.checkHasJoinCompetition(competitionid,getUserId());
    }

    @RequiresPermissions("system:competitionSignUp:edit")
    @PostMapping("/addteam")
    @ResponseBody
    public AjaxResult addteam(Long groupinfoId,Long leaderid,Long competitionid,Long groupid){
        //       0  成功
        //       1 已经加入,请先退出
        //       2 人数已满
        //       3  未知错误
        int flag=competitionService.addteam(groupinfoId,leaderid,getUserId(),competitionid,groupid);
        if( flag==1 ){
            return error("已经加入队伍,请先退出");
        }
        if( flag==2 ){
            return error("人数已满,请加入其他队伍");
        }
        if(flag==3){
            return error("未知错误");
        }
        return success();
    }

    @RequiresPermissions("system:competitionSignUp:edit")
    @PostMapping("/quitTeam")
    @ResponseBody
    public AjaxResult quitTeam(Long groupinfoid) {
        groupinfoService.quitTeam(groupinfoid);
        return success();
    }

    /**
     * 队伍类型菜单
     */
    @GetMapping("/tree/{competitionId}")
    public String tree(@PathVariable Long competitionId,ModelMap map){

        return "tree";
    }

    @Data
    private class DataVo {
        private String title,username;
        private Long competitionId;
        private Competition competition =null;
        private Competition getInstance() {
            if(competition!=null) {
                return competition;
            }
            competition = new Competition();
            competition.setId(competitionId).setTitle(title);
            return competition;
        }
    }

    @Data
    @Accessors(chain=true)
    private class TreeVo {
        private Long  least, most, nowNum, num,groupid,competitionid,uid,leaderid,groupinfoid;
        /**
         * 判断是否是组长, 是的话就是0, 不是的话就是leaderid
         */
        private Long leader;
        private String username,leaderName;
        public TreeVo(){}

    }
    public List<TreeVo>  getTreeVoInstances(List<Group> groups){
        int num=0;
        for(Group group: groups) {
            num += group.getGroupinfos().size();
        }
        ArrayList<TreeVo> treeVos=new ArrayList<>(num);
        for(Group group: groups) {
            for(Groupinfo groupinfo: group.getGroupinfos()) {
                TreeVo treeVo=new TreeVo();
                Long memberNumber=groupinfoService.countMemberNumber(group.getId());
                treeVo.setLeast(group.getLeast()).setMost(group.getMost())
                        .setGroupid(group.getId()).setCompetitionid(group.getCompetitionid())
                        .setUid(groupinfo.getUid()).setLeaderid(groupinfo.getLeaderid()).setGroupinfoid(groupinfo.getId())
                        .setUsername(userService.selectUserById(groupinfo.getUid()).getUserName())
                        .setLeaderName(userService.selectUserById(groupinfo.getLeaderid()).getUserName())
                        .setNowNum(memberNumber)
                        .setLeader(treeVo.getLeaderid().equals(treeVo.uid)?0:treeVo.getLeaderid());
                treeVos.add(treeVo);
            }
        }
        treeVos.sort((TreeVo a,TreeVo b)->a.getNowNum()<b.getNowNum()?-1:0);
        return treeVos;
    }
}
