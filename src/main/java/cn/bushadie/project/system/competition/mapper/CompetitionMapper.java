package cn.bushadie.project.system.competition.mapper;

import cn.bushadie.project.system.competition.domain.Competition;
import org.apache.ibatis.annotations.Param;
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
    public Competition selectCompetitionById(Long id);

    /**
     * 查询竞赛列表
     *
     * @param competition 竞赛信息
     * @return 竞赛集合
     */
    public List<Competition> selectCompetitionList(Competition competition);

    /**
     * 查询 开放的竞赛列表 (signUpStatus=1 正常开放     0 关闭)
     * @return
     */
    public List<Competition> selectCompetitionListOpen();

    public void decreaseCompetitionNum(Long id);
    public void increaseCompetitionNum(Long id);

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
     * 设置competition为未开始状态
     * @param id  competitionid
     */
    public void setCompetitionEarly(Long id);

    /**
     * 设置competition为未结束状态
     * @param id  competitionid
     */
    public void setCompetitionLate(Long id);
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


    /**
     * 判断是否已经加入了这个这个 competition
     * @param competitionId
     * @param userId
     * @return
     */
    int checkHasJoinCompetition(@Param("competitionId") Long competitionId,@Param("userId") Long userId);
}