package cn.bushadie.project.system.competition.domain;


import cn.bushadie.common.utils.DateUtils;
import cn.bushadie.framework.web.domain.BaseEntity;
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
    private Integer id;
    /** 事务名称 */
    private String title;
    /**
     * 负责人id
     */
    private Integer uid;
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
    private Integer num;

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
