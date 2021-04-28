package me.zhengjie.modules.mockexam.controller;

import lombok.extern.slf4j.Slf4j;
import me.zhengjie.modules.mockexam.pojo.MeWrongQuestion;
import me.zhengjie.modules.mockexam.service.MeWrongQuestionService;
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
 * 用户错题表 前端控制器
 * </p>
 *
 * @author Ming
 * @since 2021-04-12
 */
@RestController
@RequestMapping("/api/mockexam/wrongQuestion")
@Slf4j
public class MeWrongQuestionController {

    @Autowired
    private MeWrongQuestionService meWrongQuestionService;

    @Autowired
    private UserService userService;

    /**
     * 分页查询错题集
     *
     * @return:
     * @Author: Mingxuan_X
     * @Date: 2021/4/11
     */
    @RequestMapping(value = "/selectByPage")
    public ResponseEntity<Object> selectByPage(MeWrongQuestion.Query query) {
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
            Map<String, Object> map = new LinkedHashMap<>();
            map.put("content", meWrongQuestionService.selectByPage(query));
            map.put("totalElements", meWrongQuestionService.selectCount(query));

            return new ResponseEntity<>(map, HttpStatus.OK);
        } catch (Exception e) {
            log.error("error: ", e);
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }


}
