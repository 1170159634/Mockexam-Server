package me.zhengjie.modules.mockexam.controller;


import lombok.extern.slf4j.Slf4j;
import me.zhengjie.modules.mockexam.pojo.MeUserBehavior;
import me.zhengjie.modules.mockexam.service.MeUserBehaviorService;
import me.zhengjie.modules.mockexam.utils.Result;
import me.zhengjie.modules.system.service.UserService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.LinkedHashMap;
import java.util.Map;

/**
 * <p>
 * 用户行为分析表 前端控制器
 * </p>
 *
 * @author Ming
 * @since 2021-04-13
 */
@RestController
@RequestMapping("/api/mockexam/user/behavior")
@Slf4j
public class MeUserBehaviorController {


    @Autowired
    private MeUserBehaviorService meUserBehaviorService;


    @Autowired
    private UserService userService;

    @RequestMapping(value = "/updateLoginTime")
    public ResponseEntity<Object> updateLoginTime() {
        Long userId;
        //如果未传userId，直接根据token从security获取
        String userName = SecurityContextHolder.getContext().getAuthentication().getName();
        if (StringUtils.isNotBlank(userName)) {
            userId = userService.findByName(userName).getId();
        } else {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
        try {
            log.info("request:{}", userId);
            meUserBehaviorService.updateLoginTime(userId);
            return new ResponseEntity<>(HttpStatus.OK);
        } catch (Exception e) {
            log.error("error: ", e);
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }

    }

    /**
     * 判断用户离开该页面多少天了
     *
     * @return:
     * @Author: Mingxuan_X
     * @Date: 2021/4/11
     */
    @RequestMapping(value = "/lastLoginTime")
    public ResponseEntity<Object> lastLoginTime() {

        Long userId;
        //如果未传userId，直接根据token从security获取
        String userName = SecurityContextHolder.getContext().getAuthentication().getName();
        if (StringUtils.isNotBlank(userName)) {
            userId = userService.findByName(userName).getId();
        } else {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }

        try {
            log.info("request:{}", userId);
            Map<String, Object> map = new LinkedHashMap<>();
            map.put("content", meUserBehaviorService.lastLoginTime(userId));
            map.put("totalElements", null);
            return new ResponseEntity<>(map, HttpStatus.OK);
        } catch (Exception e) {
            log.error("error: ", e);
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    /**
     * 检查用户是否已读该推荐信息
     *
     * @return:
     * @Author: Mingxuan_X
     * @Date: 2021/4/11
     */
    @RequestMapping(value = "/judgeIsRead")
    public ResponseEntity<Object> judgeIsRead() {
        Long userId;
        //如果未传userId，直接根据token从security获取

        String userName = SecurityContextHolder.getContext().getAuthentication().getName();
        if (StringUtils.isNotBlank(userName)) {
            userId = userService.findByName(userName).getId();
        } else {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }

        try {
            log.info("request:{}", userId);
            Map<String, Object> map = new LinkedHashMap<>();
            map.put("content", meUserBehaviorService.judgeIsRead(userId));
            map.put("totalElements", null);
            return new ResponseEntity<>(map, HttpStatus.OK);
        } catch (Exception e) {
            log.error("error: ", e);
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }


    /**
     * 确认已读
     *
     * @return:
     * @Author: Mingxuan_X
     * @Date: 2021/4/11
     */
    @RequestMapping(value = "/confirmRead")
    public ResponseEntity<Object> confirmRead() {
        Long userId;
        //如果未传userId，直接根据token从security获取

        String userName = SecurityContextHolder.getContext().getAuthentication().getName();
        if (StringUtils.isNotBlank(userName)) {
            userId = userService.findByName(userName).getId();
        } else {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }

        try {
            log.info("request:{}", userId);
            meUserBehaviorService.confirmRead(userId);
            Map<String, Object> map = new LinkedHashMap<>();
            map.put("content", null);
            map.put("totalElements", null);
            return new ResponseEntity<>(map, HttpStatus.OK);
        } catch (Exception e) {
            log.error("error: ", e);
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    /**
     * 为您推荐题 ids
     *
     * @return:
     * @Author: Mingxuan_X
     * @Date: 2021/4/11
     */
    @RequestMapping(value = "/selectByPage")
    public ResponseEntity<Object> selectByPage(MeUserBehavior.Query query) {
        //如果未传userId，直接根据token从security获取
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
            map.put("content", meUserBehaviorService.selectByPage(query));
            map.put("totalElements", meUserBehaviorService.selectCount(query));
            return new ResponseEntity<>(map, HttpStatus.OK);
        } catch (Exception e) {
            log.error("error: ", e);
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    /**
     * 查询试题总数
     *
     * @param query
     * @return
     */
    @RequestMapping(value = "/selectCount")
    public Result selectCount(MeUserBehavior.Query query) {
        try {
            log.info("request:{}", query);
            Integer count = meUserBehaviorService.selectCount(query);
            log.info("response:{}", count);
            return new Result<>().ok(count);
        } catch (Exception e) {
            log.error("error: ", e);
            return new Result<>().error(500, e.getMessage());
        }
    }
}
