package com.ssafypjt.bboard.model.repository;

import com.ssafypjt.bboard.model.dto.User;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;

import java.util.List;

@Mapper
public interface UserRepository {

    @Select("SELECT user_id as userId, user_name as userName, tier, solved_rank as solvedRank, img_url as imgUrl\n" +
            " FROM users")
    public List<User> selectAllUser();

    @Select("SELECT user_id as userId, user_name as userName, tier, solved_rank as solvedRank, img_url as imgUrl\n" +
            " FROM users WHERE user_id = #{id}")
    public User selectUser(@Param("id") int id);

    @Insert("INSERT INTO users (user_name, tier, solved_rank, img_url) VALUES (#{userName}, #{tier}, #{solvedRank}, #{imgUrl})")
    public int insertUser(User user);

    @Select("SELECT user_id as userId, user_name as userName, tier, solved_rank as solvedRank, img_url as imgUrl\n\n" +
            " FROM users WHERE user_Name = #{userName}")
    public User selectUserByName(@Param("userName") String userName);


    public int deleteUser(int userId); // 어떻게 구현할껀가..?

    @Select("SELECT group_id as groupId FROM user_group WHERE user_id = #{userId}")
    public List<Integer> selectGroupId(int userId);
}
