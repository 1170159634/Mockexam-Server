package me.zhengjie.modules.mockexam.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import me.zhengjie.modules.mockexam.pojo.MeQuestion;
import me.zhengjie.modules.mockexam.pojo.MeUserBehavior;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

/**
 * <p>
 * 模拟试题表 Mapper 接口
 * </p>
 *
 * @author Ming
 * @since 2021-04-10
 */
public interface MeQuestionMapper extends BaseMapper<MeQuestion> {

    /**
     * 分页查询试题
     *
     * @return:
     * @Author: Mingxuan_X
     * @Date: 2021/4/11
     */
    List<MeQuestion> selectByPage(IPage page, @Param("map") Map<String, Object> map);



    /**
     * 查询没有处理的试题图片
     *
     *
     * @return:
     * @Author: Mingxuan_X
     * @Date: 2021/4/11
     */
    List<MeQuestion> selectNoProcessingPic(IPage page);

    /**
     * 处理试题图片，保存到本地
     *
     *
     * @return:
     * @Author: Mingxuan_X
     * @Date: 2021/4/11
     */
    void updateTargetPic(List<MeQuestion> list);

    /**
     * 查询试题总数
     *
     *
     * @return:
     * @Author: Mingxuan_X
     * @Date: 2021/4/12
     */
    Integer selectQuestionCount(@Param("map") Map<String, Object> map);

    /**
     * 获取模拟试题全部id
     *
     *
     * @return:
     * @Author: Mingxuan_X
     * @Date: 2021/4/12
     */
    List<Long> selectIds(@Param("map") Map<String, Object> map);

    /**
     * 随机获取一道试题
     *
     *
     * @return:
     * @Author: Mingxuan_X
     * @Date: 2021/4/12
     */
    MeQuestion selectRandomQuestion(@Param("map") Map<String, Object> map);

    /**
     * 随机获取100道题
     *
     *
     * @return:
     * @Author: Mingxuan_X
     * @Date: 2021/4/12
     */
    List<MeQuestion> selectExamQuestion(@Param("map")Map<String, Object> map);

    /**
     * 根据客观题型获取题
     *
     *
     * @return:
     * @Author: Mingxuan_X
     * @Date: 2021/4/13
     */
    List<Long> selectByObjectiveType(Map<String, Object> map);

    /**
     * 按照用户错题客观类型进行排序
     *
     *
     * @return:
     * @Author: Mingxuan_X
     * @Date: 2021/4/13
     */
    List<MeUserBehavior.userBehaviorResult> selectObjectTypeBehavior(@Param("ids")Long[] ids);

    /**
     * 按照用户错题类型进行排序
     *
     *
     * @return:
     * @Author: Mingxuan_X
     * @Date: 2021/4/13
     */
    MeUserBehavior.userBehaviorResult selectQuestionTypeBehavior(@Param("ids")Long[] ids);

    /**
     * 按照题型返回题
     *
     *
     * @return:
     * @Author: Mingxuan_X
     * @Date: 2021/4/13
     */
    List<MeQuestion> selectRandomByPage(Page page, @Param("map") Map<String, Object> map);
}
