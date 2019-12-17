package com.bridgelabz.fundoo.usertestcase;


import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.anyString;
import static org.mockito.Mockito.when;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Properties;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.modelmapper.ModelMapper;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.core.env.Environment;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.web.multipart.MultipartFile;

import com.bridgelabz.fundoo.dto.Logindto;
import com.bridgelabz.fundoo.dto.Registerdto;
import com.bridgelabz.fundoo.dto.Resetdto;
import com.bridgelabz.fundoo.jwt.utility.Jms;
import com.bridgelabz.fundoo.jwt.utility.Jwt;
import com.bridgelabz.fundoo.model.User;
import com.bridgelabz.fundoo.repository.UserRepo;
import com.bridgelabz.fundoo.response.Response;
import com.bridgelabz.fundoo.service.ServiceImp;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest
@TestPropertySource("classpath:MessageReference.properties")
public class ServiceImplTest {
	
	@InjectMocks
	ServiceImp serviceimp;
	
	@Mock
	Jwt jwt;

	@Mock
	JavaMailSender javamailsender;

	@Mock
	Jms jms;
	
	@Mock
	UserRepo userrepo; 

	@Mock
	BCryptPasswordEncoder bCryptPasswordEncoder;
	
	@Mock
	Environment userenv;

	
	@Mock
	ModelMapper mapper;
	
	@Mock
	Registerdto registerdto = new Registerdto();
	
	@Mock
	Logindto logindto; 
	
	@Mock
	Resetdto resetdto;
	
	@Mock
	MultipartFile file;
	
	
	String emailId = "rohit.thorawade51@gmail.com";
	String token = "eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJlbWFpbElkIjoicm9oaXQudGhvcmF3YWRlNTFAZ21haWwuY29tIn0.NDPPVFJb7JbTaVaqDW6xawBPlMyiDtJ9URGVJXZMf5M";
	String passWord = "Hero@2230";
	String confirmpassWord="Hero@2230";
	String profilePic = "xyz.jpg";
	long mobile = 7896547897l;
	boolean status =false;
	
	
	User user = new User();
	Optional<User> optionaluser = Optional.of(user);
	
	/**
	 * TestCase for Add New User Method 
	 */
	@Test
	public void addNewUserTest()
	{
		
		registerdto.setPassWord(passWord);
		registerdto.setConfirmpassWord(confirmpassWord);
		
		when(mapper.map(registerdto, User.class)).thenReturn(user);
		when(registerdto.getPassWord()).thenReturn(passWord);
		when(registerdto.getConfirmpassWord()).thenReturn(confirmpassWord);
	//	when(registerdto.getPassWord().equals(registerdto.getConfirmpassWord())).thenReturn(true);
		when(bCryptPasswordEncoder.encode("password")).thenReturn(anyString());
	//	user.setPassWord(bCryptPasswordEncoder.encode(registerdto.getPassWord()));
		user.setPassWord(passWord);
		
	//	System.out.println("nfklbndgfl::"+System.getenv(userenv.getProperty("USER_REGISTER_SUCCESSFULLY")));
		
		when(jwt.getUserToken(token)).thenReturn(emailId);
		jms.sendMail(emailId, token);
		when(userrepo.save(user)).thenReturn(user);
		Response response = serviceimp.addNewUser(registerdto);
		System.out.println(response.getStatus());
		//System.out.println("hytutyg:::"+property.getProperty("USER_REGISTER_SUCCESSFULLY"));
		assertEquals(200, response.getStatus());
		
		
		
	}
	
	/**
	 * TestCase for DeleteUser Method
	 */
	@Test
	public void testdeleteUser()
	{
		when(jwt.getUserToken(token)).thenReturn(emailId);
		when(userrepo.findById(emailId)).thenReturn(optionaluser);
		
		Response response = serviceimp.deleteUser(token);
		System.out.println(response.getStatus());
		assertEquals(200, response.getStatus());
	}
	
	
	/**
	 * TestCase for FindByUser Method
	 */
	@Test
	public void testfindByUser()
	{
		when(jwt.getUserToken(token)).thenReturn(emailId);
		when(userrepo.findById(emailId)).thenReturn(optionaluser);
		Response response = serviceimp.findByUser(token);
		System.out.println(response.getStatus());
		assertEquals(200, response.getStatus());
	}
	
	
	/**
	 * TestCase for UpdateUser Method
	 */
	@Test
	public void testupdateuser()
	{
		registerdto.setMobileNumber(mobile);
		when(jwt.getUserToken(token)).thenReturn(emailId);
		when(userrepo.findByEmailId(emailId)).thenReturn(user);
		when(userrepo.save(user)).thenReturn(user);
        
		
		Response response = serviceimp.updateuser(registerdto, token);
		System.out.println(response.getStatus());
		assertEquals(200, response.getStatus());
		
		
	}
	
	/**
	 * TestCase for Login Method 
	 */
	@Test
	public void testlogin()
	{
		logindto.setEmailId(emailId);
		logindto.setPassWord(passWord);
		when(jwt.getUserToken(token)).thenReturn(emailId);
		when(mapper.map(logindto, User.class)).thenReturn(user);
		when(userrepo.findByEmailId(emailId)).thenReturn(user);
		when(bCryptPasswordEncoder.matches(logindto.getPassWord(), user.getPassWord())).thenReturn(status);
		if(status)
		{
			Response response = serviceimp.login( token,logindto);
			System.out.println(response.getStatus());
			assertEquals(200, response.getStatus());
			
		}
		
	}
	
	/**
	 * TestCase for ForgetPassword Method
	 */
	@Test
	public void testforget()
	{
		when(jwt.getUserToken(token)).thenReturn(emailId);
		//doNothing().when(jms.sendMail(emailId, token));
		jms.sendMail(emailId, token);
		Response response = serviceimp.forgetPassword( emailId);
		System.out.println(response.getStatus());
		assertEquals(200, response.getStatus());
		
	}
	
	/**
	 * TestCase for ResetPassword Method
	 */
	@Test
	public void testreset()
	{
		resetdto.setNewpassWord(passWord);
		resetdto.setConfirmpassWord(confirmpassWord);
		
		when(jwt.getUserToken(token)).thenReturn(emailId);
		when(userrepo.findByEmailId(emailId)).thenReturn(user);
		user.setPassWord(passWord);
		
		when(resetdto.getNewpassWord()).thenReturn(passWord);
		when(resetdto.getConfirmpassWord()).thenReturn(confirmpassWord);
		
			when(bCryptPasswordEncoder.encode(user.getPassWord())).thenReturn(anyString());
			user.setPassWord(passWord);
			user.setConfirmpassWord(confirmpassWord);
			when(userrepo.save(user)).thenReturn(user);
			
			Response response = serviceimp.resetpassWord(resetdto, token);
			System.out.println(response.getStatus());
			assertEquals(200, response.getStatus());
		
	
	}
	
	
	/**
	 * TestCase for Show Users 
	 */
	@Test
	public void testShow()
	{
		List<User> userlist = new ArrayList<>();
 		when(userrepo.findAll()).thenReturn(userlist);
 		
 		Response response = serviceimp.Show();
		System.out.println(response.getStatus());
		assertEquals(200, response.getStatus());
	
 	}
	
	/**
	 * @throws IOException
	 * TestCase for Upload Picture Method
	 */
	@Test
	public void testuploadpic() throws IOException
	{ 
		
		when(jwt.getUserToken(token)).thenReturn(emailId);
		when(userrepo.findByEmailId(emailId)).thenReturn(user);

			if(profilePic == null) 
			{
			user.setProfilePic(profilePic);
			Response response = serviceimp.uploadProfilePic(token, file);
			assertEquals(200, response.getStatus());
			}
			
			
	}
	/**
	 * TestCase for Verify Method
	 */
	@Test
	public void testverify()

	{
		user.setEmailId(emailId);
		when(jwt.getUserToken(token)).thenReturn(emailId);
		when(userrepo.findByEmailId(emailId)).thenReturn(user);
		
		Response response = serviceimp.verify(token);
		assertEquals(200, response.getStatus());
		
	}
		
	/**
	 * TestCase for Remove Profile Picture
	 */
	@Test
	public void testremoveProfilePic()
	{
		user.setEmailId(emailId);
		when(jwt.getUserToken(token)).thenReturn(emailId);
		when(userrepo.findByEmailId(emailId)).thenReturn(user);
		Response response = serviceimp.removeProfilePic(token);

		assertEquals(200, response.getStatus());
		
	}
	
	
	
		
		
}
	
	
	
	
	
	


