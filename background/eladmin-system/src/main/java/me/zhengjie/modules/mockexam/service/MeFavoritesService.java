package me.zhengjie.modules.mockexam.service;


import com.baomidou.mybatisplus.extension.service.IService;
import me.zhengjie.modules.mockexam.pojo.MeFavorites;
import me.zhengjie.modules.mockexam.pojo.MeQuestion;

import java.util.List;

/**
 * <p>
 * 试题收藏表 服务类
 * </p>
 *
 * @author Ming
 * @since 2021-04-12
 */
public interface MeFavoritesService extends IService<MeFavorites> {

    /**
     * 分页查询用户收藏试题
     *
     *
     * @return:
     * @Author: Mingxuan_X
     * @Date: 2021/4/12
     */
    List<MeQuestion> selectByPage(MeFavorites.Query query);

    /**
     * 试题添加收藏
     *
     *
     * @return:
     * @Author: Mingxuan_X
     * @Date: 2021/4/12
     */
     MeFavorites.MeFavoritesOperationResult confirmQuestion(MeFavorites.Query query);


    /**
     * 获取收藏试题总数
     *
     *
     * @return:
     * @Author: Mingxuan_X
     * @Date: 2021/4/17
     */
    Integer selectCount(MeFavorites.Query query);


    /**
     * 判断当前用户试题是否已经收藏
     *
     *
     * @return:
     * @Author: Mingxuan_X
     * @Date: 2021/4/17
     */
    void filterFavaorites(Long userId,List<MeQuestion> list);
}
