package com.ssafypjt.bboard.model.repository;

import com.ssafypjt.bboard.model.dto.Problem;
import com.ssafypjt.bboard.model.dto.RecomProblem;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface ProblemRepository {

    @Select("SELECT * FROM problem") // * 수정
    public List<Problem> selectAllProblems();

    @Select("SELECT * FROM problem WHERE id = #{id}") // * 수정
    public Problem selectProblem(int id);

    @Select("SELECT * FROM tier_problem") // * 수정
    public List<Problem> selectAllTierProblems(int tier, int groupId);

    @Select("SELECT * FROM recom_problem") // * 수정
    public List<RecomProblem> selectRecomProblems(int groupId);

    @Select("SELECT algorithm from problem_algorithm WHERE problem_num = #{problemNum}")
    public String selectAlgorithm(int problemNum);

    @Delete("DELETE from problem")
    public int deleteAllProblems();

    @Insert("INSERT INTO problem (id, user_id, problem_num, tier, title) VALUES (#{id}, #{userId}, #{problemNum}, #{tier}, #{title})")
    public int insertProblem(Problem problem);

    @Delete("DELETE FROM problem_algorithm")
    public int deleteAllAlgorithms();

    @Insert("INSERT IGNORE INTO problem_algorithm (problem_num, algorithm) VALUES (#{problemNum}, #{algorithm})")
    public int insertAlgorithm(@Param("problemNum") int problemNum, @Param("algorithm") String algorithm);

}
