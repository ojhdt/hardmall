package cn.edu.scnu.user.mapper;

import com.easymall.common.pojo.User;

public interface NewUserMapper {

	Integer checkUsername(String userName);

	void userSave(User user);

	User queryUserByNameAndPassword(User user);

}
