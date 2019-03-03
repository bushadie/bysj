package cn.bushadie.project.system.competition.domain;

import cn.bushadie.framework.web.domain.BaseEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * 每组人员表 com_groupinfo
 *
 * @author jdmy
 * @date 2019-01-12
 */
@Data
@Accessors(chain=true)
public class Groupinfo {
    private static final long serialVersionUID=1L;

    /**
     *
     */
    private Long id;
    /**
     * 属于哪个组表
     */
    private Long groupid;
    /**
     * 人员id
     */
    private Long uid;
    /**
     * 是否组长
     */
    private Long leaderid;

}
