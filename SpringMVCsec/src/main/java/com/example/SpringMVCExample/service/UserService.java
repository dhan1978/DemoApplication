package com.example.SpringMVCExample.service;

import java.util.List;

import com.example.SpringMVCExample.model.Users;




public interface UserService {
	public void addUser(Users p);
	public void updateUser(Users p);
	public List<Users> listUsers();
	public Users getUserById(int id);
	public void removeUser(int id);
}
