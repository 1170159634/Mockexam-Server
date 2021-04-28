package me.zhengjie.modules.mockexam.pojo;

import com.baomidou.mybatisplus.annotation.TableField;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import java.io.Serializable;

/**
 * <p>
 * 模拟试题表
 * </p>
 *
 * @author Ming
 * @since 2021-04-10
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class MeQuestion implements Serializable {

    private static final long serialVersionUID = 1L;
    @JsonSerialize(using = ToStringSerializer.class)
    private Long id;

    /**
     * 选项1
     */
    private String option1;

    /**
     * 选项2
     */
    private String option2;

    /**
     * 选项3
     */
    private String option3;

    /**
     * 选项4
     */
    private String option4;

    /**
     * 答案 (1 2 3 4 对应option(n))
     */
    private String answer;

    /**
     * 图片地址
     */
    private String pic;

    /**
     * 问题
     */
    private String question;

    /**
     * 描述
     */
    private String questionExplain;

    /**
     * 1:科目一  4:科目4
     */
    private Integer subjectType;

    /**
     * 1:小车 2:货车 3:客车
     */
    private Integer carType;

    /**
     * 1:C1  2:C2  3:B2  4:A3
     */
    private Integer questionType;

    /**
     * 服务器图片地址
     */
    private String targetPic;

    @TableField(exist = false)
    private Boolean isMyFavorites;


    /**
     * 试题查询入参
     *
     * @return:
     * @Author: Mingxuan_X
     * @Date: 2021/4/12
     */
    @Data
    @Accessors(chain = true)
    @JsonInclude(JsonInclude.Include.NON_EMPTY)
    public static class Query {
        //试题id
        private Long id;
        //第几页
        private Integer page;
        //几条数据
        private Integer size;
        //科目类型
        private Integer subjectType;
        //车类型
        private Integer carType;
        //试题类型
        private Integer questionType;
        //选题结果 例如:选择ABC 则代表123
        private String chooseAnswerResult;
        //是否随机获取题 1:是
        private Integer isRandom;
        //用户id
        private Long userId;

        //是否属于综合测评 0:否  1:是
        private Integer isExam;

        //客观题类型  0 单选题 1 判断题  2 多选题
        private Integer objectiveType;

        //模糊查询关键词
        private String likeQuestions;

    }

    /**
     * 调用第三方平台返回结果
     *
     * @return:
     * @Author: Mingxuan_X
     * @Date: 2021/4/12
     */
    @Data
    @Accessors(chain = true)
    public static class ThirdPartyResult {
        private Long id;
        private String question;
        private String answer;
        private String item1;
        private String item2;
        private String item3;
        private String item4;
        private String explains;
        private String url;
    }

    /**
     * 用户选择答案反参
     *
     * @return:
     * @Author: Mingxuan_X
     * @Date: 2021/4/12
     */
    @Data
    @Accessors(chain = true)
    public static class chooseAnswerResult {
        //选择结果
        private String result;
        //题目详细描述
        private String explain;
        //0 正确 1 错误
        private Integer isError;

    }
}
