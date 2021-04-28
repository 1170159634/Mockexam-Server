package me.zhengjie.modules.mockexam.controller;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import me.zhengjie.annotation.Log;
import me.zhengjie.modules.mockexam.pojo.MeQuestion;
import me.zhengjie.modules.mockexam.service.MeQuestionService;
import me.zhengjie.modules.mockexam.utils.Result;
import me.zhengjie.modules.system.service.UserService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * @author :         Mingxuan_x
 * @version :        1.0
 * @Description:
 * @Telephone :      15135964789
 * @createDate :     2021/4/11 0:34
 * @updateUser :     Mingxuan_x
 * @updateDate :     2021/4/11 0:34
 * @updateRemark :   修改内容
 **/
@RestController
@RequiredArgsConstructor
@Api(tags = "试题：1334")
@RequestMapping("/api/mockexam/question")
@Slf4j
public class MeQuestionController {

    @Autowired
    private MeQuestionService meQuestionService;

    @Autowired
    private UserService userService;

    @RequestMapping(value = "/hello")
    public String hello() {
        return "helloworld";
    }


    /**
     * 分页查询模拟试题
     *
     * @return:
     * @Author: Mingxuan_X
     * @Date: 2021/4/11
     */
    @Log("分页查询")
    @ApiOperation("分页查询")
    @GetMapping
    public ResponseEntity<Object> selectByPage(MeQuestion.Query query) {
        try {
            log.info("request:{}", query);
            Map<String, Object> map = new LinkedHashMap<>();
            List<MeQuestion> list = meQuestionService.selectByPage(query);
            map.put("content", list);
            map.put("totalElements", meQuestionService.selectCount(query));
            return new ResponseEntity<>(map, HttpStatus.OK);
        } catch (Exception e) {
            log.error("error: ", e);
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    /**
     * 插入模拟试题
     *
     * @return:
     * @Author: Mingxuan_X
     * @Date: 2021/4/11
     */
    @Log("插入试题")
    @ApiOperation("插入试题")
    @PostMapping
    public ResponseEntity<Object> addQuestion(@RequestBody MeQuestion resources) {
        try {
            log.info("request:{}", resources);
            Map<String, Object> map = new LinkedHashMap<>();
            map.put("content", meQuestionService.addQuestion(resources));
            map.put("totalElements", null);
            return new ResponseEntity<>(map, HttpStatus.OK);
        } catch (Exception e) {
            log.error("error: ", e);
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    /**
     * 修改试题
     *
     * @return:
     * @Author: Mingxuan_X
     * @Date: 2021/4/11
     */
    @Log("修改试题")
    @ApiOperation("修改试题")
    @PutMapping
    public ResponseEntity<Object> updateQuestion(@RequestBody MeQuestion resources) {
        try {
            log.info("request:{}", resources);
            Map<String, Object> map = new LinkedHashMap<>();
            map.put("content", meQuestionService.updateQuestion(resources));
            map.put("totalElements", null);
            return new ResponseEntity<>(map, HttpStatus.OK);
        } catch (Exception e) {
            log.error("error: ", e);
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    /**
     * 修改试题图片
     *
     * @return:
     * @Author: Mingxuan_X
     * @Date: 2021/4/11
     */
    @Log("修改试题图片")
    @ApiOperation("修改试题图片")
    @RequestMapping("/updateQuestionPic")
    public ResponseEntity<Object> updateQuestionPic(@RequestParam(value = "avatar") MultipartFile file) {
        try {
            Map<String, Object> map = new LinkedHashMap<>();
            map.put("content", meQuestionService.updateQuestionPic(file));
            map.put("totalElements", null);
            return new ResponseEntity<>(map, HttpStatus.OK);
        } catch (Exception e) {
            log.error("error: ", e);
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    /**
     * 删除试题
     *
     * @return:
     * @Author: Mingxuan_X
     * @Date: 2021/4/11
     */
    @Log("删除试题")
    @ApiOperation("删除试题")
    @DeleteMapping
    public ResponseEntity<Object> deleteQuestion(@RequestBody Set<Long> ids) {
        try {
            log.info("request:{}", ids);
            Map<String, Object> map = new LinkedHashMap<>();
            map.put("content", meQuestionService.deleteQuestion(ids));
            map.put("totalElements", null);
            return new ResponseEntity<>(map, HttpStatus.OK);
        } catch (Exception e) {
            log.error("error: ", e);
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }


    /**
     * 按照客观题型查询题
     *
     * @return:
     * @Author: Mingxuan_X
     * @Date: 2021/4/11
     */
    @RequestMapping(value = "/selectByObjectiveType")
    public Result selectByObjectiveType(MeQuestion.Query query) {
        Integer type = query.getObjectiveType();
        if (null == type) {
            log.error("请输入客观题类型 id:{}", type);
            return null;
        }
        try {
            log.info("request:{}", query);
            List<Long> list = meQuestionService.selectByObjectiveType(query);
            return new Result<>().ok(list);
        } catch (Exception e) {
            log.error("error: ", e);
            return new Result<>().error(500, e.getMessage());
        }
    }

    /**
     * 根据id查询试题
     *
     * @return
     */
    @RequestMapping(value = "/selectById")
    public ResponseEntity<Object> selectById(@RequestParam("id") Long id) {
        Long vaid = id;
        if (null == vaid || vaid < 0) {
            log.error("查询id不合法! id:{}", vaid);
            return null;
        }
        try {
            log.info("request:{}", vaid);
            Map<String, Object> map = new LinkedHashMap<>();
            map.put("content", meQuestionService.selectById(vaid));
            map.put("totalElements", null);
            return new ResponseEntity<>(map, HttpStatus.OK);
        } catch (Exception e) {
            log.error("error: ", e);
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    /**
     * 随机获取一个试题
     *
     * @param query
     * @return
     */
    @RequestMapping(value = "/selectRandomQuestion")
    public Result selectRandomQuestion(MeQuestion.Query query) {
        if (null == query.getIsRandom()) {
            return new Result<>().error("入参不正确，无法查询随机题!");
        }
        try {
            log.info("request:{}", query);
            MeQuestion meQuestion = meQuestionService.selectRandomQuestion(query);
            log.info("response:{}", meQuestion);
            return new Result<>().ok(meQuestion);
        } catch (Exception e) {
            log.error("error: ", e);
            return new Result<>().error(500, e.getMessage());
        }
    }

    /**
     * 综合测评:随机获取100道题
     *
     * @return:
     * @Author: Mingxuan_X
     * @Date: 2021/4/11
     */
    @RequestMapping(value = "/selectExamQuestion")
    public ResponseEntity<Object> selectExamQuestion(@RequestParam(value = "subjectType", required = true) Integer subjectType,
                                                     @RequestParam(value = "carType", required = true) Integer carType,
                                                     @RequestParam(value = "likeQuestions", required = false) String likeQuestions,
                                                     @RequestParam(value = "isRandom", required = false) Integer isRandom,
                                                     @RequestParam(value = "objectiveType", required = false) Integer objectType) {
        try {
            MeQuestion.Query query = new MeQuestion.Query();
            query.setObjectiveType(objectType);
            query.setSubjectType(subjectType);
            query.setCarType(carType);
            query.setIsRandom(isRandom);
            query.setLikeQuestions(likeQuestions);
            log.info("request:{}", query);
            Map<String, Object> map = new LinkedHashMap<>();
            List<Long> list = meQuestionService.selectExamQuestion(query);
            map.put("content", list);
            map.put("totalElements", list.size());
            return new ResponseEntity<>(map, HttpStatus.OK);
        } catch (Exception e) {
            log.error("error: ", e);
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    /**
     * 获取试题全部id
     *
     * @return:
     * @Author: Mingxuan_X
     * @Date: 2021/4/11
     */
    @RequestMapping(value = "/selectIds")
    public ResponseEntity<Object> selectIds(@RequestParam(value = "subjectType", required = false) Integer subjectType,
                                            @RequestParam(value = "carType", required = false) Integer carType,
                                            @RequestParam(value = "likeQuestions", required = false) String likeQuestions,
                                            @RequestParam(value = "isRandom", required = false) Integer isRandom,
                                            @RequestParam(value = "objectiveType", required = false) Integer objectType) {
        try {
            MeQuestion.Query query = new MeQuestion.Query();
            query.setObjectiveType(objectType);
            query.setSubjectType(subjectType);
            query.setCarType(carType);
            query.setIsRandom(isRandom);
            query.setLikeQuestions(likeQuestions);
            log.info("request:{}", query);
            Map<String, Object> map = new LinkedHashMap<>();
            List<Long> ids = meQuestionService.selectIds(query);
            map.put("content", ids);
            map.put("totalElements", ids.size());
            return new ResponseEntity<>(map, HttpStatus.OK);
        } catch (Exception e) {
            log.error("error: ", e);
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }


    /**
     * 选择试题答案
     *
     * @return
     */
    @RequestMapping(value = "/chooseAnswer")
    public ResponseEntity<Object> chooseAnswer(@RequestParam("answer") String answer,
                                               @RequestParam("id") Long id) {
        MeQuestion.Query query = new MeQuestion.Query();
        query.setId(id);
        query.setChooseAnswerResult(answer);
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
            map.put("content", meQuestionService.chooseAnswer(query));
            map.put("totalElements", null);
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
    public Result selectCount(MeQuestion.Query query) {
        try {
            log.info("request:{}", query);
            Integer count = meQuestionService.selectCount(query);
            log.info("response:{}", count);
            return new Result<>().ok(count);
        } catch (Exception e) {
            log.error("error: ", e);
            return new Result<>().error(500, e.getMessage());
        }
    }

}
