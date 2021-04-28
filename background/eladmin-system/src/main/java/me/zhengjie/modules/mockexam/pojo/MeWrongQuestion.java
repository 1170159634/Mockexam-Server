package me.zhengjie.modules.mockexam.pojo;

import java.time.LocalDateTime;
import java.io.Serializable;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

/**
 * <p>
 * 用户错题表
 * </p>
 *
 * @author Ming
 * @since 2021-04-12
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class MeWrongQuestion implements Serializable {

    private static final long serialVersionUID = 1L;


    private Long id;
    /**
     * 问题id
     */
    private String questions;

    /**
     * 用户id
     */
    private Long userId;

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
        //试题类型
        private Integer questionType;

        //模糊查询关键词
        private String likeQuestions;
        //科目类型
        private Integer subjectType;

    }



}
