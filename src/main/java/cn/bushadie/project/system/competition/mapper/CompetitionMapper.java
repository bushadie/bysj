package cn.bushadie.project.system.competition.mapper;

import cn.bushadie.project.system.competition.domain.Competition;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * 竞赛 数据层
 *
 * @author jdmy
 * @date 2018-12-21
 */
@Repository
public interface CompetitionMapper {
    /**
     * 查询竞赛信息
     *
     * @param id 竞赛ID
     * @return 竞赛信息
     */
    public Competition selectCompetitionById(Integer id);

    /**
     * 查询竞赛列表
     *
     * @param competition 竞赛信息
     * @return 竞赛集合
     */
    public List<Competition> selectCompetitionList(Competition competition);

    /**
     * 新增竞赛
     *
     * @param competition 竞赛信息
     * @return 结果
     */
    public int insertCompetition(Competition competition);

    /**
     * 修改竞赛
     *
     * @param competition 竞赛信息
     * @return 结果
     */
    public int updateCompetition(Competition competition);

    /**
     * 删除竞赛
     *
     * @param id 竞赛ID
     * @return 结果
     */
    public int deleteCompetitionById(Integer id);

    /**
     * 批量删除竞赛
     *
     * @param ids 需要删除的数据ID
     * @return 结果
     */
    public int deleteCompetitionByIds(String[] ids);

}