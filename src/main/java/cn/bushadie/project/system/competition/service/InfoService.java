package cn.bushadie.project.system.competition.service;

import cn.bushadie.common.support.Convert;
import cn.bushadie.project.system.competition.domain.Info;
import cn.bushadie.project.system.competition.mapper.InfoMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 具体 服务层实现
 *
 * @author jdmy
 * @date 2018-12-26
 */
@Service
public class InfoService {
    @Autowired
    private InfoMapper infoMapper;

    /**
     * 查询具体信息
     *
     * @param id 具体ID
     * @return 具体信息
     */
    public Info selectInfoById(Integer id) {
        return infoMapper.selectInfoById(id);
    }

    /**
     * 查询具体列表
     *
     * @param info 具体信息
     * @return 具体集合
     */
    public List<Info> selectInfoList(Info info) {
        return infoMapper.selectInfoList(info);
    }

    /**
     * 新增具体
     *
     * @param info 具体信息
     * @return 结果
     */
    public int insertInfo(Info info) {
        return infoMapper.insertInfo(info);
    }

    /**
     * 修改具体
     *
     * @param info 具体信息
     * @return 结果
     */
    public int updateInfo(Info info) {
        return infoMapper.updateInfo(info);
    }

    /**
     * 删除具体对象
     *
     * @param ids 需要删除的数据ID
     * @return 结果
     */
    public int deleteInfoByIds(String ids) {
        return infoMapper.deleteInfoByIds(Convert.toStrArray(ids));
    }

}
