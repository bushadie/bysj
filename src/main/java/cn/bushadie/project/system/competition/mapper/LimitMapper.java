package cn.bushadie.project.system.competition.mapper;

import cn.bushadie.project.system.competition.domain.Limit;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * 条件限制 数据层
 *
 * @author jdmy
 * @date 2019-03-05
 */
@Repository
public interface LimitMapper {
    /**
     * 查询条件限制信息
     *
     * @param limitId 条件限制ID
     * @return 条件限制信息
     */
    public Limit selectLimitById(Long limitId);

    /**
     * 查询条件限制信息
     * @param competitionid  competitionid条件限制ID
     * @return条件限制信息
     */
    public List<Limit> selectLimitByCompetitionId(Long competitionid);

    /**
     * 查询条件限制列表
     *
     * @param limit 条件限制信息
     * @return 条件限制集合
     */
    public List<Limit> selectLimitList(Limit limit);

    /**
     * 新增条件限制
     *
     * @param limit 条件限制信息
     * @return 结果
     */
    public int insertLimit(Limit limit);

    /**
     * 修改条件限制
     *
     * @param limit 条件限制信息
     * @return 结果
     */
    public int updateLimit(Limit limit);

    /**
     * 删除条件限制
     *
     * @param limitId 条件限制ID
     * @return 结果
     */
    public int deleteLimitById(Long limitId);

    /**
     * 删除条件限制
     * @param competitionid 条件限制ID
     * @return 结果
     */
    public int deleteLimitByCompetitionId(Long competitionid);

    /**
     * 批量删除条件限制
     *
     * @param limitIds 需要删除的数据ID
     * @return 结果
     */
    public int deleteLimitByIds(String[] limitIds);

}