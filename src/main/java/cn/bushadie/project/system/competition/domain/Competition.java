package cn.bushadie.project.system.competition.domain;


import cn.bushadie.common.utils.DateUtils;
import cn.bushadie.framework.aspectj.lang.annotation.Excel;
import cn.bushadie.framework.web.domain.BaseEntity;
import cn.bushadie.project.system.user.domain.User;
import cn.hutool.core.date.DateUtil;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;
import org.apache.commons.lang3.time.DateFormatUtils;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
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
     */
    @Excel(name="报名状态" ,readConverterExp="0=关闭报名,1=正常开放")
    private String signUpStatus;
    private List<Info> infos=new ArrayList<>();

    private List<Group> groups=new ArrayList<>();

    public String  simpleStartTime(){
        try {
            return DateUtils.getyyyy_mm_dd(startTime);
        }catch(Exception e) {
            return "";
        }
    }
    public String  simpleEndTime(){
        try {
            return DateUtils.getyyyy_mm_dd(endTime);
        }catch(Exception e) {
            return "";
        }
    }
}
