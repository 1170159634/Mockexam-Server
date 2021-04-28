package me.zhengjie.modules.mockexam.service.impl;

import cn.hutool.core.bean.BeanUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import lombok.extern.slf4j.Slf4j;
import me.zhengjie.modules.mockexam.constant.CommonConstant;
import me.zhengjie.modules.mockexam.constant.enums.MessageTypeEnum;
import me.zhengjie.modules.mockexam.mapper.MeQuestionMapper;
import me.zhengjie.modules.mockexam.mapper.MeWrongQuestionMapper;
import me.zhengjie.modules.mockexam.pojo.MeQuestion;
import me.zhengjie.modules.mockexam.pojo.MeUserBehavior;
import me.zhengjie.modules.mockexam.pojo.MeWrongQuestion;
import me.zhengjie.modules.mockexam.service.MeFavoritesService;
import me.zhengjie.modules.mockexam.service.MeQuestionService;
import me.zhengjie.modules.mockexam.service.MeWrongQuestionService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * <p>
 * 用户错题表 服务实现类
 * </p>
 *
 * @author Ming
 * @since 2021-04-12
 */
@Service
@Slf4j
public class MeWrongQuestionServiceImpl extends ServiceImpl<MeWrongQuestionMapper, MeWrongQuestion> implements MeWrongQuestionService {

    @Resource
    private MeWrongQuestionMapper meWrongQuestionMapper;

    @Resource
    private MeQuestionService meQuestionService;
    @Resource
    private MeFavoritesService meFavoritesService;

    @Resource
    private MeQuestionMapper meQuestionMapper;

    /**
     * 分页查询错题集
     *
     * @return:
     * @Author: Mingxuan_X
     * @Date: 2021/4/12
     */
    @Override
    public List<MeQuestion> selectByPage(MeWrongQuestion.Query query) {
        Map<String, Object> map = BeanUtil.beanToMap(query);
        String strs = meWrongQuestionMapper.selectByPage(map);
        Long[] ids = Arrays.stream(strs.split(",")).map(s -> Long.parseLong(s.trim())).toArray(Long[]::new);
        //解决错题重复问题
        LambdaQueryWrapper<MeQuestion> wrapper = new LambdaQueryWrapper<MeQuestion>();
        if (ids.length > 0) {
            wrapper.in(MeQuestion::getId, ids);
        }
        if (StringUtils.isNotBlank(query.getLikeQuestions())) {
            wrapper.like(MeQuestion::getQuestion, query.getLikeQuestions());
        }
        if (query.getSubjectType() != null) {
            wrapper.eq(MeQuestion::getSubjectType, query.getSubjectType());
        }
        if (query.getQuestionType() != null) {
            wrapper.eq(MeQuestion::getQuestionType, query.getQuestionType());
        }
        List<MeQuestion> list = meQuestionService.list(wrapper);
        Integer page = query.getPage();
        if (page <= 0) {
            page = 1;
        }
        List<MeQuestion> questions = list.stream().skip((page - 1) *
                query.getSize()).limit(query.getSize()).collect(Collectors.toList());
        //过滤收藏类型
        meFavoritesService.filterFavaorites(query.getUserId(), questions);
        return questions;
    }

    @Override
    public Integer selectCount(MeWrongQuestion.Query query) {
        Map<String, Object> map = BeanUtil.beanToMap(query);
        String strs = meWrongQuestionMapper.selectByPage(map);
        Long[] ids = Arrays.stream(strs.split(",")).map(s -> Long.parseLong(s.trim())).distinct().toArray(Long[]::new);
        return ids.length;
    }


    /**
     * 计算用户错题次数和错题最多的类型
     *
     * @return:
     * @Author: Mingxuan_X
     * @Date: 2021/4/13l
     */
    @Override
    public MeUserBehavior calculateUserWrongSum(MeUserBehavior obj) {
        MeWrongQuestion wrongQuestion = this.getOne(new LambdaQueryWrapper<MeWrongQuestion>().eq(MeWrongQuestion::getUserId, obj.getUserId()));
        if (wrongQuestion == null) {
            log.info("未找到当前用户错过题");
            return null;
        }
        String questions = wrongQuestion.getQuestions();
        Long[] ids = Arrays.stream(questions.split(",")).map(s -> Long.parseLong(s.trim())).toArray(Long[]::new);

        //按照试题id查找用户错误最多的客观题型
        List<MeUserBehavior.userBehaviorResult> objectTypeBehavior = meQuestionMapper.selectObjectTypeBehavior(ids);
        //按照试题id查找用户错误最多的题型
        MeUserBehavior.userBehaviorResult questionTypeBehavior = meQuestionMapper.selectQuestionTypeBehavior(ids);
        Integer sum = objectTypeBehavior.stream().mapToInt(MeUserBehavior.userBehaviorResult::getWrongCount).sum();

        MeUserBehavior behavior = MeUserBehavior.builder()
                .id(obj.getId())
                .createDate(LocalDateTime.now())
                .userId(obj.getUserId())
                .maximumQuestionType(questionTypeBehavior.getMaximumQuestionType())
                .maximumObjectiveType(objectTypeBehavior.get(0).getMaximumObjectiveType())
                .wrongNum(sum)
                .isRead(MessageTypeEnum.UNREAD.getCode())
                .build();
        return behavior;

    }


}
