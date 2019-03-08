package cn.bushadie.project.system.competition.service;

import cn.bushadie.common.support.Convert;
import cn.bushadie.project.system.competition.domain.Limit;
import cn.bushadie.project.system.competition.mapper.LimitMapper;
import cn.bushadie.project.system.dept.domain.Dept;
import cn.bushadie.project.system.dept.service.DeptServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * 条件限制 服务层实现
 *
 * @author jdmy
 * @date 2019-03-05
 */
@Service
public class LimitService{
    @Autowired
    private LimitMapper limitMapper;
    @Autowired
    private DeptServiceImpl deptService;

    /**
     * 查询条件限制信息
     *
     * @param limitId 条件限制ID
     * @return 条件限制信息
     */
    public Limit selectLimitById(Long limitId) {
        return limitMapper.selectLimitById(limitId);
    }
    public List<Limit> selectLimitByCompetitionId(Long competitionid) {
        return limitMapper.selectLimitByCompetitionId(competitionid);
    }


    /**
     * 查询条件限制列表
     *
     * @param limit 条件限制信息
     * @return 条件限制集合
     */
    public List<Limit> selectLimitList(Limit limit) {
        return limitMapper.selectLimitList(limit);
    }

    /**
     * 新增条件限制
     *
     * @param limit 条件限制信息
     * @return 结果
     */
    public int insertLimit(Limit limit) {
        return limitMapper.insertLimit(limit);
    }

    /**
     * @param competitionid
     * @param deptIds 用逗号分割的deptid
     * @return
     */
    @Transactional
    public int insertLimit(Long competitionid,String[] deptIds){
        int result=0;
        List<Dept> deptList=deptService.selectAllDeptByIds(deptIds);
        for(Dept dept: deptList) {
            Limit limit=new Limit().setCompetitionid(competitionid).setDeptid(dept.getDeptId());
            result+=limitMapper.insertLimit(limit);
        }
        return result;
    }

    /**
     * 修改条件限制
     *
     * @param limit 条件限制信息
     * @return 结果
     */
    public int updateLimit(Limit limit) {
        return limitMapper.updateLimit(limit);
    }

    /**
     * 删除条件限制对象
     *
     * @param ids 需要删除的数据ID
     * @return 结果
     */
    public int deleteLimitByIds(String ids) {
        return limitMapper.deleteLimitByIds(Convert.toStrArray(ids));
    }

}
