package com.bridgelabz.fundoo.notetestcases;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.modelmapper.ModelMapper;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.core.env.Environment;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.bridgelabz.fundoo.jwt.utility.Jwt;
import com.bridgelabz.fundoo.model.User;
import com.bridgelabz.fundoo.notes.dto.Collaboratorsdto;
import com.bridgelabz.fundoo.notes.dto.Notedto;
import com.bridgelabz.fundoo.notes.model.Note;
import com.bridgelabz.fundoo.notes.repository.NoteRepo;
import com.bridgelabz.fundoo.notes.service.NoteImpl;
import com.bridgelabz.fundoo.repository.UserRepo;
import com.bridgelabz.fundoo.response.Response;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest
@TestPropertySource("classpath:MessageReference.properties")
public class NoteImplTest {

	@InjectMocks
	NoteImpl noteimpl;

	@Mock
	NoteRepo noterepo;

	@Mock
	ModelMapper mapper;

	@Mock
	Jwt jwt;

	@Mock
	UserRepo userrepo;

	@Mock
	Environment noteenv;

	@Mock
	User user;

	@Mock
	Collaboratorsdto collaboratorsdto;

	String emailId = "rohit.thorawade51@gmail.com";
	String token = "eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJlbWFpbElkIjoicm9oaXQudGhvcmF3YWRlNTFAZ21haWwuY29tIn0.NDPPVFJb7JbTaVaqDW6xawBPlMyiDtJ9URGVJXZMf5M";
	String noteId = "5de8c85f75952e1beae317c0";

	String title = "The Jungle Book";
	String description = "Adventure";
	boolean status = true;

	Note note = new Note();
	Optional<Note> note1 = Optional.of(note);

	List<String> collaboratorsList = new ArrayList<>();

	/**
	 * Test case for create note method
	 */
	@Test
	public void testcreateNote() {
		Notedto notedto = new Notedto();

		notedto.setTitle(title);
		notedto.setDescription(description);
		notedto.setEmailId(emailId);

		when(jwt.getUserToken(token)).thenReturn(emailId);
		when(userrepo.findByEmailId(emailId)).thenReturn(user);
		when(mapper.map(notedto, Note.class)).thenReturn(note);
		when(noterepo.save(note)).thenReturn(note);
		user.getNotelist().add(note);
		when(userrepo.save(user)).thenReturn(user);
		Response response = noteimpl.createNote(notedto, token);
		System.out.println("response code :::"+response.getStatus());
		assertEquals(200, response.getStatus());

	}

	/**
	 * Test case for update note method
	 */
	@Test
	public void testupdateNote() {

		Notedto notedto = new Notedto();
		notedto.setTitle("The Key");
		notedto.setDescription("The untold Story");

		when(jwt.getUserToken(token)).thenReturn(emailId);
		when(userrepo.findByEmailId(emailId)).thenReturn(user);
		when(mapper.map(notedto, Note.class)).thenReturn(note);
		when(noterepo.findByIdAndEmailId(noteId, emailId)).thenReturn(note);
		when(noterepo.save(note)).thenReturn(note);

		assertThat(user.getNotelist().removeIf(value -> value.getId().equals(note.getId())));
		user.getNotelist().add(note);

		when(userrepo.save(user)).thenReturn(user);

		Response response = noteimpl.updateNote(notedto, noteId, token);

		System.out.println("response status :" + response.getStatus());

		assertEquals(200, response.getStatus());

	}

	/**
	 * Test case for delete note method
	 */
	@Test
	public void testdeleteNote() {

		when(jwt.getUserToken(token)).thenReturn(emailId);
		when(userrepo.findByEmailId(emailId)).thenReturn(user);
		when(noterepo.findByIdAndEmailId(noteId, emailId)).thenReturn(note);

		assertThat(user.getNotelist().removeIf(value -> value.getId().equals(note.getId())));
		when(userrepo.save(user)).thenReturn(user);

		Response response = noteimpl.deleteNote(noteId, token);
		assertEquals(200, response.getStatus());
	}

	/**
	 * Test case to get all notes
	 */
	@Test
	public void testfindNote() {

		when(jwt.getUserToken(token)).thenReturn(emailId);

		when(noterepo.findByIdAndEmailId(noteId, emailId)).thenReturn(note);

		Response response = noteimpl.findNote(noteId, token);
		assertEquals(200, response.getStatus());

	}

	/**
	 * Test case for Pin/unPinned method
	 */
	@Test
	public void testPin() {
		note.setId(noteId);
		note.setPin(false);

		when(jwt.getUserToken(token)).thenReturn(emailId);
		when(userrepo.findByEmailId(emailId)).thenReturn(user);
		when(noterepo.findByIdAndEmailId(noteId, emailId)).thenReturn(note);
		when(noterepo.save(note)).thenReturn(note);
		assertThat(user.getNotelist().removeIf(value -> value.getId().equals(note.getId())));

		user.getNotelist().add(note);
		when(userrepo.save(user)).thenReturn(user);

		Response response = noteimpl.pin(noteId, token);
		assertEquals(200, response.getStatus());

	}

	/**
	 * Test case for trash/unTrash Method
	 */
	@Test
	public void testTrash() {
		note.setId(noteId);
		note.setTrash(false);

		when(jwt.getUserToken(token)).thenReturn(emailId);
		when(userrepo.findByEmailId(emailId)).thenReturn(user);
		when(noterepo.findByIdAndEmailId(noteId, emailId)).thenReturn(note);
		when(noterepo.save(note)).thenReturn(note);
		assertThat(user.getNotelist().removeIf(value -> value.getId().equals(note.getId())));

		user.getNotelist().add(note);
		when(userrepo.save(user)).thenReturn(user);

		Response response = noteimpl.trash(noteId, token);
		assertEquals(200, response.getStatus());

	}

	/**
	 * Test case for archive/unArchive method
	 */
	@Test
	public void testArchive() {
		note.setId(noteId);
		note.setArchive(false);

		when(jwt.getUserToken(token)).thenReturn(emailId);
		when(userrepo.findByEmailId(emailId)).thenReturn(user);
		when(noterepo.findByIdAndEmailId(noteId, emailId)).thenReturn(note);
		when(noterepo.save(note)).thenReturn(note);
		assertThat(user.getNotelist().removeIf(value -> value.getId().equals(note.getId())));

		user.getNotelist().add(note);
		when(userrepo.save(user)).thenReturn(user);

		Response response = noteimpl.archive(noteId, token);
		assertEquals(200, response.getStatus());

	}

	/**
	 * Method : TestCase for show all notes
	 */
	@Test
	public void testshowNotes() {
		List<Note> note1 = noteimpl.showNotes();

		assertEquals(noterepo.findAll(), note1);
	}

	/**
	 * Method: TestCase for sort Note By Title in Ascending Order
	 */
	@Test
	public void testsortNotesByTitle() {

		List<?> notelist = noteimpl.sortNotesByTitle();
		assertEquals(notelist, noterepo.findAll());
	}

	/**
	 * Method: TestCase sort Note By Title in Descending Order
	 */
	@Test
	public void testSortNoteByTitleDesc() {

		List<?> notelist = noteimpl.sortNotesByTitleInDesc();
		assertEquals(notelist, noterepo.findAll());
	}

	/**
	 * Method:TestCase sort Note By Title in Ascending Order
	 */
	@Test
	public void testsortNotesByDate() {

		List<?> notelist = noteimpl.sortNotesByDate();
		assertEquals(notelist, noterepo.findAll());
	}

	/**
	 * Method: TestCase sort Note By Title in descending Order
	 */
	@Test
	public void testsortNotesByDateInDesc() {

		List<?> notelist = noteimpl.sortNotesByDateInDesc();
		assertEquals(notelist, noterepo.findAll());
	}

	/**
	 * Method : TestCase Add Collaborator Method
	 */
	@Test
	public void testaddCollaborator() {
		collaboratorsdto.setCollaboratorsEmail(emailId);
		note.getCollaboratorsList().add(emailId);

		when(jwt.getUserToken(token)).thenReturn(emailId);
		when(noterepo.findByIdAndEmailId(noteId, emailId)).thenReturn(note);
		assertThat(note.getCollaboratorsList().contains(collaboratorsdto.getCollaboratorsEmail()));

		note.getCollaboratorsList().add(emailId);
		when(noterepo.save(note)).thenReturn(note);

		Response response = noteimpl.addCollaborator(noteId, collaboratorsdto, token);
		assertEquals(200, response.getStatus());

	}

	/**
	 * Method : TestCase Remove Collaborator
	 */
	@Test
	public void testremoveCollaborator() {
		collaboratorsdto.setCollaboratorsEmail(emailId);
		note.getCollaboratorsList().add(emailId);

		when(jwt.getUserToken(token)).thenReturn(emailId);
		when(noterepo.findByIdAndEmailId(noteId, emailId)).thenReturn(note);
		assertThat(note.getCollaboratorsList().contains(collaboratorsdto.getCollaboratorsEmail()));

		note.getCollaboratorsList().remove(emailId);
		when(noterepo.save(note)).thenReturn(note);

		Response response = noteimpl.removeCollaborator(noteId, collaboratorsdto, token);
		assertEquals(200, response.getStatus());

	}

}
