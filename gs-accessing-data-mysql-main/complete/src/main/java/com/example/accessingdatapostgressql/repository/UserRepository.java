package com.example.accessingdatapostgressql.repository;

import org.springframework.data.repository.CrudRepository;

import com.example.accessingdatapostgressql.entity.Users;



// This will be AUTO IMPLEMENTED by Spring into a Bean called userRepository
// CRUD refers Create, Read, Update, Delete

public interface UserRepository extends CrudRepository<Users, Integer> {

}
