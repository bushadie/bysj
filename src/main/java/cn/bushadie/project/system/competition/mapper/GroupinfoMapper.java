package cn.bushadie.project.system.competition.mapper;

import cn.bushadie.project.system.competition.domain.Groupinfo;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * 每组人员 数据层
 *
 * @author jdmy
 * @date 2019-01-12
 */
@Repository
public interface GroupinfoMapper {
    /**
     * 查询每组人员信息
     *
     * @param id 每组人员ID
     * @return 每组人员信息
     */
    public Groupinfo selectGroupinfoById(Long id);

    /**
     * 查询每组人员列表
     *
     * @param groupinfo 每组人员信息
     * @return 每组人员集合
     */
    public List<Groupinfo> selectGroupinfoList(Groupinfo groupinfo);

    /**
     * 新增每组人员
     *
     * @param groupinfo 每组人员信息
     * @return 结果
     */
    public int insertGroupinfo(Groupinfo groupinfo);

    /**
     * 修改每组人员
     *
     * @param groupinfo 每组人员信息
     * @return 结果
     */
    public int updateGroupinfo(Groupinfo groupinfo);

    /**
     * 删除每组人员
     *
     * @param id 每组人员ID
     * @return 结果
     */
    public int deleteGroupinfoById(Long id);

    /**
     * 批量删除每组人员
     *
     * @param ids 需要删除的数据ID
     * @return 结果
     */
    public int deleteGroupinfoByIds(String[] ids);


    /**
     * 把user 从队伍中删除
     * @param groupId
     * @param userId
     * @return
     */
    public int deleteGroupinfoByGroupIdAndUserId(@Param("groupId") Long groupId,@Param("userId") Long userId);

    public Long countMemberNumber(Long groupId);

    /**
     * 统计该组中还剩多少名额
     * @param leaderid  队长id
     * @param groupid 队组id
     * @return 剩余数量
     */
    public Integer remainTeamNum(@Param("leaderid") Long leaderid,@Param("groupid") Long groupid);
}