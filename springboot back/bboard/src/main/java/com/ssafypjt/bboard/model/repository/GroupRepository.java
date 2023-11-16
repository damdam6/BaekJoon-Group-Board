package com.ssafypjt.bboard.model.repository;

import com.ssafypjt.bboard.model.dto.Group;
import com.ssafypjt.bboard.model.dto.User;
import org.apache.ibatis.annotations.*;

@Mapper
public interface GroupRepository {

    // group without adminValidation
    @Select("SELECT id, group_name as groupName, password FROM group WHERE id = #{id}")
    public Group selectGroup(int id);

    // getting adminValidation
    @Select("SELECT id, group_name as groupName, password FROM group WHERE id = #{id}, password = #{password}") // 비번 권한 확인용
    public Group selectGroupByPassword(int id, String password);

    // group with adminValidation
    @Insert("INSERT INTO group (group_name, password) VALUES (#{groupName}, #{password})")
    public int insertGroup(Group group);

    @Delete("DELETE FROM group where id = #{id}")
    public int deleteGroup(int id);

    @Update("UPDATE group SET group_name = #{newName} WHERE id = #{id}")
    public int updateGroupName(int id, String newName);

    @Update("UPDATE group SET password = #{newPassword} WHERE id = #{id}")
    public int updateGroupPassword(int id, String newPassword);


}
