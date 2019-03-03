package cn.bushadie.project.system.competition.mapper;

import cn.bushadie.project.system.competition.domain.Group;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * 组数  数据层
 *
 * @author ruoyi
 * @date 2019-01-12
 */
@Repository
public interface GroupMapper {
    /**
     * 查询组数 信息
     *
     * @param id 组数 ID
     * @return 组数 信息
     */
    public Group selectGroupById(Long id);

    /**
     * @param id ID
     * @return 组数 信息
     */
    public int deleteGroupByCompetitionId(Long id);

    /**
     * 查询组数 列表
     *
     * @param group 组数 信息
     * @return 组数 集合
     */
    public List<Group> selectGroupList(Group group);

    /**
     * 新增组数 
     *
     * @param group 组数 信息
     * @return 结果
     */
    public int insertGroup(Group group);

    public int insertGroups(List<Group> groups);

    /**
     * 修改组数
     *
     * @param group 组数 信息
     * @return 结果
     */
    public int updateGroup(Group group);

    /**
     * 删除组数
     *
     * @param id 组数 ID
     * @return 结果
     */
    public int deleteGroupById(Integer id);

    /**
     * 批量删除组数
     *
     * @param ids 需要删除的数据ID
     * @return 结果
     */
    public int deleteGroupByIds(String[] ids);


    public void increaseGroupNum(@Param("groupId") Long groupId);

    public void decreaseGroupNum(@Param("groupId") Long groupId);
}