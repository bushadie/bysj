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
    private Long id;
    private Long competitionid;
    /**
     * 最少人数
     */
    private Long least;
    /**
     * 最多人数
     */
    private Long most;
    /**
     * 组数量
     */
    private Long num;
    /**
     * 现有组数量
     */
    private Long nowNum=0L;
    /**
     * 是否满足条件
     */
    private Long accept=0L;

    private List<Groupinfo> groupinfos=new ArrayList<>();

    public Group() {
    }

    public Group(String  least,String  most,String  num) {
        this.least=Long.valueOf(least);
        this.most=Long.valueOf(most);
        this.num=Long.valueOf(num);
    }

    public Group setLeastString(String least){
        this.least = Long.valueOf(least);
        return this;
    }
    public Group setMostString(String most){
        this.most = Long.valueOf(most);
        return this;
    }
    public Group setNumString(String num){
        this.num = Long.valueOf(num);
        return this;
    }

}
