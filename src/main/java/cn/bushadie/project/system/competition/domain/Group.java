package cn.bushadie.project.system.competition.domain;

import cn.bushadie.framework.web.domain.BaseEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.util.ArrayList;
import java.util.List;

/**
 * 组数 表 com_group
 *
 * @author jdmy
 * @date 2019-01-12
 */
@EqualsAndHashCode(callSuper=true)
@Data
@Accessors(chain=true)
public class Group extends BaseEntity {
    private static final long serialVersionUID=1L;

    /**
     *
     */
    private Integer id;
    private Integer competitionid;
    /**
     * 最少人数
     */
    private Integer least;
    /**
     * 最多人数
     */
    private Integer most;
    /**
     * 组数量
     */
    private Integer num;
    /**
     * 现有组数量
     */
    private Integer nowNum;
    /**
     * 是否满足条件
     */
    private Integer accept;

    private List<Groupinfo> groupinfos=new ArrayList<>();

    public Group() {
        accept=0;
        nowNum=0;
    }

    public Group(String  least,String  most,String  num) {
        Group group=new Group();
        group.least=Integer.parseInt(least);
        group.most=Integer.parseInt(most);
        group.num=Integer.parseInt(num);
    }
}
