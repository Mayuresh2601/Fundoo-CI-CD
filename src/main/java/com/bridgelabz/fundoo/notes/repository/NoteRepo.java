/**
 * @author Rohit Thorawade
 * @Purpose Extending the interface with mongoRespository
 *
 */
package com.bridgelabz.fundoo.notes.repository;

import java.util.List;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import com.bridgelabz.fundoo.notes.model.Note;

@Repository
public interface NoteRepo extends MongoRepository<Note, String> {

	List<Note> findByEmailId(String emailId);// Find User by emailId

	List<Note> findAllById(String id);// Find note using Id

	Object findByIdAndEmailId(String noteId, String emailId);

}
