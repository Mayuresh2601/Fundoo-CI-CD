/**
 * @author Rohit Thorawade
 * @Purpose Performing crud operations in following service class
 *
 */
package com.bridgelabz.fundoo.notes.service;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import com.bridgelabz.fundoo.exception.NoteException;
import com.bridgelabz.fundoo.exception.NullException;
import com.bridgelabz.fundoo.jwt.utility.Jwt;
import com.bridgelabz.fundoo.label.repository.LabelRepo;
import com.bridgelabz.fundoo.model.User;
import com.bridgelabz.fundoo.notes.dto.Collaboratorsdto;
import com.bridgelabz.fundoo.notes.dto.Notedto;
import com.bridgelabz.fundoo.notes.model.Note;
import com.bridgelabz.fundoo.notes.repository.NoteRepo;
import com.bridgelabz.fundoo.repository.UserRepo;
import com.bridgelabz.fundoo.response.Response;

@Service

public class NoteImpl implements NoteOperations {

	@Autowired
	NoteRepo noterepo;

	@Autowired
	Jwt jwt;

	@Autowired
	LabelRepo labelrepo;

	@Autowired
	UserRepo userrepo;

	@Autowired
	Environment noteenv;

	/**
	 * Creating note for user
	 */
	public Response createNote(Notedto notedto, String token) {

		String emailId = jwt.getUserToken(token);
		User user = userrepo.findByEmailId(emailId);

		if (emailId != null) {

			ModelMapper mapper = new ModelMapper();
			Note note = mapper.map(notedto, Note.class);

			LocalDateTime now = LocalDateTime.now();

			note.setCreatedate(now);
			note.setEmailId(emailId);
			noterepo.save(note);

			user.getNotelist().add(note);
			userrepo.save(user);

			return new Response(200, (noteenv.getProperty("CREATE_NOTE")), HttpStatus.OK);
		}
		return new Response(400, noteenv.getProperty("UNAUTHORIZED_USER"), HttpStatus.BAD_REQUEST);

	}

	/**
	 * Update Note for user
	 */
	@Override
	public Response updateNote(Notedto notedto, String id, String token) {

		String emailId = jwt.getUserToken(token);
		User user = userrepo.findByEmailId(emailId);

		if (emailId != null) {


			Note notefound = (Note) noterepo.findByIdAndEmailId(id, emailId);
			System.out.println(notefound);
			if (notefound != null) {
				notefound.setTitle(notedto.getTitle());
				notefound.setDescription(notedto.getDescription());

				LocalDateTime now = LocalDateTime.now();
				notefound.setEditdate(now);
				noterepo.save(notefound);

				user.getNotelist().removeIf(value -> value.getId().equals(notefound.getId()));
				user.getNotelist().add(notefound);
				userrepo.save(user);

				return new Response(200, noteenv.getProperty("NOTE_UPDATED"), HttpStatus.OK);

			}
		}
		return new Response(400, (noteenv.getProperty("UNAUTHORIZED_USER")), HttpStatus.BAD_REQUEST);
	}

	/**
	 * Search particular record using id
	 */
	@Override
	public Response findNote(String id, String token) {
		String emailId = jwt.getUserToken(token);

		if (emailId != null) {
			
			
		Note note1 = (Note ) noterepo.findByIdAndEmailId(id, emailId);
		
			return new Response(200, noteenv.getProperty("NOTE_FOUND"), note1);
		}
		return new Response(400, noteenv.getProperty("UNAUTHORIZED_USER"), HttpStatus.BAD_REQUEST);
	}

	/**
	 * Deleting notes for user
	 */
	@Override
	public Response deleteNote(String id, String token) {
		String emailId = jwt.getUserToken(token);
		User user = userrepo.findByEmailId(emailId);
		if (emailId != null) {

			Note note1 = (Note) noterepo.findByIdAndEmailId(id, emailId);
			noterepo.delete(note1);

			user.getNotelist().removeIf(value -> value.getId().equals(note1.getId()));

			userrepo.save(user);

			return new Response(200, (noteenv.getProperty("NOTE_DELETE")), HttpStatus.OK);
		}
		return new Response(404, noteenv.getProperty("UNAUTHORIZED_USER"), HttpStatus.BAD_REQUEST);
	}

	/**
	 * Check note is pin or unpinned for user using token
	 */
	@Override
	public Response pin(String id, String token) {

		String emailId = jwt.getUserToken(token);
		User user = userrepo.findByEmailId(emailId);
		if (emailId != null) {

			Note note = (Note) noterepo.findByIdAndEmailId(id, emailId);

			if (note.getId()!=null) {
				note.setPin(!(note.isPin()));
				noterepo.save(note);

				user.getNotelist().removeIf(value -> value.getId().equals(note.getId()));

				user.getNotelist().add(note);
				userrepo.save(user);
				return new Response(200, noteenv.getProperty("PIN_VALUE"), note.isPin());
			}
			throw new NullException(noteenv.getProperty("NULL_VALUE"));

		}
		throw new NoteException(noteenv.getProperty("UNAUTHORIZED_USER"));
	}

	/**
	 * check archive or unarchive for user
	 */
	@Override
	public Response archive(String id, String token) {

		String emailId = jwt.getUserToken(token);
		User user = userrepo.findByEmailId(emailId);

		if (emailId!=null) {
			
			Note note = (Note) noterepo.findByIdAndEmailId(id, emailId);
			
			if (note != null) {
				note.setArchive(!(note.isArchive()));
				noterepo.save(note);

				user.getNotelist().removeIf(value -> value.getId().equals(note.getId()));

				user.getNotelist().add(note);
				userrepo.save(user);
				return new Response(200, noteenv.getProperty("ARCHIVE_VALUE"), note.isArchive());
			}
			throw new NullException(noteenv.getProperty("NULL_VALUE"));
		}
		throw new NoteException(noteenv.getProperty("UNAUTHORIZED_USER"));
	}

	/**
	 * check for trash or restore for user
	 */
	@Override
	public Response trash(String id, String token) {
		String emailId = jwt.getUserToken(token);
		 User user = userrepo.findByEmailId(emailId);
		 
		if (emailId != null) {
			 Note note = (Note) noterepo.findByIdAndEmailId(id, emailId);
			if (note != null) {
				note.setTrash(!(note.isTrash()));
				noterepo.save(note);
				user.getNotelist().removeIf(value -> value.getId().equals(note.getId()));
				
				user.getNotelist().add(note);
				userrepo.save(user);
				return new Response(200, noteenv.getProperty("TRASH_VALUE"),note.isTrash());
			}

			throw new NullException(noteenv.getProperty("NULL_VALUE"));

		}

		throw new NoteException(noteenv.getProperty("UNAUTHORIZED_USER"));
	}

	/**
	 * Show all notes present in the database
	 */
	@Override
	public List<Note> showNotes() {

		return noterepo.findAll();

	}

	/**
	 * Sorting by title
	 */
	@Override
	public List<?> sortNotesByTitle() {

		List<Note> list = showNotes();

		list = list.stream().sorted((list1, list2) -> list1.getTitle().compareToIgnoreCase(list2.getTitle())).parallel()
				.collect(Collectors.toList());

		return list;

	}

	/**
	 * Sort notes By Date
	 */
	@Override
	public List<?> sortNotesByDate() {

		List<Note> list = showNotes();

		list = list.stream().sorted((list1, list2) -> list1.getCreatedate().compareTo(list2.getCreatedate())).parallel()
				.collect(Collectors.toList());

		return list;

	}

	/**
	 * Added Collaborator Method
	 */
	@Override
	public Response addCollaborator(String noteId, Collaboratorsdto collaboratorsdto, String token) {

		String emailId = jwt.getUserToken(token);
		//User user = userrepo.findByEmailId(emailId);

		if (emailId!=null) {

			Note note1 = (Note) noterepo.findByIdAndEmailId(noteId, emailId);
			boolean status = note1.getCollaboratorsList().contains(collaboratorsdto.getCollaboratorsEmail());
		
			if (!status) {
				
			
			
				
					note1.getCollaboratorsList().add(collaboratorsdto.getCollaboratorsEmail());
					noterepo.save(note1);
//					user.getNotelist().add(note1);
//
//					user.getNotelist().removeIf(value -> value.getId().equals(note1.getId()));
//
//					user.getNotelist().add(note1);
//					userrepo.save(user);

					return new Response(200, noteenv.getProperty("ADDED_SUCCESSFULLY"), HttpStatus.OK);

				
			}
			
			return new Response(400, noteenv.getProperty("COLLABORATOR_EXIST"), HttpStatus.BAD_REQUEST);
		}
		throw new NoteException(noteenv.getProperty("UNAUTHORIZED_USER"));
	}

	/**
	 * removed Collaborator Method
	 */
	@Override
	public Response removeCollaborator(String noteId, Collaboratorsdto collaboratorsdto, String token) {

		String emailId = jwt.getUserToken(token);
		//User user = userrepo.findByEmailId(emailId);

		if (emailId!=null)
		{
			Note note1 = (Note) noterepo.findByIdAndEmailId(noteId, emailId);
				if (note1 != null) {
					note1.getCollaboratorsList().remove(collaboratorsdto.getCollaboratorsEmail());
					noterepo.save(note1);
	
	//				user.getNotelist().removeIf(value -> value.getId().equals(note1.getId()));
	//
	//				user.getNotelist().add(note1);
	//				userrepo.save(user);
	
					return new Response(200, noteenv.getProperty("REMOVED_SUCCESSFULLY"), HttpStatus.OK);
				}
	
					else
					{
						return new Response(400, noteenv.getProperty("NULL_VALUE"), HttpStatus.BAD_REQUEST);
					}
				
		}
		return new Response(400, noteenv.getProperty("UNAUTHORIZED_USER"), HttpStatus.BAD_REQUEST);

	}

	/**
	 * Adding remainder for the user
	 */
	@Override
	public Response addRemainder(String noteId, String token, int year, int month, int day, int hour, int minute,
			int second) {

		String emailId = jwt.getUserToken(token);
		 User user = userrepo.findByEmailId(emailId);

		if (emailId != null) {
			List<Note> listnote = noterepo.findByEmailId(emailId);
			Note note1 = listnote.stream().filter(i -> i.getId().equals(noteId)).findAny().orElse(null);
			LocalDateTime now = LocalDateTime.now();
			DateTimeFormatter formatter = DateTimeFormatter
					.ofPattern(day + "-" + month + "-" + year + "  " + hour + ":" + minute + ":" + second);

			String date = now.format(formatter);
			note1.setReminder(date);
			noterepo.save(note1);


			user.getNotelist().removeIf(value -> value.getId().equals(note1.getId()));
			
			user.getNotelist().add(note1);
			userrepo.save(user);

			return new Response(200, (noteenv.getProperty("ADDED_REMAINDER")), HttpStatus.OK);

		}
		return new Response	(400, noteenv.getProperty("UNAUTHORIZED_USER"), HttpStatus.BAD_REQUEST);
	}

	/**
	 * deleting remainder for the user
	 */
	@Override
	public Response deleteRemainder(String noteId, String token) {
		String emailId = jwt.getUserToken(token);
		User user = userrepo.findByEmailId(emailId);

		if (emailId.equals(user.getEmailId())) {
			List<Note> listnote = noterepo.findByEmailId(emailId);
			Note note1 = listnote.stream().filter(i -> i.getId().equals(noteId)).findAny().orElse(null);

			if(note1.getReminder()==null)
			{
				return new Response(200, noteenv.getProperty("NO_REMAINDER_TO_DELETE"), HttpStatus.OK);

			}
			
			note1.setReminder(null);
			noterepo.save(note1);

			return new Response(200, noteenv.getProperty("DELETED_REMAINDER"), HttpStatus.OK);

		}

		return new Response(400, noteenv.getProperty("NULL_VALUE"), HttpStatus.BAD_REQUEST);

	}

	@Override
	public Response editRemainder(String noteId, String token, int year, int month, int day, int hour, int minute,
			int second) {
		String emailId = jwt.getUserToken(token);
		User user = userrepo.findByEmailId(emailId);

		if (emailId != null) {
			List<Note> listnote = noterepo.findByEmailId(emailId);
			Note note1 = listnote.stream().filter(i -> i.getId().equals(noteId)).findAny().orElse(null);
			LocalDateTime now = LocalDateTime.now();
			DateTimeFormatter formatter = DateTimeFormatter
					.ofPattern(day + "-" + month + "-" + year + "  " + hour + ":" + minute + ":" + second);

			String date = now.format(formatter);

			if (note1.getReminder().equals(date)) {
				throw new NoteException(noteenv.getProperty("REMAINDER_ALREADY_EXIST"));
			}

			else {
				note1.setReminder(date);
				noterepo.save(note1);

				user.getNotelist().removeIf(value -> value.getId().equals(note1.getId()));

				user.getNotelist().add(note1);
				userrepo.save(user);

				return new Response(200, noteenv.getProperty("ADDED_REMAINDER"), HttpStatus.OK);

			}

		}
		return new Response(400, noteenv.getProperty("UNAUTHORIZED_USER"), HttpStatus.BAD_REQUEST);

	}

	@Override
	public List<?> sortNotesByTitleInDesc() {
		List<Note> list = showNotes();

		list = list.stream().sorted((list1, list2) -> list2.getTitle().compareToIgnoreCase(list1.getTitle())).parallel()
				.collect(Collectors.toList());

		return list;

	}

	@Override
	public List<?> sortNotesByDateInDesc() {
		List<Note> list = showNotes();

		list = list.stream().sorted((list1, list2) -> list2.getCreatedate().compareTo(list1.getCreatedate())).parallel()
				.collect(Collectors.toList());

		return list;

	}

}
