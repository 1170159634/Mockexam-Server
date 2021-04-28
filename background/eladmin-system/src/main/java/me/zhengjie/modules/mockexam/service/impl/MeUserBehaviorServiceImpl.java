package me.zhengjie.modules.mockexam.service.impl;

import cn.hutool.core.util.NumberUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import me.zhengjie.modules.mockexam.constant.CommonConstant;
import me.zhengjie.modules.mockexam.constant.RedisConstant;
import me.zhengjie.modules.mockexam.constant.enums.MessageTypeEnum;
import me.zhengjie.modules.mockexam.mapper.MeUserBehaviorMapper;
import me.zhengjie.modules.mockexam.pojo.MeQuestion;
import me.zhengjie.modules.mockexam.pojo.MeUserBehavior;
import me.zhengjie.modules.mockexam.service.MeFavoritesService;
import me.zhengjie.modules.mockexam.service.MeQuestionService;
import me.zhengjie.modules.mockexam.service.MeUserBehaviorService;
import me.zhengjie.modules.mockexam.service.MeWrongQuestionService;
import me.zhengjie.modules.mockexam.utils.JsonUtil;
import me.zhengjie.modules.mockexam.utils.LocalDateUtil;
import me.zhengjie.utils.RedisUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

/**
 * <p>
 * 用户行为分析表 服务实现类
 * </p>
 *
 * @author Ming
 * @since 2021-04-13
 */
@Service
public class MeUserBehaviorServiceImpl extends ServiceImpl<MeUserBehaviorMapper, MeUserBehavior> implements MeUserBehaviorService {


    @Resource
    private MeUserBehaviorMapper meUserBehaviorMapper;

    @Resource
    private MeWrongQuestionService meWrongQuestionService;

    @Resource
    private MeQuestionService meQuestionService;

    @Resource
    private RedisUtils redisUtils;

    @Resource
    private MeFavoritesService meFavoritesService;

    /**
     * 定时任务: 计算一月内用户所做的题，算出他们平常错的题型有哪些
     *
     * @return:
     * @Author: Mingxuan_X
     * @Date: 2021/4/13
     */
    @Override
    public void calculateUserWrongQuestion() {
        LocalDateTime now = LocalDateTime.now();
        Date endTime = LocalDateUtil.localDateTime2Date(now);
        Date beginTime = LocalDateUtil.localDateTime2Date(now.plusMonths(-1));
        //获取一周内登录的用户
        List<MeUserBehavior> userList = meUserBehaviorMapper.selectList(new QueryWrapper<MeUserBehavior>()
                .ge("update_date", beginTime)
                .le("update_date", endTime));
        //获取userid，转为数组
        userList.forEach(obj -> {
            MeUserBehavior meUserBehavior = meWrongQuestionService.calculateUserWrongSum(obj);
            //如果用户还没错过题，就不推荐了
            if (meUserBehavior == null) {
                return;
            }
            this.updateById(meUserBehavior);
            //算法： 阀值*加载因子=该题型的题  阀值-题型题=题型下客观题
            Integer recommendTopicCount = CommonConstant.RECOMMEND_TOPIC_COUNT;
            Integer questionTypeCount = NumberUtil.mul(recommendTopicCount * CommonConstant.LOAD_FACTOR_PERCENTAGE).intValue();
            Integer objectTypeCount = recommendTopicCount - questionTypeCount;

            List<MeQuestion> questionList = new ArrayList<>(recommendTopicCount);
            MeQuestion.Query query = new MeQuestion.Query();
            query.setPage(1);
            query.setSize(questionTypeCount);
            query.setQuestionType(meUserBehavior.getMaximumQuestionType());
            //获取最多题型的题
            List<MeQuestion> questions = meQuestionService.selectRandomByPage(query);
            questionList.addAll(questions);
            //在获取客观题型的题
            query.setSize(objectTypeCount);
            query.setObjectiveType(meUserBehavior.getMaximumObjectiveType());
            List<MeQuestion> objectives = meQuestionService.selectRandomByPage(query);
            questionList.addAll(objectives);
            //合并 添加到redis
            String json = JsonUtil.objectToJson(questionList);
            String userId = obj.getUserId().toString();
            redisUtils.del(RedisConstant.USER_BEHAVIOR_KEY + userId);
            redisUtils.set(RedisConstant.USER_BEHAVIOR_KEY + userId, json, LocalDateUtil.ONE_WEEKEND_SECONDS);
        });
    }

    /**
     * 获取用户推荐的题ids
     *
     * @return:
     * @Author: Mingxuan_X
     * @Date: 2021/4/13
     */
    @Override
    public List<Long> selectByPage(MeUserBehavior.Query query) {
        String json = (String) redisUtils.get(RedisConstant.USER_BEHAVIOR_KEY + query.getUserId().toString());
        if (json != null) {
            List<MeQuestion> list = JsonUtil.jsonToList(json, MeQuestion.class);
            meFavoritesService.filterFavaorites(query.getUserId(), list);
            return list.stream().map(MeQuestion::getId).collect(Collectors.toList());
        }
        MeUserBehavior behavior = this.getOne(new LambdaQueryWrapper<MeUserBehavior>().eq(MeUserBehavior::getUserId, query.getUserId()));
        if (behavior == null) {
            return null;
        }
        //算法： 阀值*加载因子=该题型的题  阀值-题型题=题型下客观题
        Integer recommendTopicCount = CommonConstant.RECOMMEND_TOPIC_COUNT;
        Integer questionTypeCount = NumberUtil.mul(recommendTopicCount * CommonConstant.LOAD_FACTOR_PERCENTAGE).intValue();
        Integer objectTypeCount = recommendTopicCount - questionTypeCount;
        List<MeQuestion> questionList = new ArrayList<>(recommendTopicCount);
        MeQuestion.Query questionQuery = new MeQuestion.Query();
        questionQuery.setPage(1);
        questionQuery.setSize(questionTypeCount);
        questionQuery.setQuestionType(behavior.getMaximumQuestionType());
        //获取最多题型的题
        List<MeQuestion> questions = meQuestionService.selectUserBehaviorByPage(questionQuery);
        questionList.addAll(questions);
        //在获取客观题型的题
        questionQuery.setSize(objectTypeCount);
        questionQuery.setObjectiveType(behavior.getMaximumObjectiveType());
        List<MeQuestion> objectives = meQuestionService.selectUserBehaviorByPage(questionQuery);
        questionList.addAll(objectives);
        //过滤收藏类型
        meFavoritesService.filterFavaorites(query.getUserId(), questionList);
        String data = JsonUtil.objectToJson(questionList);
        redisUtils.set(RedisConstant.USER_BEHAVIOR_KEY + query.getUserId().toString(), data, LocalDateUtil.ONE_WEEKEND_SECONDS);

        return questionList.stream().map(MeQuestion::getId).collect(Collectors.toList());

    }


    /**
     * 获取分页推荐题总数
     *
     * @return:
     * @Author: Mingxuan_X
     * @Date: 2021/4/13
     */
    @Override
    public Integer selectCount(MeUserBehavior.Query query) {
        return CommonConstant.RECOMMEND_TOPIC_COUNT;
    }

    /**
     * 判断用户是否已读该信息
     *
     * @return:
     * @Author: Mingxuan_X
     * @Date: 2021/4/13
     */
    @Override
    public Integer judgeIsRead(Long query) {
        MeUserBehavior behavior = this.getOne(new LambdaQueryWrapper<MeUserBehavior>().eq(MeUserBehavior::getUserId,query));
        if (behavior == null) {
            return MessageTypeEnum.UNREAD.getCode();
        }
        return behavior.getIsRead();
    }

    /**
     * 确认已读
     *
     * @return:
     * @Author: Mingxuan_X
     * @Date: 2021/4/15
     */
    @Override
    public void confirmRead(Long query) {
        MeUserBehavior behavior = this.getOne(new LambdaQueryWrapper<MeUserBehavior>().eq(MeUserBehavior::getUserId,query));
        if (behavior != null) {
            behavior.setIsRead(MessageTypeEnum.READ.getCode());
            this.updateById(behavior);
        }
    }

    /**
     * 获取用户最后登录时间
     *
     * @return:
     * @Author: Mingxuan_X
     * @Date: 2021/4/15
     */
    @Override
    public Integer lastLoginTime(Long userId) {
        MeUserBehavior behavior = this.getOne(new LambdaQueryWrapper<MeUserBehavior>().eq(MeUserBehavior::getUserId,userId));
        if (behavior != null) {
            LocalDateTime lastTime = behavior.getUpdateDate();
            LocalDateTime nowTime = LocalDateTime.now();
            return (int) LocalDateUtil.getChronoUnitBetweenByLocalDateTime(lastTime, nowTime, ChronoUnit.DAYS);
        }
        return 0;
    }

    /**
     * 修改用户登录时间
     *
     * @return:
     * @Author: Mingxuan_X
     * @Date: 2021/4/20
     */
    @Override
    public void updateLoginTime(Long userId) {
        MeUserBehavior behavior = this.getOne(new LambdaQueryWrapper<MeUserBehavior>().eq(MeUserBehavior::getUserId,userId));
        if (behavior != null) {
            behavior.setUpdateDate(LocalDateTime.now());
        }
        this.updateById(behavior);
    }
}
