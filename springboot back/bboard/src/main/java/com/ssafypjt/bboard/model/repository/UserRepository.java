package com.ssafypjt.bboard.model.repository;

import com.ssafypjt.bboard.model.dto.User;
import io.swagger.v3.oas.annotations.Hidden;
import org.apache.ibatis.annotations.*;
import org.springframework.stereotype.Repository;

import java.util.List;

@Mapper
public interface UserRepository {

    @Select("SELECT user_id as userId, user_name as userName, tier, solved_rank as solvedRank, img_url as imgUrl\n" +
            " FROM users")
    public List<User> selectAllUser();

    @Select("SELECT user_id as userId, user_name as userName, tier, solved_rank as solvedRank, img_url as imgUrl\n" +
            " FROM users WHERE user_id = #{userId}")
    public User selectUser(@Param("userId") int userId);

    @Insert("INSERT INTO users (user_name, tier, solved_rank, img_url) VALUES (#{userName}, #{tier}, #{solvedRank}, #{imgUrl})")
    public int insertUser(User user);

    @Delete("DELETE FROM users WHERE user_id = #{userId}")
    public int deleteUser(int userId);

    @Select("SELECT user_id as userId, user_name as userName, tier, solved_rank as solvedRank, img_url as imgUrl\n\n" +
            " FROM users WHERE user_Name = #{userName}")
    public User selectUserByName(@Param("userName") String userName);

    //    public int deleteUser(int userId); // (안하기로)

}
