package me.zhengjie.modules.mockexam.job;

import com.xxl.job.core.biz.model.ReturnT;
import com.xxl.job.core.handler.IJobHandler;
import com.xxl.job.core.handler.annotation.JobHandler;
import lombok.extern.slf4j.Slf4j;
import me.zhengjie.modules.mockexam.service.MeUserBehaviorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * @author :         Mingxuan_x
 * @version :        1.0
 * @Description: 定时获取错题集数据，收集用户最近错的题，推荐他一部分这样的题,并把推荐的结果放入到redis
 * cron:
 * @Telephone :      15135964789
 * @createDate :     2021/4/15 11:36
 * @updateUser :     Mingxuan_x
 * @updateDate :     2021/4/15 11:36
 * @updateRemark :   修改内容
 **/
@JobHandler(value = "CalUserBehaviorJobHandler")
@Component
@Slf4j
public class CalUserBehaviorJobHandler extends IJobHandler {

    @Autowired
    private MeUserBehaviorService meUserBehaviorService;

    @Override
    public ReturnT<String> execute(String param)  {
        meUserBehaviorService.calculateUserWrongQuestion();
        return ReturnT.SUCCESS;
    }

}
