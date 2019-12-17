/**
 * @author Rohit Thorawade
 * 
 * @Purpose Generate token and get user emailid from a particular token 
 *
 */
package com.bridgelabz.fundoo.jwt.utility;

import org.springframework.stereotype.Component;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.Claim;

@Component
public class Jwt {

	private static final String SECRET_KEY = "SECRET";

	/**
	 * @param emailId-EmailId of the given user
	 * @return Token
	 */
	public String createToken(String emailId) {
		Algorithm algorithm = Algorithm.HMAC256(SECRET_KEY);

		return JWT.create().withClaim("emailId", emailId).sign(algorithm);

	}

	/**
	 * @param token-generated token
	 * @return EmailId of the user
	 */
	public String getUserToken(String token) {

		Claim claim = JWT.require(Algorithm.HMAC256(SECRET_KEY)).build().verify(token).getClaim("emailId");
		return claim.asString();
	}

}
