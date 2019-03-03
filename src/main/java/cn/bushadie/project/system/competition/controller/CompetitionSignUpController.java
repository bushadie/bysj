package cn.bushadie.project.system.competition.controller;

import cn.bushadie.common.support.Convert;
import cn.bushadie.common.utils.StringUtils;
import cn.bushadie.common.utils.security.ShiroUtils;
import cn.bushadie.framework.web.controller.BaseController;
import cn.bushadie.framework.web.domain.AjaxResult;
import cn.bushadie.framework.web.page.TableDataInfo;
import cn.bushadie.project.system.competition.domain.Competition;
import cn.bushadie.project.system.competition.domain.Group;
import cn.bushadie.project.system.competition.domain.Info;
import cn.bushadie.project.system.competition.service.CompetitionService;
import cn.bushadie.project.system.user.domain.User;
import lombok.Data;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import static com.github.pagehelper.page.PageMethod.startPage;

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
        startPage();
        Competition competition=dataVo.getInstance();
        List<Competition> list=competitionService.selectCompetitionList(competition);
        // 根据姓名查找时需要
        if(StringUtils.isNotEmpty(dataVo.getUsername())){
            competitionService.delCompetitionFormListByName(list,dataVo.getUsername());
        }
//        ArrayList<Competition> result = new ArrayList<>(1);
//        result.add(list.get(0));
        return getDataTable(list);
    }

    @PostMapping("/competitionInfo")
    @ResponseBody
    public AjaxResult competitionInfo(Long id){
        AjaxResult result=new AjaxResult();
        result.put("competition",competitionService.selectCompetitionById(id));
        return result;
    }

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
    @GetMapping("/treeData")
    @ResponseBody
    public List<Map<String,Object>> treeData() {
        List<Map<String,Object>> tree=competitionService.selectCompetitionTree();
        return tree;
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
}
