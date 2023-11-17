package com.ssafypjt.bboard.model.repository;

import com.ssafypjt.bboard.model.dto.UserTier;
import io.swagger.v3.oas.annotations.Hidden;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface TierProblemRepository {
    @Insert("INSERT INTO tier_problem (user_id, tier, problem_count, page_no) VALUES (userId, tier, problemCount, pageNo)")
    public int insertUserTier(UserTier userTier);

    @Select("SELECT id, user_id as userId, tier, problem_count as problemCount FROM tier_problem WHERE user_id = #{userId}, tier = #{tier}")
    public List<UserTier> selectUserTiers(int userId);

    @Delete("DELETE from tier_problem")
    public int deleteAll();
}
