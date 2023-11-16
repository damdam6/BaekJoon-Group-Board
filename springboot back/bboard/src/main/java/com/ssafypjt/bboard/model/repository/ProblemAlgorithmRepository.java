package com.ssafypjt.bboard.model.repository;

import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Param;

public interface ProblemAlgorithmRepository {

    @Delete("DELETE FROM problem_algorithm")
    public int deleteAll();

    @Insert("INSERT IGNORE INTO problem_algorithm (problem_num, algorithm) VALUES (#{problemNum}, #{algorithm})")
    public int insertAlgorithm(@Param("problemNum") int problemNum, @Param("algorithm") String algorithm);

}
