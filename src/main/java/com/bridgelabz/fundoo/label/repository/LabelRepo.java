/**
 * @author Rohit Thorawade
 * @purpose Creating interface labelrepo which extends to Mongo respository
 *
 */
package com.bridgelabz.fundoo.label.repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import com.bridgelabz.fundoo.label.model.Label;

@Repository
public interface LabelRepo extends MongoRepository<Label, String> {

	Label findByEmailId(String emailId);// find all user by emailId
	
	

}
