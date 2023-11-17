package com.ssafypjt.bboard.model.repository;

import com.ssafypjt.bboard.model.dto.Problem;
import com.ssafypjt.bboard.model.dto.RecomProblem;
import io.swagger.v3.oas.annotations.Hidden;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface ProblemRepository {

    @Select("SELECT id, user_id as userId, problem_num as problemNum, tier, title FROM problem") // * 수정
    public List<Problem> selectAllProblems();

    @Select("SELECT id, user_id as userId, problem_num as problemNum, tier, title FROM problem WHERE id = #{id}") // * 수정
    public Problem selectProblem(int id);

    @Select("SELECT id, user_id as userId, problem_num as problemNum, tier, title FROM tier_problem") // * 수정
    public List<Problem> selectAllTierProblems(int tier, int groupId);

    @Select("SELECT id, user_id as userId, problem_num as problemNum, tier, title FROM recom_problem") // * 수정
    public List<RecomProblem> selectRecomProblems(int groupId);

    @Select("SELECT algorithm from problem_algorithm WHERE problem_num = #{problemNum}")
    public String selectAlgorithm(int problemNum);

    @Delete("DELETE from problem")
    public int deleteAll();

    @Insert("INSERT INTO problem (id, user_id, problem_num, tier, title) VALUES (#{id}, #{userId}, #{problemNum}, #{tier}, #{title})")
    public int insertProblem(Problem problem);

}
