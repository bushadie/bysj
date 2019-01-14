package cn.bushadie.project.system.competition.domain;

import lombok.Data;
import lombok.experimental.Accessors;

/**
 * 具体表 com_info
 *
 * @author jdmy
 * @date 2018-12-26
 */
@Data
@Accessors(chain=true)
public class Info  {
    private static final long serialVersionUID=1L;

    /**
     *
     */
    private Integer id;
    /**
     * 所属竞赛id
     */
    private Integer competitionid;
    /**
     *
     */
    private String k;
    /**
     *
     */
    private String v;

}
