package cn.bushadie.project.system.competition.domain;


import cn.bushadie.common.utils.DateUtils;
import cn.bushadie.framework.aspectj.lang.annotation.Excel;
import cn.bushadie.project.system.user.domain.User;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import lombok.experimental.Accessors;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * 竞赛表 com_competition
 *
 * @author jdmy
 * @date 2018-12-21
 */
@Data
@Accessors(chain=true)
public class Competition {
    private static final long serialVersionUID=1L;

    /**
     *
     */
    private Long id;
    /** 事务名称 */
    private String title;
    /**
     * 负责人id
     */
    private long uid;
    /**
     * 创建者信息
     */
    private User user;
    /**
     * 开始时间
     */
    @JsonFormat(pattern="yyyy-MM-dd")
    private Date startTime;
    /**
     * 报名结束时间
     */
    @JsonFormat(pattern="yyyy-MM-dd")
    private Date endTime;
    /**
     * 总报名人数
     */
    private Long num;

    /**
     * 报名状态
     * 0=关闭报名,1=正常开放,2=还未开放,3=已经结束
     */
    @Excel(name="报名状态" ,readConverterExp="0=关闭报名,1=正常开放,2=还未开放,3=已经结束")
    private String signUpStatus;
    private List<Info> infos=new ArrayList<>();

    private List<Group> groups=new ArrayList<>();

    private List<Limit> limits=new ArrayList<>();

    public String  simpleStartTime(){
        try {
            return DateUtils.getDateTime(startTime);
        }catch(Exception e) {
            return "";
        }
    }
    public String  simpleEndTime(){
        try {
            return DateUtils.getDateTime(endTime);
        }catch(Exception e) {
            return "";
        }
    }
    public boolean haveDept(Long deptId){
        for(Limit limit: limits) {
            if( limit.getDeptid().equals(deptId) ){
                return true;
            }
        }
        return false;
    }
    public String getUserName(){
        return user.getUserName();
    }
}
