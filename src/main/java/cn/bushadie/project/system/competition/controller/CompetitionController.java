package cn.bushadie.project.system.competition.controller;

import cn.bushadie.common.support.Convert;
import cn.bushadie.common.utils.poi.ExcelUtil;
import cn.bushadie.framework.aspectj.lang.annotation.Log;
import cn.bushadie.framework.aspectj.lang.enums.BusinessType;
import cn.bushadie.framework.web.controller.BaseController;
import cn.bushadie.framework.web.domain.AjaxResult;
import cn.bushadie.framework.web.page.TableDataInfo;
import cn.bushadie.project.system.competition.domain.Competition;
import cn.bushadie.project.system.competition.domain.Group;
import cn.bushadie.project.system.competition.domain.Info;
import cn.bushadie.project.system.competition.service.CompetitionService;
import lombok.Data;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
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
    public TableDataInfo list(Competition competition) {
        startPage();
        List<Competition> list=competitionService.selectCompetitionList(competition);
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
    @GetMapping("/add")
    public String add() {
        return prefix+"/add";
    }

    /**
     * 新增保存竞赛
     */
    @RequiresPermissions("system:competition:add")
    @Log(title="事务", businessType=BusinessType.INSERT)
    @PostMapping("/add")
    @ResponseBody
    public AjaxResult addSave(Competition competition,String k,String v,String min,String max,String groupNum,DataVo data) {
//        String[] ks=Convert.toStrArray(k);
//        String[] vs=Convert.toStrArray(v);
//        for(int i=0;i<ks.length;i++) {
//            Info info=new Info();
//            info.setK(ks[i]).setV(vs[i]);
//            competition.getInfos().add(info);
//        }
//
//        String[] mins=Convert.toStrArray(min);
//        String[] maxs=Convert.toStrArray(max);
//        String[] groupNums=Convert.toStrArray(groupNum);
//        for(int i=0;i<mins.length;i++) {
//            Group group=new Group(mins[i],maxs[i],groupNums[i]);
//            competition.getGroups().add(group);
//        }
        data.getInstance();
        int result=competitionService.insertCompetition(data.getInstance());
        return toAjax(result);
    }

    /**
     * 修改竞赛
     */
    @GetMapping("/edit/{id}")
    public String edit(@PathVariable("id") Integer id,ModelMap mmap) {
        Competition competition=competitionService.selectCompetitionById(id);
        mmap.put("competition",competition);
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
        return toAjax(competitionService.updateCompetition(competition));
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

    @Data
    private class DataVo {
        private Integer id, num;
        private Date startTime, endTime;
        private String k, v, min, max, groupNum,title;
        private String[] ks, vs, mins, maxs, groupNums;
        private Competition competition =null;
        private Competition getInstance() {
            check();
            if(competition!=null) {
                return competition;
            }
            competition = new Competition();
            ks=Convert.toStrArray(k);
            vs=Convert.toStrArray(v);
            mins=Convert.toStrArray(min);
            maxs=Convert.toStrArray(max);
            groupNums=Convert.toStrArray(groupNum);

            competition.setId(id).setTitle(title).setNum(num)
                    .setStartTime(startTime).setEndTime(endTime);
            for(int i=0;i<ks.length;i++) {
                Info info=new Info().setK(ks[i]).setV(vs[i]).setCompetitionid(id);
                competition.getInfos().add(info);
            }
            for(int i=0;i<mins.length;i++) {
                Group group=new Group().setLeastString(mins[i]).setMostString(maxs[i]).setNumString(groupNums[i]).setCompetitionid(id);
                competition.getGroups().add(group);
            }
            return competition;
        }

        private void check(){
            if(num==null) {
                num=0;
            }
        }
    }


}
