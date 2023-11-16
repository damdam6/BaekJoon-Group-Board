package com.ssafypjt.bboard.model.repository;

import com.ssafypjt.bboard.model.dto.Group;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

@Mapper
public interface GroupRepository {
    @Select("SELECT * FROM group WHERE id = #{id}")
    public Group selectGroup(int id);

    @Insert("INSERT INTO group (group_name, password) VALUES (#{groupName}, #{password})")
    public int insertGroup(Group group);

    @Delete("DELETE FROM group where id = #{id}")
    public int deleteGroup(int id);

    @Insert("INSERT INTO user_group (user_id, group_id) VALUES (#{userId}, #{id})") // 그룹-유저 관계 삽입
    public int insertUser(int id, int userId);

    @Delete("DELETE FROM user_group WHERE user_id = #{userId}, group_id = #{id}") // 그룹-유저 관계 삭제
    public int removeUser(int id, int userId);

    @Select("SELECT * FROM group WHERE id = #{id}, password = #{password}") // 비번 권한 확인용
    public Group selectGroup(int id, String password);

//    public int updateGroupName(int id, String newName);

//    public int updateGroupPassword(int id, String newPassword);

}
