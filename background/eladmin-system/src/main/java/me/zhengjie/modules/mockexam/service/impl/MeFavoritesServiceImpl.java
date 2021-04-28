package me.zhengjie.modules.mockexam.service.impl;


import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.collection.CollectionUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import me.zhengjie.modules.mockexam.constant.CommonConstant;
import me.zhengjie.modules.mockexam.constant.enums.FavoritesTypeEnum;
import me.zhengjie.modules.mockexam.mapper.MeFavoritesMapper;
import me.zhengjie.modules.mockexam.pojo.MeFavorites;
import me.zhengjie.modules.mockexam.pojo.MeQuestion;
import me.zhengjie.modules.mockexam.service.MeFavoritesService;
import me.zhengjie.modules.mockexam.service.MeQuestionService;
import me.zhengjie.modules.system.service.UserService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

/**
 * <p>
 * 试题收藏表 服务实现类
 * </p>
 *
 * @author Ming
 * @since 2021-04-12
 */
@Service
public class MeFavoritesServiceImpl extends ServiceImpl<MeFavoritesMapper, MeFavorites> implements MeFavoritesService {


    @Resource
    private MeFavoritesMapper meFavoritesMapper;

    @Resource
    private MeQuestionService meQuestionService;

    @Resource
    private UserService userService;

    /**
     * 分页查询用户收藏的题
     *
     * @return:
     * @Author: Mingxuan_X
     * @Date: 2021/4/12
     */
    @Override
    public List<MeQuestion> selectByPage(MeFavorites.Query query) {
        Page page = CommonConstant.queryLimitBuild(query.getPage(), query.getSize());
        Map<String, Object> map = BeanUtil.beanToMap(query);
        List<MeQuestion> list = meFavoritesMapper.selectByPage(page, map);
        this.filterFavaorites(query.getUserId(),list);
        return list;
    }

    /**
     * 确认收藏
     *
     * @return:
     * @Author: Mingxuan_X
     * @Date: 2021/4/12
     */
    @Override
    public MeFavorites.MeFavoritesOperationResult confirmQuestion(MeFavorites.Query query) {
        Long id = query.getQuestionId();
        MeQuestion meQuestion = meQuestionService.selectById(id);
        if (null == meQuestion) {
            log.error("未找到该试题");
            return null;
        }
        MeFavorites.MeFavoritesOperationResult operationResult = new MeFavorites.MeFavoritesOperationResult();
        boolean flag = query.getFavorites();
        if (flag) {
            MeFavorites favorites = MeFavorites.builder()
                    .id(CommonConstant.getUniqueId())
                    .userId(query.getUserId())
                    .questionId(query.getQuestionId())
                    .createDate(LocalDateTime.now())
                    .build();
            meFavoritesMapper.insert(favorites);
            operationResult.setMsg("收藏成功!");
        } else {
            meFavoritesMapper.delete(new QueryWrapper<MeFavorites>()
                    .eq("question_id", query.getQuestionId())
                    .eq("user_id", query.getUserId())
            );
            operationResult.setMsg("取消收藏成功!");
        }


        return operationResult;
    }

    /**
     * 获取收藏试题总数
     *
     * @return:
     * @Author: Mingxuan_X
     * @Date: 2021/4/17
     */
    @Override
    public Integer selectCount(MeFavorites.Query query) {
        Map<String, Object> map = BeanUtil.beanToMap(query);
        return meFavoritesMapper.selectQuestionCount(map);
    }

    /**
     * 过滤返回的试题是否用户已经收藏
     *
     * @return:
     * @Author: Mingxuan_X
     * @Date: 2021/4/17
     */
    @Override
    public void filterFavaorites(Long id, List<MeQuestion> list) {
        if (CollectionUtil.isEmpty(list)) {
            return;
        }
        if (null == id) {
            String userName = SecurityContextHolder.getContext().getAuthentication().getName();
            id = userService.findByName(userName).getId();
        }
        final Long finalUserId = id;
        list.forEach(obj -> {
            MeFavorites favorites = this.getOne(new LambdaQueryWrapper<MeFavorites>()
                    .eq(MeFavorites::getQuestionId, obj.getId())
                    .eq(MeFavorites::getUserId, finalUserId));
            if (favorites == null) {
                obj.setIsMyFavorites(false);
            } else {
                obj.setIsMyFavorites(true);
            }
        });


    }


}

