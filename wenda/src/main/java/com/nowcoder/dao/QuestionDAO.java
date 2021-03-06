package com.nowcoder.dao;

import com.nowcoder.model.Question;
import org.apache.ibatis.annotations.*;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * Created by Administrator on 2018/10/5.
 */
@Mapper
@Component
public interface QuestionDAO {
    String TABLE_NAME = " question ";
    String INSERT_FIELDS = " title , content , created_date , user_id , comment_count ";
    String SELECT_FIELDS = " id , " + INSERT_FIELDS;

    @Insert({"insert into ", TABLE_NAME, "(", INSERT_FIELDS,
            ") values (#{title},#{content},#{createdDate},#{userId},#{commentCount})"})
    int addQuestion(Question question);


    //这个是xml配置的 ，com.nowcoder.mapper

    List<Question> selectLatestQuestions(@Param("userId") int userId, @Param("offset") int offset,
                                         @Param("limit") int limit);

    @Select({"select " , SELECT_FIELDS , " from " , TABLE_NAME , " where id = #{id}"})
    Question getById(int id);

    @Update({"update " , TABLE_NAME , "set comment_count = #{commentCount} where id = #{id}"})
    int updateCommentCount(@Param("id") int id , @Param("commentCount") int commentCount);


}
