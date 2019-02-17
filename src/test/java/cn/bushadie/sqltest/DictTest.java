package cn.bushadie.sqltest;

import cn.bushadie.framework.web.service.DictService;
import cn.bushadie.project.system.dict.domain.DictData;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.List;

/**
 * @author jdmy
 * on 2019/2/17.
 **/
@SpringBootTest
@RunWith( SpringRunner.class)
@Transactional(rollbackFor = Exception.class)
public class DictTest {
    @Resource
    private DictService dictService;

    @Test
    public void getType(){
        // com_competition_status  sys_normal_disable
        List<DictData> list=dictService.getType("com_competition_status");
        for(DictData dictData: list) {
            System.out.println(dictData);
        }
    }
}
