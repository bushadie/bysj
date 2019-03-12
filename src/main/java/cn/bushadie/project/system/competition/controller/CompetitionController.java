package cn.bushadie.project.system.competition.controller;

import cn.bushadie.common.support.Convert;
import cn.bushadie.common.utils.StringUtils;
import cn.bushadie.common.utils.poi.ExcelUtil;
import cn.bushadie.common.utils.security.ShiroUtils;
import cn.bushadie.framework.aspectj.lang.annotation.Log;
import cn.bushadie.framework.aspectj.lang.enums.BusinessType;
import cn.bushadie.framework.web.controller.BaseController;
import cn.bushadie.framework.web.domain.AjaxResult;
import cn.bushadie.framework.web.page.TableDataInfo;
import cn.bushadie.project.system.competition.domain.Competition;
import cn.bushadie.project.system.competition.domain.Group;
import cn.bushadie.project.system.competition.domain.Info;
import cn.bushadie.project.system.competition.mapper.LimitMapper;
import cn.bushadie.project.system.competition.service.CompetitionService;
import cn.bushadie.project.system.competition.service.LimitService;
import cn.bushadie.project.system.dept.domain.Dept;
import cn.bushadie.project.system.dept.service.DeptServiceImpl;
import cn.bushadie.project.system.user.domain.User;
import lombok.Data;
import org.apache.shiro.authz.annotation.Logical;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.apache.shiro.authz.annotation.RequiresRoles;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.List;

/**
 * 竞赛 信息操作处理
 *
 * @author jdmy
 * @date 2018-12-21
 */
@Controller
@RequestMapping("/system/competition")
public class CompetitionController extends BaseController {
    private String prefix="system/competition";

    @Autowired
    private CompetitionService competitionService;
    @Autowired
    private DeptServiceImpl deptService;
    @Autowired
    private LimitService limitService;

    @RequiresPermissions("system:competition:view")
    @GetMapping()
    public String competition() {
        return prefix+"/competition";
    }

    /**
     * 查询竞赛列表
     */
    @RequiresPermissions("system:competition:list")
    @PostMapping("/list")
    @ResponseBody
    public TableDataInfo list(DataVo dataVo) {
        Competition competition=dataVo.getInstance();
        List<Competition> list=competitionService.selectCompetitionList(competition);
        // 根据姓名查找时需要
        if(StringUtils.isNotEmpty(dataVo.getUsername())){
            competitionService.delCompetitionFormListByName(list,dataVo.getUsername());
        }
        // 如果是教师则只能呢查看自己的事务   若果是管理员则全部可以查看
        if( competitionService.isOnlyTeacher(competition.getUid())){
            competitionService.delCompetitionFromListByUid(list,competition.getUid());
        }

        list=competitionService.sortList(list,dataVo.orderByColumn,dataVo.isAsc);
        list=competitionService.delCompetitionFormListByPage(list,dataVo.pageSize,dataVo.pageNum);
        return getDataTable(list);
    }


    /**
     * 导出竞赛列表
     */
    @RequiresPermissions("system:competition:export")
    @PostMapping("/export")
    @ResponseBody
    public AjaxResult export(Competition competition) {
        List<Competition> list=competitionService.selectCompetitionList(competition);
        ExcelUtil<Competition> util=new ExcelUtil<Competition>(Competition.class);
        return util.exportExcel(list,"competition");
    }

    /**
     * 新增竞赛
     */
    @RequiresPermissions("system:competition:add")
    @GetMapping("/add")
    public String add(ModelMap mmap) {
        mmap.put("depts",deptService.selectDeptList(new Dept()));
        return prefix+"/add";
    }

    /**
     * 新增保存竞赛
     */
    @RequiresPermissions("system:competition:add")
    @Log(title="事务", businessType=BusinessType.INSERT)
    @PostMapping("/add")
    @ResponseBody
    public AjaxResult addSave(DataVo data) {
        data.getInstance();
        int result=competitionService.insertCompetition(data.getInstance(),data.getPosts());
        return toAjax(result);
    }

    /**
     * 修改竞赛
     */
    @GetMapping("/edit/{id}")
    public String edit(@PathVariable("id") Long id,ModelMap mmap) {
        Competition competition=competitionService.selectCompetitionById(id);
        mmap.put("competition",competition);
        mmap.put("depts",deptService.selectDeptList(new Dept()));
        return prefix+"/edit";
    }

    /**
     * 修改保存竞赛
     */
    @RequiresPermissions("system:competition:edit")
    @Log(title="竞赛", businessType=BusinessType.UPDATE)
    @PostMapping("/edit")
    @ResponseBody
    public AjaxResult editSave(DataVo data) {
        Competition competition=data.getInstance();
        return toAjax(competitionService.updateCompetition(competition,data.posts));
    }

    /**
     * 删除竞赛
     */
    @RequiresPermissions("system:competition:remove")
    @Log(title="竞赛", businessType=BusinessType.DELETE)
    @PostMapping("/remove")
    @ResponseBody
    public AjaxResult remove(String ids) {
        return toAjax(competitionService.deleteCompetitionByIds(ids));
    }

    @RequiresPermissions("system:competition:list")
    @RequiresRoles(value={"admin","teacher"},logical=Logical.OR)
    @GetMapping("/detail/{competitionId}")
    public String detail(@PathVariable("competitionId") Long competitionId,ModelMap mmap){
        Competition competition=competitionService.selectCompetitionById(competitionId);
        mmap.put("competition",competition);
        return "system/competition/data/detail";
    }


    @Data
    private class DataVo {
        private Long id;
        private Date startTime, endTime;
        private String k, v, min, max, groupNum,title,username,signUpStatus,post;
        private String[] ks, vs, mins, maxs, groupNums,posts;
        private Competition competition =null;
        private Integer pageNum,pageSize;
        private String orderByColumn,isAsc;
        private Competition getInstance() {
            if(competition!=null) {
                return competition;
            }
            competition = new Competition();
            competition.setSignUpStatus(signUpStatus);
            ks=Convert.toStrArray(k);
            vs=Convert.toStrArray(v);
            posts=Convert.toStrArray(post);
            mins=Convert.toStrArray(min);
            maxs=Convert.toStrArray(max);
            groupNums=Convert.toStrArray(groupNum);

            competition.setId(id).setTitle(title)
                    .setStartTime(startTime).setEndTime(endTime);
            for(int i=0;i<ks.length;i++) {
                Info info=new Info().setK(ks[i]).setV(vs[i]).setCompetitionid(id);
                competition.getInfos().add(info);
            }
            for(int i=0;i<mins.length;i++) {
                Group group=new Group().setLeastString(mins[i]).setMostString(maxs[i]).setNumString(groupNums[i]).setCompetitionid(id);
                competition.getGroups().add(group);
            }

            User user=ShiroUtils.getSysUser();
            competition.setUid(Math.toIntExact(user.getUserId()));
            return competition;
        }
    }
}
