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
    private Integer nowNum=0;
    /**
     * 是否满足条件
     */
    private Integer accept=0;

    private List<Groupinfo> groupinfos=new ArrayList<>();

    public Group() {
    }

    public Group(String  least,String  most,String  num) {
        this.least=Integer.parseInt(least);
        this.most=Integer.parseInt(most);
        this.num=Integer.parseInt(num);
    }

    public Group setLeastString(String least){
        this.least = Integer.valueOf(least);
        return this;
    }
    public Group setMostString(String most){
        this.most = Integer.valueOf(most);
        return this;
    }
    public Group setNumString(String num){
        this.num = Integer.valueOf(num);
        return this;
    }

}
