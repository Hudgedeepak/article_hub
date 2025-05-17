package com.example.article_hub.serviceImpl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.Objects;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.example.article_hub.dao.UserInfoRepo;
import com.example.article_hub.entity.AuthRequest;
import com.example.article_hub.entity.UserInfo;
import com.example.article_hub.filter.JwtAuthFilter;
import com.example.article_hub.jwtService.JwtService;
import com.example.article_hub.jwtService.UserInfoDetails;
import com.example.article_hub.service.UserInfoService;

@Service
public class UserInfoServiceImpl implements UserInfoService {

	Logger log = LoggerFactory.getLogger(UserInfoServiceImpl.class);

	@Autowired
	UserInfoRepo userInfoRepository;

	@Autowired
	private PasswordEncoder encoder;

	@Autowired
	private JwtService jwtService;

	@Autowired
	AuthenticationManager authenticationManager;

	@Autowired
	JwtAuthFilter jwtAuthFilter;

	@Override
	public ResponseEntity<?> addNewAppuser(UserInfo userInfo) {

		try {
			if (!validateUserInfo(userInfo))
				return new ResponseEntity<>("{\"message\":\"Missing Required Data.\"}", HttpStatus.BAD_REQUEST);

			Optional<UserInfo> db = userInfoRepository.findByEmail(userInfo.getEmail());

			if (db.isPresent())
				return new ResponseEntity<>("{\"message\":\"Email Already Exit.\"}", HttpStatus.BAD_REQUEST);
			userInfo.setPassword(encoder.encode(userInfo.getPassword()));
			userInfo.setIsDeletable("true");
			userInfo.setStatus("true");
			userInfo.setEmail(userInfo.getEmail().toLowerCase());
			userInfoRepository.save(userInfo);
			return new ResponseEntity<>("{\"message\":\"User Added successfully.\"}", HttpStatus.OK);
		} catch (Exception ex) {
			log.error("Exception in addNewUser : {}", ex);
		}
		return new ResponseEntity<>("{\"message\":\"Something went wrong\"}", HttpStatus.INTERNAL_SERVER_ERROR);
	}

	private boolean validateUserInfo(UserInfo userInfo) {
		return !Objects.isNull(userInfo) && StringUtils.hasText(userInfo.getName())
				&& StringUtils.hasText(userInfo.getEmail()) && StringUtils.hasText(userInfo.getPassword());

	}

	@Override
	public ResponseEntity<?> login(AuthRequest authRequest) {
		try {
			Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(
					authRequest.getEmail().toLowerCase(), authRequest.getPassword()));

			if (authentication != null && authentication.isAuthenticated()) {
				UserInfoDetails userInfoDetails = (UserInfoDetails) authentication.getPrincipal();
				if ("true".equalsIgnoreCase(userInfoDetails.getStatus())) {
					return new ResponseEntity<>(
							"{\"token\":\"" + jwtService.generateToken(authRequest.getEmail().toLowerCase()) + "\"}",
							HttpStatus.OK);
				} else {
					return new ResponseEntity<>("{\"message\":\"wait for admin approval.\"}", HttpStatus.BAD_REQUEST);
				}
			} else {
				throw new UsernameNotFoundException("invalid user request !");
			}

		} catch (BadCredentialsException ex) {
			return new ResponseEntity<>("{\"message\":\"Invalid credentials.\"}", HttpStatus.UNAUTHORIZED);
		} catch (UsernameNotFoundException ex) {
			throw ex;
		} catch (Exception ex) {
			log.error("Exception in login : {}", ex);
		}
		return new ResponseEntity<>("{\"message\":\"Something went wrong\"}", HttpStatus.INTERNAL_SERVER_ERROR);

	}

	@Override
	public ResponseEntity<?> getAllAppuser() {
		try {
			return new ResponseEntity<>(userInfoRepository.getAllAppuser(jwtAuthFilter.getEmail()), HttpStatus.OK);
		} catch (Exception ex) {
			log.error("Exception in getAllAppuser : {}", ex.getMessage());
		}
		return new ResponseEntity<>("{\"message\":\"Something went wrong\"}", HttpStatus.INTERNAL_SERVER_ERROR);
	}

	@Override
	public ResponseEntity<?> updateUserStatus(UserInfo userInfo) {
		try {

			if (!Objects.isNull(userInfo) && !Objects.isNull(userInfo.getId())
					&& !Objects.isNull(userInfo.getStatus())) 
		   {				
				Integer updateCount = userInfoRepository.updateUserStatus(userInfo.getStatus(), userInfo.getId());

				if (updateCount == 0) {
					return new ResponseEntity<>("{\"message\":\"User id does not exit\"}", HttpStatus.BAD_REQUEST);
				} else {
					return new ResponseEntity<>("{\"message\":\"\"}", HttpStatus.OK);
				}
			} 
			
		 else {
				return new ResponseEntity<>("{\"message\":\"Invalid data\"}", HttpStatus.BAD_REQUEST);
			}

		} 
		
		catch (Exception ex) {
			log.error("Exception in updateUserStatus : {}", ex.getMessage());
		}
		return new ResponseEntity<>("{\"message\":\"Something went wrong\"}", HttpStatus.INTERNAL_SERVER_ERROR);

	}

	@Override
	public ResponseEntity<?> checkToken() {
		return new ResponseEntity<>( "{\"message\":\"true\"}", HttpStatus.OK);
	}

	@Override
	public ResponseEntity<?> updateUser(UserInfo userInfo) {
		try {
			
			Optional<UserInfo> optionalUserInfo= userInfoRepository.findById(userInfo.getId());
			if(!optionalUserInfo.isPresent()) {
				return new ResponseEntity<>("{\"message\":\"user not found\"}", HttpStatus.BAD_REQUEST);
			}
			
			UserInfo info = optionalUserInfo.get();
			info.setEmail(userInfo.getEmail());
			info.setName(userInfo.getName());
			userInfoRepository.save(info);
			
			return new ResponseEntity<>("{\"message\":\"user update successfully\"}", HttpStatus.OK);
			
			
		} catch (Exception ex) {
			log.error("Exception in updateUser : {}", ex.getMessage());
		}
		return new ResponseEntity<>("{\"message\":\"Something went wrong\"}", HttpStatus.INTERNAL_SERVER_ERROR);
	
	}
	
	
	@Override
	public ResponseEntity<?> deleteUser(Integer id) {
	    try {
	        String requesterEmail = jwtAuthFilter.getEmail();
	        Optional<UserInfo> requesterOpt = userInfoRepository.findByEmail(requesterEmail);

	        if (requesterOpt.isEmpty() || !"admin@gmail.com".equalsIgnoreCase(requesterOpt.get().getEmail())) {
	            return new ResponseEntity<>("{\"message\":\"Only admin can delete users.\"}", HttpStatus.FORBIDDEN);
	        }

	        Optional<UserInfo> userToDelete = userInfoRepository.findById(id);
	        if (userToDelete.isEmpty()) {
	            return new ResponseEntity<>("{\"message\":\"User not found.\"}", HttpStatus.NOT_FOUND);
	        }

	        UserInfo user = userToDelete.get();
	        if ("false".equals(user.getIsDeletable())) {
	            return new ResponseEntity<>("{\"message\":\"This user cannot be deleted.\"}", HttpStatus.BAD_REQUEST);
	        }

	        userInfoRepository.delete(user);
	        return new ResponseEntity<>("{\"message\":\"User deleted successfully.\"}", HttpStatus.OK);

	    } catch (Exception ex) {
	        log.error("Exception in deleteUser : {}", ex.getMessage());
	    }
	    return new ResponseEntity<>("{\"message\":\"Something went wrong\"}", HttpStatus.INTERNAL_SERVER_ERROR);
	}


}
