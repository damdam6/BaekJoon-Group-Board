package com.ssafypjt.bboard.model.repository;

import com.ssafypjt.bboard.model.domain.groupinfo.UserAndGroupObjectDomain;
import com.ssafypjt.bboard.model.dto.ProblemAlgorithm;
import io.swagger.v3.oas.annotations.Hidden;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface ProblemAlgorithmRepository {

    @Delete("DELETE FROM problem_algorithm")
    public int deleteAll();

    @Insert("INSERT IGNORE INTO problem_algorithm (problem_num, algorithm) VALUES (#{problemNum}, #{algorithm})")
    public int insertAlgorithm(ProblemAlgorithm problemAlgorithm);

    @Select("SELECT problem_num as problemNum, algorithm FROM problem_algorithm WHERE problem_num = #{problemNum}")
    public ProblemAlgorithm selectAlgorithm(int problemNum);

    @Select("SELECT problem_num as problemNum, algorithm FROM problem_algorithm ORDER BY problem_num ASC")
    public List<ProblemAlgorithm> selectAllAlgorithm();

    @Select("SELECT problem_num as problemNum, algorithm FROM problem_algorithm pa INNER JOIN  WHERE ")
    public List<ProblemAlgorithm> selectGroupAlgorithm(UserAndGroupObjectDomain userAndGroup);

}
