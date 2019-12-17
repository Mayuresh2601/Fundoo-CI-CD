/**
 * @author Rohit Thorawade
 * @Purpose Implement testcases for Label Implementation class
 *
 */
package com.bridgelabz.fundoo.labeltestcases;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.modelmapper.ModelMapper;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.core.env.Environment;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.bridgelabz.fundoo.jwt.utility.Jwt;
import com.bridgelabz.fundoo.label.dto.Labeldto;
import com.bridgelabz.fundoo.label.model.Label;
import com.bridgelabz.fundoo.label.repository.LabelRepo;
import com.bridgelabz.fundoo.label.service.LabelImpl;
import com.bridgelabz.fundoo.model.User;
import com.bridgelabz.fundoo.notes.model.Note;
import com.bridgelabz.fundoo.notes.repository.NoteRepo;
import com.bridgelabz.fundoo.notes.service.NoteImpl;
import com.bridgelabz.fundoo.repository.UserRepo;
import com.bridgelabz.fundoo.response.Response;




@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest
@TestPropertySource("classpath:MessageReference.properties")
public class LabelImplTest {

	@InjectMocks
	LabelImpl labelimpl;
	
	@Mock
	LabelRepo labelrepo;

	@Mock
	Jwt jwt;

	@Mock
	NoteRepo noterepo;

	@Mock
	NoteImpl noteimpl;

	@Mock
	UserRepo userrepo;
	
	@Mock
	ModelMapper mapper;

	@Mock
	Labeldto labeldto;
	
	@Mock
	Environment labelenv;
	
	String token = "eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJlbWFpbElkIjoicm9oaXQudGhvcmF3YWRlNTFAZ21haWwuY29tIn0.NDPPVFJb7JbTaVaqDW6xawBPlMyiDtJ9URGVJXZMf5M";
	String emailId = "rohit.thorawade51@gmail.com";

	String labeltitle="MonkeyKing";
	String labelId= "5de8c8efc0f1657fc69ded84";
	String noteId = "5de8c85f75952e1beae317c0";
	
	User user = new User();
	Label label = new Label();
	Note note  = new Note();
	
	Optional<Label> optionlabel = Optional.of(label);
	Optional<Note> optionnote = Optional.of(note);
	
	List<Label> labellist = new ArrayList<>();
	List<Note> notelist = new ArrayList<>();
 	
	
	/**
	 * TestCase for CreateLabel Method
	 */
	@Test
	public void testcreateLabel()
	{
		when(jwt.getUserToken(token)).thenReturn(emailId);
		when(userrepo.findByEmailId(emailId)).thenReturn(user);
		when(mapper.map(labeldto, Label.class)).thenReturn(label);
		when(labelrepo.save(label)).thenReturn(label);
		
		Response response = labelimpl.createLabel(labeldto, token);
		
		assertEquals(200, response.getStatus());
			
	}
	
	/**
	 * TestCase for DeleteLabel Method
	 */
	@Test
	public void testdeleteLabel()
	{
		when(jwt.getUserToken(token)).thenReturn(emailId);
		when(userrepo.findByEmailId(emailId)).thenReturn(user);
		labelrepo.deleteById(labelId);
		when(noterepo.findByIdAndEmailId(noteId, labelId)).thenReturn(note);
		
		Response response = labelimpl.deleteLabel(noteId, labelId, token);
		
		assertEquals(200, response.getStatus());

	}
	
	
	/**
	 * TestCase for FindLabel Method
	 */
	@Test
	public void testfindlabel()
	{
		when(jwt.getUserToken(token)).thenReturn(emailId);
		when(userrepo.findByEmailId(emailId)).thenReturn(user);
		when(labelrepo.findById(labelId)).thenReturn(optionlabel);
		
		Response response = labelimpl.findlabel(labelId, token);
		
		assertEquals(200, response.getStatus());
		
	}
	
	/**
	 * TestCase for UpdateLabel method
	 */
	@Test 
	public void testupdateLabel()
	{
		Labeldto labeldto = new Labeldto();

		label.setLabelId(labelId);
		labeldto.setLabeltitle(labeltitle);
	
 		when(jwt.getUserToken(token)).thenReturn(emailId);
		when(userrepo.findByEmailId(emailId)).thenReturn(user);
		when(mapper.map(labeldto, Label.class)).thenReturn(label);
		when(labelrepo.findById(labelId)).thenReturn(optionlabel);
		when(labelrepo.save(label)).thenReturn(label);
		
		Response response = labelimpl.updateLabel(labelId, labeldto, token);
		assertEquals(200, response.getStatus());

	}
	
	
	
	
	
	
}
