package cn.bushadie.project.system.dept.service;

import java.util.*;

import cn.bushadie.common.constant.UserConstants;
import cn.bushadie.common.support.Convert;
import cn.bushadie.common.utils.StringUtils;
import cn.bushadie.common.utils.security.ShiroUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import cn.bushadie.framework.aspectj.lang.annotation.DataScope;
import cn.bushadie.project.system.dept.domain.Dept;
import cn.bushadie.project.system.dept.mapper.DeptMapper;
import cn.bushadie.project.system.role.domain.Role;

/**
 * 部门管理 服务实现
 *
 * @author ruoyi
 */
@Service
public class DeptServiceImpl implements IDeptService {
    @Autowired
    private DeptMapper deptMapper;

    /**
     * 查询部门管理数据
     *
     * @return 部门信息集合
     */
    @Override
    @DataScope(tableAlias="d")
    public List<Dept> selectDeptList(Dept dept) {
        return deptMapper.selectDeptList(dept);
    }

    /**
     * 查询部门管理树
     *
     * @return 所有部门信息
     */
    @Override
    public List<Map<String,Object>> selectDeptTree() {
        List<Map<String,Object>> trees=new ArrayList<Map<String,Object>>();
        List<Dept> deptList=selectDeptList(new Dept());
        trees=getTrees(deptList,false,null);
        return trees;
    }

    /**
     * 根据角色ID查询部门（数据权限）
     *
     * @param role 角色对象
     * @return 部门列表（数据权限）
     */
    @Override
    public List<Map<String,Object>> roleDeptTreeData(Role role) {
        Long roleId=role.getRoleId();
        List<Map<String,Object>> trees=new ArrayList<Map<String,Object>>();
        List<Dept> deptList=selectDeptList(new Dept());
        if(StringUtils.isNotNull(roleId)) {
            List<String> roleDeptList=deptMapper.selectRoleDeptTree(roleId);
            trees=getTrees(deptList,true,roleDeptList);
        }else {
            trees=getTrees(deptList,false,null);
        }
        return trees;
    }

    /**
     * 对象转部门树
     *
     * @param deptList     部门列表
     * @param isCheck      是否需要选中
     * @param roleDeptList 角色已存在菜单列表
     * @return
     */
    public List<Map<String,Object>> getTrees(List<Dept> deptList,boolean isCheck,List<String> roleDeptList) {

        List<Map<String,Object>> trees=new ArrayList<Map<String,Object>>();
        for(Dept dept: deptList) {
            if(UserConstants.DEPT_NORMAL.equals(dept.getStatus())) {
                Map<String,Object> deptMap=new HashMap<String,Object>();
                deptMap.put("id" ,dept.getDeptId());
                deptMap.put("pId" ,dept.getParentId());
                deptMap.put("name" ,dept.getDeptName());
                deptMap.put("title" ,dept.getDeptName());
                if(isCheck) {
                    deptMap.put("checked" ,roleDeptList.contains(dept.getDeptId()+dept.getDeptName()));
                }else {
                    deptMap.put("checked" ,false);
                }
                trees.add(deptMap);
            }
        }
        return trees;
    }

    /**
     * 查询部门人数
     *
     * @param parentId 部门ID
     * @return 结果
     */
    @Override
    public int selectDeptCount(Long parentId) {
        Dept dept=new Dept();
        dept.setParentId(parentId);
        return deptMapper.selectDeptCount(dept);
    }

    /**
     * 查询部门是否存在用户
     *
     * @param deptId 部门ID
     * @return 结果 true 存在 false 不存在
     */
    @Override
    public boolean checkDeptExistUser(Long deptId) {
        int result=deptMapper.checkDeptExistUser(deptId);
        return result>0 ? true : false;
    }

    /**
     * 删除部门管理信息
     *
     * @param deptId 部门ID
     * @return 结果
     */
    @Override
    public int deleteDeptById(Long deptId) {
        return deptMapper.deleteDeptById(deptId);
    }

    /**
     * 新增保存部门信息
     *
     * @param dept 部门信息
     * @return 结果
     */
    @Override
    public int insertDept(Dept dept) {
        Dept info=deptMapper.selectDeptById(dept.getParentId());
        dept.setCreateBy(ShiroUtils.getLoginName());
        dept.setAncestors(info.getAncestors()+","+dept.getParentId());
        return deptMapper.insertDept(dept);
    }

    /**
     * 修改保存部门信息
     *
     * @param dept 部门信息
     * @return 结果
     */
    @Override
    public int updateDept(Dept dept) {
        Dept info=deptMapper.selectDeptById(dept.getParentId());
        if(StringUtils.isNotNull(info)) {
            String ancestors=info.getAncestors()+","+dept.getParentId();
            dept.setAncestors(ancestors);
            updateDeptChildren(dept.getDeptId(),ancestors);
        }
        dept.setUpdateBy(ShiroUtils.getLoginName());
        return deptMapper.updateDept(dept);
    }

    /**
     * 修改子元素关系
     *
     * @param deptId    部门ID
     * @param ancestors 元素列表
     */
    public void updateDeptChildren(Long deptId,String ancestors) {
        Dept dept=new Dept();
        dept.setParentId(deptId);
        List<Dept> childrens=deptMapper.selectDeptList(dept);
        for(Dept children: childrens) {
            children.setAncestors(ancestors+","+dept.getParentId());
        }
        if(childrens.size()>0) {
            deptMapper.updateDeptChildren(childrens);
        }
    }

    /**
     * 根据部门ID查询信息
     *
     * @param deptId 部门ID
     * @return 部门信息
     */
    @Override
    public Dept selectDeptById(Long deptId) {
        return deptMapper.selectDeptById(deptId);
    }

    /**
     * 校验部门名称是否唯一
     *
     * @param dept 部门信息
     * @return 结果
     */
    @Override
    public String checkDeptNameUnique(Dept dept) {
        Long deptId=StringUtils.isNull(dept.getDeptId()) ? -1L : dept.getDeptId();
        Dept info=deptMapper.checkDeptNameUnique(dept.getDeptName(),dept.getParentId());
        if(StringUtils.isNotNull(info)&&info.getDeptId().longValue()!=deptId.longValue()) {
            return UserConstants.DEPT_NAME_NOT_UNIQUE;
        }
        return UserConstants.DEPT_NAME_UNIQUE;
    }

    /**
     * id所属的部门以及所有子部门
     * @param deptIds 用逗号分割的id
     * @return id所属的部门以及所有子部门
     */
    @Override
    public List<Dept> selectAllDeptByIds(String deptIds) {
        if( deptIds==null || "".equals(deptIds) || ",".equals(deptIds) ){
            return selectAllDeptByIds(new String[0]);
        }
        return selectAllDeptByIds(Convert.toStrArray(deptIds));
    }

    /**
     * id所属的部门以及所有子部门
     * @param deptIds
     * @return
     */
    public List<Dept> selectAllDeptByIds(String[] deptIds){
        List<Dept> deptList=selectDeptList(new Dept());
        if(deptIds.length==0) {
            return deptList;
        }
        ArrayList<Dept> depts=new ArrayList<>(deptIds.length);
        for(String id: deptIds) {
            Dept dept=deptMapper.selectDeptById(Long.valueOf(id));
            depts.add(dept);
        }
        HashMap<String,List<Long>> map=new HashMap<>(deptList.size()*4);
        HashMap<Long,Dept> deptMap=new HashMap<>(deptList.size());
        // 得到对应dept的所有下属 deptId
        for(Dept dept: deptList) {
            deptMap.put(dept.getDeptId(),dept);
            String[] tps=Convert.toStrArray(dept.getAncestors()+","+dept.getDeptId());
            for(String tp: tps) {
                map.computeIfAbsent(tp,k->new ArrayList<>());
                map.get(tp).add(dept.getDeptId());
            }
        }
        HashSet<Dept> deptSet=new HashSet<>();
        for(Dept dept: depts) {
            List<Long> longs=map.get(dept.getDeptId().toString());
            for(Long tp: longs) {
                deptSet.add( deptMap.get(tp) );
            }
        }

        return new ArrayList<>(deptSet);
    }

    /**
     * id所属的部门以及所有子部门
     *
     * @param deptId id
     * @return
     */
    @Override
    public List<Dept> selectAllDeptByIds(Long deptId) {
        return selectAllDeptByIds(deptId.toString());
    }


}
