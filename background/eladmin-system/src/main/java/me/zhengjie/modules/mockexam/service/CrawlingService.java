package me.zhengjie.modules.mockexam.service;


import cn.hutool.core.collection.CollectionUtil;
import com.baomidou.mybatisplus.core.toolkit.IdWorker;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.tobato.fastdfs.domain.fdfs.StorePath;
import lombok.extern.slf4j.Slf4j;
import me.zhengjie.modules.mockexam.config.CrawlingConfig;
import me.zhengjie.modules.mockexam.constant.CommonConstant;
import me.zhengjie.modules.mockexam.constant.enums.QuestionBelongTypeEnum;
import me.zhengjie.modules.mockexam.constant.enums.QuestionTypeEnum;
import me.zhengjie.modules.mockexam.constant.enums.SubjectTypeEnum;
import me.zhengjie.modules.mockexam.pojo.MeQuestion;
import me.zhengjie.modules.mockexam.utils.DockingFileUtils;
import me.zhengjie.modules.mockexam.utils.FastDFSUtil;
import me.zhengjie.modules.mockexam.utils.FileUtil;
import me.zhengjie.modules.mockexam.utils.HttpUtil;
import me.zhengjie.modules.mockexam.utils.JsonUtil;
import me.zhengjie.modules.mockexam.utils.SystemUtil;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.UUID;


/**
 * @author :         Mingxuan_x
 * @version :        1.0
 * @Description: 爬取小葱计算试题，插入到数据库
 * @Telephone :      15135964789
 * @createDate :     2021/4/10 17:30
 * @updateUser :     Mingxuan_x
 * @updateDate :     2021/4/10 17:30
 * @updateRemark :   修改内容
 **/
@Service
@Slf4j
public class CrawlingService {


    @Autowired
    private CrawlingConfig.CrawlingConfigProperties properties;

    @Autowired
    private MeQuestionService meQuestionService;

    @Autowired
    private SystemUtil systemUtil;

    @Autowired
    private FastDFSUtil fastDFSUtil;


    /**
     * 定时处理服务器图片，到本地
     *
     * @return:
     * @Author: Mingxuan_X
     * @Date: 2021/4/11
     */
    public void pic2Local() {
        MeQuestion.Query query = new MeQuestion.Query();
        query.setSize(200);
        for (int i = 1; i < 50; i++) {
            query.setPage(i);
            List<MeQuestion> list = meQuestionService.selectNoProcessingPic(query);
            if (CollectionUtil.isEmpty(list)) {
                log.info("无图片处理到本地!");
                return;
            }
            pic2Local(list);
        }

    }

    /**
     * 处理服务器图片，到本地
     *
     * @return:
     * @Author: Mingxuan_X
     * @Date: 2021/4/11
     */
    public void pic2Local(List<MeQuestion> list) {


        String prefix = systemUtil.getFilePrefix();
        list.forEach(obj -> {
            DockingFileUtils.rebuildFileDirectory(prefix);
            String serverPath = obj.getPic();
            String suffix = serverPath.split("\\.")[3];
            String uuid = UUID.randomUUID().toString();
            String localPath = prefix + uuid + "." + suffix;
            log.info("本地下载地址:{}", localPath);
            if (localPath.contains("/home")) {
                DockingFileUtils.rebuildFile(localPath);
            }
            //(1)下载文件
            boolean download = FileUtil.download(serverPath, localPath);
            if (download) {
                //(2)上转到fastdfs服务器
                try {
                    StorePath path = fastDFSUtil.uploadByLocalFile(localPath);
                    obj.setTargetPic(path.getPath());
                } catch (IOException e) {
                    log.error("fastdfs上传时出错!", e);
                    return;
                }
            } else {
                return;
            }

        });
        log.info("下载完成，准备批量SQL更新");
        meQuestionService.processingPicBatch(list);
    }

    /**
     * 定时获取第三方平台数据
     *
     * @return:
     * @Author: Mingxuan_X
     * @Date: 2021/4/11
     */
    public void getTimingThirdPartyData() throws Exception {
        String models = properties.getModels();
        String[] types = models.split(",");
        //根据试题类型遍历，每个类型获取试题科目一试题和科目四试题，后进行插入
        if (types.length > 0) {
            String body = null;
            for (String model : types) {
                //(1)科目一录入
                properties.setSubject(SubjectTypeEnum.SUBJECT_ONE.getMsg());
                List<MeQuestion> questions = new ArrayList<>();
                properties.setModel(model);
                body = HttpUtil.sendPostForm(properties.getUrl(), properties, null);
                if (StringUtils.isNotBlank(body)) {
                    //处理请求
                    ObjectMapper objectMapper = new ObjectMapper();
                    JsonNode jsonNode = objectMapper.readTree(body).get("result");
                    if (jsonNode.isArray()) {
                        jsonNode.forEach(obj -> {
                            MeQuestion.ThirdPartyResult question = JsonUtil.jsonToPojo(obj.toString(), MeQuestion.ThirdPartyResult.class);
                            if (null != question && !"swf".equals(question.getUrl())) {
                                MeQuestion meQuestion = MeQuestion.builder()
                                        .id(CommonConstant.getUniqueId())
                                        .answer(question.getAnswer())
                                        .questionExplain(question.getExplains())
                                        .option1(question.getItem1())
                                        .option2(question.getItem2())
                                        .option3(question.getItem3())
                                        .option4(question.getItem4())
                                        .questionType(QuestionTypeEnum.getEnumCode(model))
                                        .carType(QuestionBelongTypeEnum.getEnumCode(model))
                                        .pic(question.getUrl())
                                        .question(question.getQuestion())
                                        .subjectType(SubjectTypeEnum.getEnumCode(properties.getSubject()))
                                        .build();
                                questions.add(meQuestion);
                            }

                        });
                    }
                }


                //(2)科目四录入
                properties.setSubject(SubjectTypeEnum.SUBJECT_FOUR.getMsg());
                body = HttpUtil.sendPostForm(properties.getUrl(), properties, null);
                if (StringUtils.isNotBlank(body)) {
                    //处理请求
                    ObjectMapper objectMapper = new ObjectMapper();
                    JsonNode jsonNode = objectMapper.readTree(body).get("result");
                    if (jsonNode.isArray()) {
                        jsonNode.forEach(obj -> {
                            MeQuestion.ThirdPartyResult question = JsonUtil.jsonToPojo(obj.toString(), MeQuestion.ThirdPartyResult.class);
                            if (null != question && !"swf".equals(question.getUrl())) {
                                MeQuestion meQuestion = MeQuestion.builder()
                                        .id(CommonConstant.getUniqueId())
                                        .answer(question.getAnswer())
                                        .questionExplain(question.getExplains())
                                        .option1(question.getItem1())
                                        .option2(question.getItem2())
                                        .option3(question.getItem3())
                                        .option4(question.getItem4())
                                        .questionType(QuestionTypeEnum.getEnumCode(model))
                                        .carType(QuestionBelongTypeEnum.getEnumCode(model))
                                        .pic(question.getUrl())
                                        .question(question.getQuestion())
                                        .subjectType(SubjectTypeEnum.getEnumCode(properties.getSubject()))
                                        .build();
                                questions.add(meQuestion);
                            }

                        });
                    }
                }
                meQuestionService.insetBatch(questions);
                //处理图片
                pic2Local(questions);

            }


        }

    }
}
