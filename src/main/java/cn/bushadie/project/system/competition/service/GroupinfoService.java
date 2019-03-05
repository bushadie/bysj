package cn.bushadie.project.system.competition.service;

import cn.bushadie.common.support.Convert;
import cn.bushadie.project.system.competition.domain.Groupinfo;
import cn.bushadie.project.system.competition.mapper.GroupinfoMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 每组人员 服务层实现
 *
 * @author jdmy
 * @date 2019-01-12
 */
@Service
public class GroupinfoService {
    @Autowired
    private GroupinfoMapper groupinfoMapper;

    /**
     * 查询每组人员信息
     *
     * @param id 每组人员ID
     * @return 每组人员信息
     */
    public Groupinfo selectGroupinfoById(Long id) {
        return groupinfoMapper.selectGroupinfoById(id);
    }

    /**
     * 查询每组人员列表
     *
     * @param groupinfo 每组人员信息
     * @return 每组人员集合
     */
    public List<Groupinfo> selectGroupinfoList(Groupinfo groupinfo) {
        return groupinfoMapper.selectGroupinfoList(groupinfo);
    }

    /**
     * 新增每组人员
     *
     * @param groupinfo 每组人员信息
     * @return 结果
     */
    public int insertGroupinfo(Groupinfo groupinfo) {
        return groupinfoMapper.insertGroupinfo(groupinfo);
    }

    /**
     * 修改每组人员
     *
     * @param groupinfo 每组人员信息
     * @return 结果
     */
    public int updateGroupinfo(Groupinfo groupinfo) {
        return groupinfoMapper.updateGroupinfo(groupinfo);
    }

    /**
     * 删除每组人员对象
     *
     * @param ids 需要删除的数据ID
     * @return 结果
     */
    public int deleteGroupinfoByIds(String ids) {
        return groupinfoMapper.deleteGroupinfoByIds(Convert.toStrArray(ids));
    }

    /**
     * 统计该组中有多少人
     *
     * @param groupid groupid
     * @return 结果
     */
    public Long countMemberNumber(Long groupid){
        return groupinfoMapper.countMemberNumber(groupid);
    }
    /**
     * 统计该组中还剩多少名额
     * @param leaderid  队长id
     * @param groupid 队组id
     * @return 剩余数量
     */
    public int remainTeamNum(Long groupid,Long leaderid){
        return groupinfoMapper.remainTeamNum(groupid,leaderid);
    }

    public void quitTeam(Long groupinfoId) {
        groupinfoMapper.deleteGroupinfoById(groupinfoId);
    }
}
