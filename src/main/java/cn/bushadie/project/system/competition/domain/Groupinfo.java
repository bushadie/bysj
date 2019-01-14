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
@EqualsAndHashCode(callSuper=true)
@Data
@Accessors(chain=true)
public class Groupinfo extends BaseEntity {
    private static final long serialVersionUID=1L;

    /**
     *
     */
    private Integer id;
    /**
     * 属于哪个组表
     */
    private Integer groupid;
    /**
     * 人员id
     */
    private Integer uid;
    /**
     * 是否组长
     */
    private Integer leader;

}
