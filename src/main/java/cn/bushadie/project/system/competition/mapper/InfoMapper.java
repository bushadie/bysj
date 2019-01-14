package cn.bushadie.project.system.competition.mapper;

import cn.bushadie.project.system.competition.domain.Info;
import org.springframework.stereotype.Repository;
import java.util.List;

/**
 * 具体 数据层
 *
 * @author jdmy
 * @date 2018-12-26
 */
@Repository
public interface InfoMapper {
    /**
     * 查询具体信息
     *
     * @param id 具体ID
     * @return 具体信息
     */
    public Info selectInfoById(Integer id);

    /**
     * 查询具体列表
     *
     * @param info 具体信息
     * @return 具体集合
     */
    public List<Info> selectInfoList(Info info);

    /**
     * 新增具体
     *
     * @param info 具体信息
     * @return 结果
     */
    public int insertInfo(Info info);

    /**
     * 修改具体
     *
     * @param info 具体信息
     * @return 结果
     */
    public int updateInfo(Info info);

    /**
     * 删除具体
     *
     * @param id 具体ID
     * @return 结果
     */
    public int deleteInfoById(Integer id);

    /**
     * 批量删除具体
     *
     * @param ids 需要删除的数据ID
     * @return 结果
     */
    public int deleteInfoByIds(String[] ids);

}