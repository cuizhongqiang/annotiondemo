package com.dn.dao;

import java.util.List;

import org.apache.ibatis.annotations.CacheNamespace;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import com.dn.model.User;


@Mapper
@CacheNamespace
public interface UserDao {

	@Insert("insert t_user(id,name) values(#{id},#{userName})")
	void insert(User u);

	@Update("update t_user set name = #{userName} where id=#{id} ")
	void update(User u);

	@Delete("delete from t_user where id=#{id} ")
	void delete(@Param("id") String id);

	@Select("select id,name from t_user where id=#{id} ")
	User find(@Param("id") String id);

	List<User> query(@Param("userName") String userName);

}
