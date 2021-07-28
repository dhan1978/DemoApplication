package com.example.SpringMVCExample.dao;

import java.util.List;



import com.example.SpringMVCExample.model.Users;

public interface UserDAO {
	public void addUser(Users p);
	public void updateUser(Users p);
	public List<Users> listUser();
	public Users getUserById(int id);
	public void removeUser(int id);
}
