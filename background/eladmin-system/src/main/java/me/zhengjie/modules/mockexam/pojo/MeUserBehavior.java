package me.zhengjie.modules.mockexam.pojo;

import java.time.LocalDateTime;
import java.io.Serializable;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

/**
 * <p>
 * 用户行为分析表
 * </p>
 *
 * @author Ming
 * @since 2021-04-13
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class MeUserBehavior implements Serializable {

    private static final long serialVersionUID = 1L;

    private Long id;

    private Long userId;

    /**
     * 最后一次登录时间
     */
    private LocalDateTime updateDate;

    /**
     * 首次登录时间
     */
    private LocalDateTime createDate;

    /**
     * 错题次数
     */
    private Integer wrongNum;


    /**
     * 错题最多类型
     */

    private Integer maximumQuestionType;


    /**
     * 错题最多客观题类型
     */

    private Integer maximumObjectiveType;


    private Integer isRead;

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
        //第几页
        private Integer page;
        //几条数据
        private Integer size;
        private Long userId;

    }


    @Data
    @Accessors(chain = true)
    @JsonInclude(JsonInclude.Include.NON_EMPTY)
    public static class userBehaviorResult{
        private Integer maximumObjectiveType;
        private Integer maximumQuestionType;
        private Integer wrongCount;

    }
}
