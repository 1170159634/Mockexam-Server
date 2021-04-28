package me.zhengjie.modules.mockexam.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import me.zhengjie.modules.mockexam.pojo.MeWrongQuestion;
import org.apache.ibatis.annotations.Param;
import java.util.Map;

/**
 * <p>
 * 用户错题表 Mapper 接口
 * </p>
 *
 * @author Ming
 * @since 2021-04-12
 */
public interface MeWrongQuestionMapper extends BaseMapper<MeWrongQuestion> {

    /**
     * 分页查询当前错题集
     *
     *
     * @return:
     * @Author: Mingxuan_X
     * @Date: 2021/4/12
     */
    String  selectByPage( @Param("map") Map<String, Object> map);


}
