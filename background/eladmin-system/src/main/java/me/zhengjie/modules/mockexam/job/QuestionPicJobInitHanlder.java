package me.zhengjie.modules.mockexam.job;

import com.xxl.job.core.biz.model.ReturnT;
import com.xxl.job.core.handler.IJobHandler;
import com.xxl.job.core.handler.annotation.JobHandler;
import lombok.extern.slf4j.Slf4j;
import me.zhengjie.modules.mockexam.service.CrawlingService;
import me.zhengjie.modules.mockexam.service.MeUserBehaviorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * @author :         Mingxuan_x
 * @version :        1.0
 * @Description: 初始化驾考题图片(Init)
 * @Telephone :      15135964789
 * @createDate :     2021/4/15 12:16
 * @updateUser :     Mingxuan_x
 * @updateDate :     2021/4/15 12:16
 * @updateRemark :   修改内容
 **/
@JobHandler(value = "QuestionPicJobInitHanlder")
@Component
@Slf4j
public class QuestionPicJobInitHanlder extends IJobHandler {


    @Autowired
    private CrawlingService crawlingService;

    @Override
    public ReturnT<String> execute(String param) {
        crawlingService.pic2Local();
        return ReturnT.SUCCESS;
    }


}
