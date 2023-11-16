package com.ssafypjt.bboard.model.repository;

import com.ssafypjt.bboard.model.dto.UserTier;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Select;

import java.util.List;

public interface TierProblemRepository {
    @Insert("INSERT INTO tier_problem WHERE user_id = #{userId}")
    public int insertUserTier(int userId);

    @Select("SELECT id, user_id as userId, tier, problem_count as problemCount FROM tier_problem WHERE user_id = #{userId}, tier = #{tier}")
    public List<UserTier> selectUserTiers(int userId);

    @Delete("DELETE from tier_problem")
    public int deleteAll();
}
