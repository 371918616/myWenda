package com.nowcoder.dao;

import com.nowcoder.model.User;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Component;

/**
 * Created by Administrator on 2018/10/4.
 */
@Mapper
@Component
public interface UserDAO {
    String TABLE_NAME = "user";
    String INSERT_FIELDS = " name , password , salt , head_url ";
    String SELECT_FIELDS = " id , " + INSERT_FIELDS;

    @Insert({"insert into ", TABLE_NAME, "(", INSERT_FIELDS,
            ") values (#{name},#{password},#{salt},#{headUrl})"})
    int addUser(User user);
}
