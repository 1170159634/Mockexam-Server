package me.zhengjie.modules.mockexam.service.impl;


import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.collection.CollectionUtil;
import cn.hutool.json.JSONUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.github.tobato.fastdfs.domain.fdfs.StorePath;
import lombok.extern.slf4j.Slf4j;
import me.zhengjie.modules.mockexam.constant.CommonConstant;
import me.zhengjie.modules.mockexam.constant.enums.AnswerTypeEnum;
import me.zhengjie.modules.mockexam.constant.enums.ChooseAnswerTypeEnum;
import me.zhengjie.modules.mockexam.constant.enums.FavoritesTypeEnum;
import me.zhengjie.modules.mockexam.mapper.MeQuestionMapper;
import me.zhengjie.modules.mockexam.pojo.MeFavorites;
import me.zhengjie.modules.mockexam.pojo.MeQuestion;
import me.zhengjie.modules.mockexam.pojo.MeWrongQuestion;
import me.zhengjie.modules.mockexam.service.MeFavoritesService;
import me.zhengjie.modules.mockexam.service.MeQuestionService;
import me.zhengjie.modules.mockexam.service.MeWrongQuestionService;
import me.zhengjie.modules.mockexam.utils.FastDFSUtil;
import me.zhengjie.modules.mockexam.utils.FileUtil;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import java.io.IOException;
import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;

/**
 * <p>
 * 模拟试题表 服务实现类
 * </p>
 *
 * @author Ming
 * @since 2021-04-10
 */
@Slf4j
@Service
public class MeQuestionServiceImpl extends ServiceImpl<MeQuestionMapper, MeQuestion> implements MeQuestionService {

    @Resource
    private MeQuestionMapper meQuestionMapper;


    @Resource
    private MeWrongQuestionService meWrongQuestionService;

    @Resource
    private FastDFSUtil fastDFSUtil;

    @Resource
    private MeFavoritesService meFavoritesService;

    /**
     * 分页查询试题
     *
     * @return:
     * @Author: Mingxuan_X
     * @Date: 2021/4/11
     */
    @Override
    public List<MeQuestion> selectByPage(MeQuestion.Query query) {
        Page page = CommonConstant.queryLimitBuild(query.getPage(), query.getSize());
        Map<String, Object> map = BeanUtil.beanToMap(query);
        List<MeQuestion> list = meQuestionMapper.selectByPage(page, map);
        //过滤收藏类型
        meFavoritesService.filterFavaorites(query.getUserId(), list);
        return list;
    }

    /**
     * 查询试题没保存到服务器的图片
     *
     * @return:
     * @Author: Mingxuan_X
     * @Date: 2021/4/11
     */
    @Override
    public List<MeQuestion> selectNoProcessingPic(MeQuestion.Query query) {
        Page page = CommonConstant.queryLimitBuild(query.getPage(), query.getSize());
        return meQuestionMapper.selectNoProcessingPic(page);
    }

    /**
     * 综合评测:随机获取100道题
     *
     * @return:
     * @Author: Mingxuan_X
     * @Date: 2021/4/12
     */
    @Override
    public List<Long> selectExamQuestion(MeQuestion.Query query) {
        Map<String, Object> map = BeanUtil.beanToMap(query);
        List<MeQuestion> list = null;
        try {
            list = meQuestionMapper.selectExamQuestion(map);
            List<Long> ids = list.stream().map(MeQuestion::getId).collect(Collectors.toList());
            return ids;
        } catch (Exception e) {
            log.error("获取综合测评试题失败!", e);
        }

        return null;
    }


    /**
     * 处理试题图片
     *
     * @return:
     * @Author: Mingxuan_X
     * @Date: 2021/4/11
     */
    @Override
    public void processingPicBatch(List<MeQuestion> list) {
        meQuestionMapper.updateTargetPic(list);
    }

    /**
     * 批量插入模拟试题
     *
     * @return:
     * @Author: Mingxuan_X
     * @Date: 2021/4/10
     */
    @Override
    public void insetBatch(List<MeQuestion> list) {
        log.info("body: {}", JSONUtil.toJsonStr(list));
        if (CollectionUtil.isNotEmpty(list)) {
            saveBatch(list, list.size());
        }
    }

    /**
     * 查询试题总数
     *
     * @return:
     * @Author: Mingxuan_X
     * @Date: 2021/4/12
     */
    @Override
    public Integer selectCount(MeQuestion.Query query) {
        Map<String, Object> map = BeanUtil.beanToMap(query);
        if (CollectionUtil.isEmpty(map)) {
            return CommonConstant.DEFAULT_COUNT;
        }
        return meQuestionMapper.selectQuestionCount(map);
    }

    /**
     * 根据id查询试题
     *
     * @return:
     * @Author: Mingxuan_X
     * @Date: 2021/4/12
     */
    @Override
    public MeQuestion selectById(Long id) {
        return meQuestionMapper.selectById(id);
    }

    /**
     * 选试题结果
     *
     * @return:
     * @Author: Mingxuan_X
     * @Date: 2021/4/12
     */
    @Override
    public MeQuestion.chooseAnswerResult chooseAnswer(MeQuestion.Query query) {
        Long id = query.getId();
        MeQuestion meQuestion = meQuestionMapper.selectById(id);
        if (null == meQuestion) {
            log.error("未找到该试题 : {}", id);
            return null;
        }
        MeQuestion.chooseAnswerResult result = new MeQuestion.chooseAnswerResult();
        String msg = AnswerTypeEnum.getEnumMessage(meQuestion.getAnswer());
        if (query.getChooseAnswerResult().equals(msg)) {
            result.setIsError(ChooseAnswerTypeEnum.RIGNT.getCode());
            result.setResult(ChooseAnswerTypeEnum.RIGNT.getMsg());
        } else {
            result.setIsError(ChooseAnswerTypeEnum.WORING.getCode());
            result.setResult(ChooseAnswerTypeEnum.WORING.getMsg());
            result.setExplain(meQuestion.getQuestionExplain());
            //添加到错题集 json形式 以','分割
            MeWrongQuestion wrongQuestion = meWrongQuestionService
                    .getOne(new LambdaQueryWrapper<MeWrongQuestion>().eq(MeWrongQuestion::getUserId, query.getUserId()));
            if (null == wrongQuestion) {
                wrongQuestion = MeWrongQuestion.builder()
                        .id(CommonConstant.getUniqueId())
                        .createDate(LocalDateTime.now())
                        .questions(id.toString())
                        .userId(query.getUserId())
                        .build();
                meWrongQuestionService.save(wrongQuestion);
            } else {
                wrongQuestion.setQuestions(wrongQuestion.getQuestions() + "," + id.toString());
                meWrongQuestionService.updateById(wrongQuestion);
            }


        }
        return result;
    }

    /**
     * 获取模拟试题全部id
     *
     * @return:
     * @Author: Mingxuan_X
     * @Date: 2021/4/12
     */
    @Override
    public List<Long> selectIds(MeQuestion.Query query) {
        Map<String, Object> map = BeanUtil.beanToMap(query);
        List<Long> list = meQuestionMapper.selectIds(map);
        if (query.getIsRandom() != null) {
            Collections.shuffle(list);
        }
        return list;
    }

    @Override
    public MeQuestion selectRandomQuestion(MeQuestion.Query query) {
        Map<String, Object> map = BeanUtil.beanToMap(query);
        MeQuestion meQuestion = null;
        try {
            meQuestion = meQuestionMapper.selectRandomQuestion(map);
        } catch (Exception e) {
            log.error("随机查询试题失败!", e);
        }
        return meQuestion;
    }

    /**
     * 根据客观提醒获取题
     *
     * @return:
     * @Author: Mingxuan_X
     * @Date: 2021/4/13
     */
    @Override
    public List<Long> selectByObjectiveType(MeQuestion.Query query) {

        Map<String, Object> map = BeanUtil.beanToMap(query);
        return meQuestionMapper.selectByObjectiveType(map);
    }

    /**
     * 按照随机题型返回题
     *
     * @return:
     * @Author: Mingxuan_X
     * @Date: 2021/4/13
     */
    @Override
    public List<MeQuestion> selectRandomByPage(MeQuestion.Query query) {
        Page page = CommonConstant.queryLimitBuild(query.getPage(), query.getSize());
        Map<String, Object> map = BeanUtil.beanToMap(query);
        return meQuestionMapper.selectRandomByPage(page, map);
    }

    /**
     * 添加试题
     *
     * @return:
     * @Author: Mingxuan_X
     * @Date: 2021/4/16
     */
    @Override
    public Object addQuestion(MeQuestion query) {
        query.setId(CommonConstant.getUniqueId());
        this.save(query);
        return null;
    }

    /**
     * 修改试题
     *
     * @return:
     * @Author: Mingxuan_X
     * @Date: 2021/4/16
     */
    @Override
    public Object updateQuestion(MeQuestion query) {
        MeQuestion meQuestion = this.getById(query.getId());
        if (meQuestion != null) {
            this.updateById(query);
        }
        return null;
    }

    /**
     * 删除试题
     *
     * @return:
     * @Author: Mingxuan_X
     * @Date: 2021/4/16
     */
    @Override
    public Object deleteQuestion(Set<Long> ids) {
        this.removeByIds(ids);
        return null;
    }

    /**
     * 修改试题图片
     *
     * @return:
     * @Author: Mingxuan_X
     * @Date: 2021/4/16
     */
    @Override
    public Object updateQuestionPic(MultipartFile file) {

        try {
            StorePath path = fastDFSUtil.upload(file);
            return path.getPath();
        } catch (IOException e) {
            log.error("上传图片到FastDFS服务器错误!", e);
        }
        return null;
    }

    /**
     * 返回用户推荐的题
     *
     *
     * @return:
     * @Author: Mingxuan_X
     * @Date: 2021/4/20
     */
    @Override
    public List<MeQuestion> selectUserBehaviorByPage(MeQuestion.Query query) {
        Page page = CommonConstant.queryLimitBuild(query.getPage(), query.getSize());
        Map<String, Object> map = BeanUtil.beanToMap(query);
        return meQuestionMapper.selectRandomByPage(page, map);
    }
}
