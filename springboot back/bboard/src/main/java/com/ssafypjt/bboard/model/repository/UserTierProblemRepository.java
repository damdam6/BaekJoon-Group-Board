package com.ssafypjt.bboard.model.repository;

import com.ssafypjt.bboard.model.domain.solvedacAPI.ProblemAndAlgoObjectDomain;
import com.ssafypjt.bboard.model.dto.Problem;
import com.ssafypjt.bboard.model.dto.User;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface UserTierProblemRepository {

    // user_tier_problem에서 갱신을 어떻게 할 거지, 다 지우고 새로 받아올껀가 아니면 변경된 애들만?
    @Select("SELECT id, user_id as userId, tier, problem_num as problemTitle, title FROM user_tier_problem WHERE tier = #{tier}")
    public List<Problem> selectTierProblems(@Param("userId") int userId);


    @Select({
            "<script>",
            "SELECT id, user_id as userId, problem_num as problemNum, tier, title FROM user_tier_problem",
            "WHERE user_id IN",
            "<foreach item='user' collection='users' open='(' separator=',' close=')'>",
            "#{user.userId}",
            "</foreach>",
            "</script>"
    })
    public List<Problem> selectGroupTierProblem(@Param("users") List<User> user);

    @Insert("INSERT INTO user_tier_problem (user_id, tier, problem_num, title) VALUES (#{userId}, #{tier}, #{problemNum}, #{title})")
    public int insertTierProblem(Problem problem);

    @Insert({
            "<script>",
            "INSERT INTO user_tier_problem (user_id, tier, problem_num, title) VALUES ",
            "<foreach item='item' collection='list' separator=','>",
            "(#{item.problem.userId}, #{item.problem.tier}, #{item.problem.problemNum}, #{item.problem.title})",
            "</foreach>",
            "</script>"
    })
    public int insertTierProblems(List<ProblemAndAlgoObjectDomain> list);
    @Delete("DELETE FROM user_tier_problem")
    public int deleteAll();

}
