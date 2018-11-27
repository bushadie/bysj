package cn.bushadie.project.monitor.job.controller;

import java.util.List;

import cn.bushadie.common.utils.poi.ExcelUtil;
import cn.bushadie.project.monitor.job.domain.JobLog;
import cn.bushadie.project.monitor.job.service.IJobLogService;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import cn.bushadie.framework.aspectj.lang.annotation.Log;
import cn.bushadie.framework.aspectj.lang.enums.BusinessType;
import cn.bushadie.framework.web.controller.BaseController;
import cn.bushadie.framework.web.domain.AjaxResult;
import cn.bushadie.framework.web.page.TableDataInfo;

/**
 * 调度日志操作处理
 *
 * @author ruoyi
 */
@Controller
@RequestMapping("/monitor/jobLog")
public class JobLogController extends BaseController {
    private String prefix="monitor/job" ;

    @Autowired
    private IJobLogService jobLogService;

    @RequiresPermissions("monitor:job:view")
    @GetMapping()
    public String jobLog() {
        return prefix+"/jobLog" ;
    }

    @RequiresPermissions("monitor:job:list")
    @PostMapping("/list")
    @ResponseBody
    public TableDataInfo list(JobLog jobLog) {
        startPage();
        List<JobLog> list=jobLogService.selectJobLogList(jobLog);
        return getDataTable(list);
    }

    @Log(title="调度日志" , businessType=BusinessType.EXPORT)
    @RequiresPermissions("monitor:job:export")
    @PostMapping("/export")
    @ResponseBody
    public AjaxResult export(JobLog jobLog) {
        List<JobLog> list=jobLogService.selectJobLogList(jobLog);
        ExcelUtil<JobLog> util=new ExcelUtil<JobLog>(JobLog.class);
        return util.exportExcel(list,"jobLog");
    }

    @Log(title="调度日志" , businessType=BusinessType.DELETE)
    @RequiresPermissions("monitor:job:remove")
    @PostMapping("/remove")
    @ResponseBody
    public AjaxResult remove(String ids) {
        return toAjax(jobLogService.deleteJobLogByIds(ids));
    }

    @RequiresPermissions("monitor:job:detail")
    @GetMapping("/detail/{jobLogId}")
    public String detail(@PathVariable("jobLogId") Long jobLogId,ModelMap mmap) {
        mmap.put("jobLog" ,jobLogService.selectJobLogById(jobLogId));
        return prefix+"/detail" ;
    }

    @Log(title="调度日志" , businessType=BusinessType.CLEAN)
    @RequiresPermissions("monitor:job:remove")
    @PostMapping("/clean")
    @ResponseBody
    public AjaxResult clean() {
        jobLogService.cleanJobLog();
        return success();
    }
}
