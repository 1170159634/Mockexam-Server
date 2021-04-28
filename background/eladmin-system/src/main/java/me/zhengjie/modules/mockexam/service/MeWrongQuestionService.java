package me.zhengjie.modules.mockexam.service;


import com.baomidou.mybatisplus.extension.service.IService;
import me.zhengjie.modules.mockexam.pojo.MeFavorites;
import me.zhengjie.modules.mockexam.pojo.MeQuestion;
import me.zhengjie.modules.mockexam.pojo.MeUserBehavior;
import me.zhengjie.modules.mockexam.pojo.MeWrongQuestion;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

/**
 * <p>
 * 用户错题表 服务类
 * </p>
 *
 * @author Ming
 * @since 2021-04-12
 */
public interface MeWrongQuestionService extends IService<MeWrongQuestion> {

    /**
     * 分页查询错题集
     *
     *
     * @return:
     * @Author: Mingxuan_X
     * @Date: 2021/4/12
     */
    List<MeQuestion> selectByPage(MeWrongQuestion.Query query);


    /**
     * 错题总数
     *
     *
     * @return:
     * @Author: Mingxuan_X
     * @Date: 2021/4/17
     */
    Integer  selectCount(MeWrongQuestion.Query query);
    /**
     * 计算用户错误最多的题型及错题次数
     *
     *
     * @return:
     * @Author: Mingxuan_X
     * @Date: 2021/4/13
     */
    MeUserBehavior calculateUserWrongSum(MeUserBehavior meUserBehavior);



}
