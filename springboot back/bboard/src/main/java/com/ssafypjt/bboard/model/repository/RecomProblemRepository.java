package com.ssafypjt.bboard.model.repository;

import com.ssafypjt.bboard.model.dto.RecomProblem;
import io.swagger.v3.oas.annotations.Hidden;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface RecomProblemRepository {

    // 한 유저가 한개의 추천 문제만 줄 수 있게!
    @Select("SELECT id, user_id as userId, group_id as groupId, problem_num as problemNum, tier, title FROM recom_problem WHERE user_id = #{userId} AND group_id = #{groupId}")
    public RecomProblem selectRecomProblem(@Param("userId") int userId, @Param("groupId") int groupId);

    @Select("SELECT id, user_id as userId, group_id as groupId, problem_num as problemNum, tier, title FROM recom_problem WHERE group_id = #{groupId}")
    public List<RecomProblem> selectGroupRecomProblems(@Param("groupId") int groupId);

    @Select("SELECT id, user_id as userId, group_id as groupId, problem_num as problemNum, tier, title FROM recom_problem")
    public List<RecomProblem> selectAllRecomProblems();

    @Insert("INSERT INTO recom_problem (user_id, group_id, problem_num, tier, title) VALUES (#{userId}, #{groupId}, #{problemNum}, #{tier}, #{title})")
    public int insertRecomProblem(RecomProblem recomProblem);

    @Delete("DELETE FROM recom_problem WHERE group_id = #{groupId}")
    public int deleteGroupRecomProblem(@Param("groupId") int groupId);

    @Delete("DELETE FROM recom_problem")
    public int deleteAll();

    // 사용 계획 없음
    @Update("UPDATE recom_problem SET problem_num = #{problemNum} WHERE id = #{id}")
    public int updateRecomProblem(RecomProblem recomProblem);

}
