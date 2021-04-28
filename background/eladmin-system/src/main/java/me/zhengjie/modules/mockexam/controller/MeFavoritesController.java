package me.zhengjie.modules.mockexam.controller;


import lombok.extern.slf4j.Slf4j;
import me.zhengjie.modules.mockexam.pojo.MeFavorites;
import me.zhengjie.modules.mockexam.pojo.MeQuestion;
import me.zhengjie.modules.mockexam.pojo.page.PageData;
import me.zhengjie.modules.mockexam.service.MeFavoritesService;
import me.zhengjie.modules.mockexam.utils.Result;
import me.zhengjie.modules.system.service.UserService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/**
 * <p>
 * 试题收藏表 前端控制器
 * </p>
 *
 * @author Ming
 * @since 2021-04-12
 */
@RestController
@RequestMapping("/api/mockexam/favorites")
@Slf4j
public class MeFavoritesController {


    @Autowired
    private MeFavoritesService meFavoritesService;

    @Autowired
    private UserService userService;

    /**
     * 分页查询收藏问题
     *
     * @return:
     * @Author: Mingxuan_X
     * @Date: 2021/4/11
     */
    @RequestMapping(value = "/selectByPage")
    public ResponseEntity<Object> selectByPage(MeFavorites.Query query) {
        if (null == query.getUserId()) {
            String userName = SecurityContextHolder.getContext().getAuthentication().getName();
            if (StringUtils.isNotBlank(userName)) {
                query.setUserId(userService.findByName(userName).getId());
            } else {
                return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
            }
        }
        try {
            log.info("request:{}", query);
            Map<String, Object> map = new LinkedHashMap<>();
            List<MeQuestion> list = meFavoritesService.selectByPage(query);
            map.put("content", list);
            map.put("totalElements", meFavoritesService.selectCount(query));
            return new ResponseEntity<>(map, HttpStatus.OK);
        } catch (Exception e) {
            log.error("error: ", e);
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    /**
     * 确认/添加收藏
     *
     * @return:
     * @Author: Mingxuan_X
     * @Date: 2021/4/11
     */
    @RequestMapping(value = "/operation")
    public ResponseEntity<Object> confirmQuestion(@RequestBody MeFavorites.Query query) {
        if (null == query.getQuestionId() || null == query.getFavorites()) {
            log.error("入参错误,无法添加收藏!");
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
        if (null == query.getUserId()) {
            String userName = SecurityContextHolder.getContext().getAuthentication().getName();
            if (StringUtils.isNotBlank(userName)) {
                query.setUserId(userService.findByName(userName).getId());
            } else {
                return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
            }
        }
        try {
            log.info("request:{}", query);
            Map<String, Object> map = new LinkedHashMap<>();
            map.put("content", meFavoritesService.confirmQuestion(query));
            map.put("totalElements", null);
            return new ResponseEntity<>(map, HttpStatus.OK);
        } catch (Exception e) {
            log.error("error: ", e);
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }


}
