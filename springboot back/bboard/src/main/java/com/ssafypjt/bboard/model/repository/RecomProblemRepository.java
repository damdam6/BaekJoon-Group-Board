package com.ssafypjt.bboard.model.repository;

import com.ssafypjt.bboard.model.dto.RecomProblem;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

@Mapper
public interface RecomProblemRepository {

    // 한 유저가 한개의 추천 문제만 줄 수 있게!
    @Select("SELECT id, user_id as userId, group_id as groupId,problem_num as problemNum, tier, title FROM recom_problem WHERE user_id = #{userId}")
    public RecomProblem selectRecomProblems(int userId);

    @Insert("INSERT INTO recom_problem (user_id, group_id, problem_num, tier, title) VALUES (#{userId}, #{groupId}, #{problemNum}, #{tier}, #{title})")
    public int insertRecomProblem(RecomProblem recomProblem);

    @Delete("DELETE FROM recom_problem WHERE user_id = #{userID}")
    public int deleteRecomProblem(int userId);

    @Delete("DELETE FROM recom_problem")
    public int deleteAll();



}
