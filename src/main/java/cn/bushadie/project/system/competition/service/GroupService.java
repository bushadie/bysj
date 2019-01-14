package cn.bushadie.project.system.competition.service;

import cn.bushadie.common.support.Convert;
import cn.bushadie.project.system.competition.domain.Group;
import cn.bushadie.project.system.competition.mapper.GroupMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 组数  服务层实现
 *
 * @author ruoyi
 * @date 2019-01-12
 */
@Service
public class GroupService {
    @Autowired
    private GroupMapper groupMapper;

    /**
     * 查询组数 信息
     *
     * @param id 组数 ID
     * @return 组数 信息
     */
    public Group selectGroupById(Integer id) {
        return groupMapper.selectGroupById(id);
    }

    /**
     * 查询组数 列表
     *
     * @param group 组数 信息
     * @return 组数 集合
     */
    public List<Group> selectGroupList(Group group) {
        return groupMapper.selectGroupList(group);
    }

    /**
     * 新增组数 
     *
     * @param group 组数 信息
     * @return 结果
     */
    public int insertGroup(Group group) {
        return groupMapper.insertGroup(group);
    }

    /**
     * 修改组数 
     *
     * @param group 组数 信息
     * @return 结果
     */
    public int updateGroup(Group group) {
        return groupMapper.updateGroup(group);
    }

    /**
     * 删除组数 对象
     *
     * @param ids 需要删除的数据ID
     * @return 结果
     */
    public int deleteGroupByIds(String ids) {
        return groupMapper.deleteGroupByIds(Convert.toStrArray(ids));
    }

}
