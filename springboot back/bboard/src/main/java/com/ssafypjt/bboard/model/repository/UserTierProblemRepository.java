package com.ssafypjt.bboard.model.repository;

import com.ssafypjt.bboard.model.dto.Problem;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface UserTierProblemRepository {

    // user_tier_problem에서 갱신을 어떻게 할 거지, 다 지우고 새로 받아올껀가 아니면 변경된 애들만?
    @Select("SELECT id, user_id as userId, tier, problem_num as problemTitle, title FROM user_tier_problem WHERE tier = #{tier}")
    public List<Problem> selectTierProblems(int tier);
    @Insert("INSERT INTO user_tier_problem (user_id, tier, problem_num, title) VALUES (#{userId}, #{tier}, #{problemNum}, #{title}) ")
    public int insertTierProblem(Problem problem);
    @Delete("DELETE FROM user_tier_problem")
    public int deleteAll();

}
