package com.dxc.service;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.Collection;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

import javax.transaction.Transactional;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.dxc.config.JwtTokenUtil;
import com.dxc.entity.Roles;
import com.dxc.entity.Users;
import com.dxc.exception.ResourceNotFoundException;
import com.dxc.payload.UserDto;
import com.dxc.repository.RoleRepository;
import com.dxc.repository.UserRepository;

@Service
@Transactional
public class UserServiceImpl implements UserService{
	
	@Autowired
	private UserRepository userRepository;
	
	@Autowired
	private RoleRepository roleRepository;
	
	@Autowired
	private BCryptPasswordEncoder passwordEncoder;
	
	@Autowired
    private JwtTokenUtil jwtTokenUtil;
	
	private static final long EXPIRE_TOKEN_AFTER_MINUTES = 60;
	
	//convert entity to DTO
	private UserDto mapToDto(Users users) {
		UserDto userDto = new UserDto();
		userDto.setUid(users.getUid());
		userDto.setUsername(users.getUsername());
		userDto.setEmail(users.getEmail());
		userDto.setRole(users.getRoles());
		userDto.setPosts(users.getPosts());
		return userDto;	
	}
	
	//convert DTO to entity
	public Users mapToEntity(UserDto userDto) {
		Users users = new Users();
		users.setEmail(userDto.getEmail());
		users.setUsername(userDto.getUsername());
		users.setPassword(userDto.getPassword());
		users.setRoles(userDto.getRole());
		users.setPosts(userDto.getPosts());
		return users;
	}
	
	@Override
	public List<Users> getAllUsers() {
		return userRepository.findAll();
	}
	
	@Override
    public boolean checkIfUserExist(String email) {
        return userRepository.findByEmail(email) !=null ? true : false;
    }
	
	 @Override
	 public Users getUserById(long uid) {
		 Optional<Users> users = userRepository.findById(uid);
		 return users.isPresent() ? users.get() : null;
	 }
	 
	 @Override
	 public Users getUserByEmail(String email) {
		 return userRepository.findByEmail(email);
	 }
	 
	@Override
	public UserDto changePassword(UserDto userDto, String email) {
		Users users = getUserByEmail(email);
		if(users != null) {
			users.setPassword(passwordEncoder.encode(userDto.getPassword()));
			userRepository.save(users);
		}
		return mapToDto(users);
	}
	

	@Override
	public void deletUserById(long uid) {
		Users users = userRepository.findById(uid).orElseThrow(
				() ->new ResourceNotFoundException("Users", "uid", uid)
				);
		users.getRoles().removeAll(users.getRoles());
		users.getPosts().removeAll(users.getPosts());
		userRepository.delete(users);
	}
	
	@Override
	public UserDto updateUser(UserDto userDto) {
		Users user = getUserById(userDto.getUid());
        if(user != null) {
            BeanUtils.copyProperties(userDto, user, "password");
            userRepository.save(user);
        }
        return userDto;
	}
	
	@Override
	public String updateResetPasswordToken(String email) {
		Users users = userRepository.findByEmail(email);
		if (users != null) {
			//users.setResetPasswordToken(token);
			users.setResetPasswordToken(generateToken());
			users.setTokenCreationDate(LocalDateTime.now());
			userRepository.save(users);
		} else {
			return ("Could not find any user with the email " + email);
		}
		return users.getResetPasswordToken();
	}
	
	private String generateToken() {
		StringBuilder resetpwdtoken = new StringBuilder();

		return resetpwdtoken.append(UUID.randomUUID().toString())
				.append(UUID.randomUUID().toString()).toString();
	}
	
	private boolean isTokenExpired(final LocalDateTime tokenCreationDate) {

		LocalDateTime now = LocalDateTime.now();
		Duration diff = Duration.between(tokenCreationDate, now);

		return diff.toMinutes() >= EXPIRE_TOKEN_AFTER_MINUTES;
	}

	
	@Override
	public Users getByResetPasswordToken(String token) {
		return userRepository.findByResetPasswordToken(token);
	}
	
	@Override
	public void updatePassword(Users users, String newPassword) {
		String encodedPassword = passwordEncoder.encode(newPassword);
		users.setPassword(encodedPassword);

		users.setResetPasswordToken(null);
		userRepository.save(users);
	}
	
	public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
		Users users = userRepository.findByEmail(email);
		if(users == null) {
			throw new UsernameNotFoundException("Invalid email or password.");
		}
		return new org.springframework.security.core.userdetails.User(users.getEmail(), users.getPassword(), mapRolesToAuthorities(users.getRoles()));	
	}
	
	private Collection<? extends GrantedAuthority> mapRolesToAuthorities(Collection<Roles> roles){
		return roles.stream().map(role -> new SimpleGrantedAuthority(role.getName().name())).collect(Collectors.toList());
	}

}
