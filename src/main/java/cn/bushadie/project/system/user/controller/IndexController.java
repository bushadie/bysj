package cn.bushadie.project.system.user.controller;

import java.util.List;

import cn.bushadie.project.system.menu.domain.Menu;
import cn.bushadie.project.system.menu.service.IMenuService;
import cn.bushadie.project.system.user.domain.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import cn.bushadie.framework.config.RuoYiConfig;
import cn.bushadie.framework.web.controller.BaseController;

/**
 * 首页 业务处理
 *
 * @author ruoyi
 */
@Controller
public class IndexController extends BaseController {
    @Autowired
    private IMenuService menuService;

    @Autowired
    private RuoYiConfig ruoYiConfig;

    // 系统首页
    @GetMapping("/index")
    public String index(ModelMap mmap) {
        // 取身份信息
        User user=getSysUser();
        // 根据用户id取出菜单
        List<Menu> menus=menuService.selectMenusByUser(user);
        mmap.put("menus" ,menus);
        mmap.put("user" ,user);
        mmap.put("copyrightYear" ,ruoYiConfig.getCopyrightYear());
        return "index" ;
    }

    // 系统介绍
    @GetMapping("/system/main")
    public String main(ModelMap mmap) {
        mmap.put("version" ,ruoYiConfig.getVersion());
        return "main" ;
    }
}
