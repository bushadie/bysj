package cn.bushadie.project.system.competition.domain;

import cn.bushadie.framework.web.domain.BaseEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * 条件限制表 com_limit
 *
 * @author jdmy
 * @date 2019-03-05
 */
@Data
@Accessors(chain=true)
public class Limit {
    private static final long serialVersionUID=1L;

    /**
     *
     */
    private Long id;
    /**
     * 所属竞赛id
     */
    private Long competitionid;
    /**
     * 部门id
     */
    private Long deptid;

}
