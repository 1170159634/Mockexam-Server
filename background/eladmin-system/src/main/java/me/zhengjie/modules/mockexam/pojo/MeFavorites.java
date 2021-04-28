package me.zhengjie.modules.mockexam.pojo;

import java.time.LocalDateTime;
import java.io.Serializable;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * <p>
 * 试题收藏表
 * </p>
 *
 * @author Ming
 * @since 2021-04-12
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Builder
public class MeFavorites implements Serializable {

    private static final long serialVersionUID = 1L;


    private Long id;
    /**
     * 用户id
     */
    private Long userId;

    /**
     * 问题id
     */
    private Long questionId;

    /**
     * 创建时间
     */
    private LocalDateTime createDate;


    @Data
    @Accessors(chain = true)
    @JsonInclude(JsonInclude.Include.NON_EMPTY)
    public static class Query {
        //第几页
        private Integer page;
        //几条数据
        private Integer size;
        /**
         * 用户id
         */
        private Long userId;

        /**
         * 问题id
         */
        private Long questionId;

        /**
         * 0：添加收藏 1:取消收藏
         */
        private Boolean favorites;
        //试题类型
        private Integer questionType;

        //模糊查询关键词
        private String likeQuestions;
        //科目类型
        private Integer subjectType;
    }


    @Data
    @Accessors(chain = true)
    @JsonInclude(JsonInclude.Include.NON_EMPTY)
    public static class MeFavoritesOperationResult {

        //结果
        private String msg;

    }

}
