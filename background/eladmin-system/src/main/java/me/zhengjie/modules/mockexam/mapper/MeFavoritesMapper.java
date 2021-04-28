package me.zhengjie.modules.mockexam.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import me.zhengjie.modules.mockexam.pojo.MeFavorites;
import me.zhengjie.modules.mockexam.pojo.MeQuestion;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

/**
 * <p>
 * 试题收藏表 Mapper 接口
 * </p>
 *
 * @author Ming
 * @since 2021-04-12
 */
public interface MeFavoritesMapper extends BaseMapper<MeFavorites> {

    /**
     * 分页查询用户收藏试题
     *
     * @return:
     * @Author: Mingxuan_X
     * @Date: 2021/4/12
     */
    List<MeQuestion> selectByPage(IPage page, @Param("map") Map<String, Object> map);


    /**
     * 获取收藏试题总数
     *
     *
     * @return:
     * @Author: Mingxuan_X
     * @Date: 2021/4/17
     */
    Integer selectQuestionCount(@Param("map") Map<String, Object> map);
}
