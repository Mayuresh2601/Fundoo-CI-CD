/**
 * @author Rohit Thorawade
 * @Purpose Extending Interface to the mongoRespository
 *
 */
package com.bridgelabz.fundoo.repository;

import java.util.Optional;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import com.bridgelabz.fundoo.model.User;

@Repository
public interface UserRepo extends MongoRepository<User, String> {

	Optional<User> findById(String id);// Find User By Using Id

	public User findByEmailId(String emailId);// Find User by emailId

}
