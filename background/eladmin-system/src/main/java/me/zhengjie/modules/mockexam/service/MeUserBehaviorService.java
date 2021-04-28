package me.zhengjie.modules.mockexam.service;


import com.baomidou.mybatisplus.extension.service.IService;
import me.zhengjie.modules.mockexam.pojo.MeQuestion;
import me.zhengjie.modules.mockexam.pojo.MeUserBehavior;

import java.util.List;

/**
 * <p>
 * 用户行为分析表 服务类
 * </p>
 *
 * @author Ming
 * @since 2021-04-13
 */
public interface MeUserBehaviorService extends IService<MeUserBehavior> {


    /**
     * 定时任务: 计算一周内用户所做的题，算出他们平常错的题型有哪些
     *
     *
     * @return:
     * @Author: Mingxuan_X
     * @Date: 2021/4/15
     */
    void calculateUserWrongQuestion();

    /**
     * 分页获取推荐题型
     *
     * @return:
     * @Author: Mingxuan_X
     * @Date: 2021/4/13
     */
    List<Long> selectByPage(MeUserBehavior.Query query);

    /**
     * 分页获取题型总数
     *
     * @return:
     * @Author: Mingxuan_X
     * @Date: 2021/4/13
     */
    Integer selectCount(MeUserBehavior.Query query);

    /**
     * 判读用户是否已读推荐题型
     *
     * @return:
     * @Author: Mingxuan_X
     * @Date: 2021/4/13
     */
    Integer judgeIsRead(Long query);

    /**
     * 确认消息已读
     *
     * @return:
     * @Author: Mingxuan_X
     * @Date: 2021/4/13
     */
    void confirmRead(Long query);

    /**
     * 判断
     *
     *
     * @return:
     * @Author: Mingxuan_X
     * @Date: 2021/4/15
     */
    Integer lastLoginTime(Long query);

    /**
     * 修改用户登录时间
     *
     *
     * @return:
     * @Author: Mingxuan_X
     * @Date: 2021/4/20
     */
    void updateLoginTime(Long userId);
}
