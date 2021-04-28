package me.zhengjie.modules.mockexam.service;

import com.baomidou.mybatisplus.extension.service.IService;
import me.zhengjie.modules.mockexam.pojo.MeQuestion;
import me.zhengjie.modules.mockexam.pojo.MeWrongQuestion;
import org.apache.ibatis.annotations.Param;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Set;

/**
 * <p>
 * 模拟试题表 服务类
 * </p>
 *
 * @author Ming
 * @since 2021-04-10
 */
public interface MeQuestionService extends IService<MeQuestion> {




    /**
     * 分页查询
     *
     * @return:
     * @Author: Mingxuan_X
     * @Date: 2021/4/11
     */
    List<MeQuestion> selectByPage(MeQuestion.Query query);

    /**
     * 分页查询没有处理图片的试题
     *
     * @return:
     * @Author: Mingxuan_X
     * @Date: 2021/4/11
     */
    List<MeQuestion> selectNoProcessingPic(MeQuestion.Query query);

    /**
     * 随机获取100道题
     *
     * @return:
     * @Author: Mingxuan_X
     * @Date: 2021/4/11
     */
    List<Long> selectExamQuestion(MeQuestion.Query query);
    /**
     * 处理试题图片，设置本地路径
     *
     * @return:
     * @Author: Mingxuan_X
     * @Date: 2021/4/11
     */
    void processingPicBatch(List<MeQuestion> list);

    /**
     * 批量插入
     *
     * @return:
     * @Author: Mingxuan_X
     * @Date: 2021/4/11
     */
    void insetBatch(List<MeQuestion> list);

    /**
     * 查询试题总数
     *
     *
     * @return:
     * @Author: Mingxuan_X
     * @Date: 2021/4/12
     */
    Integer selectCount(MeQuestion.Query query);

    /**
     * 根据id查询试题
     *
     *
     * @return:
     * @Author: Mingxuan_X
     * @Date: 2021/4/12
     */
    MeQuestion selectById(Long  id);

    /**
     * 选试题结果
     *
     *
     * @return:
     * @Author: Mingxuan_X
     * @Date: 2021/4/12
     */
    MeQuestion.chooseAnswerResult chooseAnswer(MeQuestion.Query query);

    /**
     * 获取模拟试题全部id
     *
     *
     * @return:
     * @Author: Mingxuan_X
     * @Date: 2021/4/12
     */
    List<Long> selectIds(MeQuestion.Query query);

    /**
     * 随机获取一道试题
     *
     *
     * @return:
     * @Author: Mingxuan_X
     * @Date: 2021/4/12
     */
    MeQuestion selectRandomQuestion(MeQuestion.Query query);

    /**
     * 根据客观题类型获取试题
     *
     *
     * @return:
     * @Author: Mingxuan_X
     * @Date: 2021/4/13
     */
    List<Long> selectByObjectiveType(MeQuestion.Query query);

    /**
     * 按照推荐题型随机返回多个题
     *
     *
     * @return:
     * @Author: Mingxuan_X
     * @Date: 2021/4/13
     */
    List<MeQuestion> selectRandomByPage(MeQuestion.Query questionQuery);

    /**
     * 添加试题
     *
     *
     * @return:
     * @Author: Mingxuan_X
     * @Date: 2021/4/16
     */
    Object addQuestion(MeQuestion query);

    /**
     * 修改试题
     *
     *
     * @return:
     * @Author: Mingxuan_X
     * @Date: 2021/4/16
     */
    Object updateQuestion(MeQuestion query);

    /**
     * 删除试题
     *
     *
     * @return:
     * @Author: Mingxuan_X
     * @Date: 2021/4/16
     */
    Object deleteQuestion(Set<Long> ids);

    /**
     * 修改试题图片
     *
     *
     * @return:
     * @Author: Mingxuan_X
     * @Date: 2021/4/16
     */
    Object updateQuestionPic(MultipartFile file);

    /**
     * 返回用户推荐的题
     *
     *
     * @return:
     * @Author: Mingxuan_X
     * @Date: 2021/4/20
     */
    List<MeQuestion> selectUserBehaviorByPage(MeQuestion.Query questionQuery);
}
