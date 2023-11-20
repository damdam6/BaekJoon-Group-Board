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

    @Select("SELECT id, user_id as userId, tier, problem_count as problemCount FROM tier_problem")
    public List<UserTier> selectAllUserTiers();

    @Select("SELECT id, user_id as userId, tier, problem_count as problemCount FROM tier_problem WHERE user_id = #{userId}")
    public List<UserTier> selectUserTiers(int userId);

    @Insert("INSERT INTO tier_problem (user_id, tier, problem_count, page_no, page_idx) VALUES (#{userId}, #{tier}, #{problemCount}, #{pageNo}, #{pageIdx})")
    public int insertUserTier(UserTier userTier);


    @Delete("DELETE from tier_problem")
    public int deleteAll();
}
