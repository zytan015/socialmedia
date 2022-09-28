package com.dxc.entity;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor

@Entity
@Table(name = "users", uniqueConstraints = @UniqueConstraint(columnNames = "email"))
public class Users {
	@Id
	@Column(name = "uid", nullable = false)
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long uid;
	private String username;
	private String email;
	private String password;
	@Column(name = "reset_password_token")
	private String resetPasswordToken;
	@Column(columnDefinition = "TIMESTAMP", name = "Token_CreationDate")
	private LocalDateTime tokenCreationDate;

	@ManyToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
	@JoinTable(name = "user_role", joinColumns = {
			@JoinColumn(name = "uid", referencedColumnName = "uid") }, inverseJoinColumns = {
					@JoinColumn(name = "rid", referencedColumnName = "id") })
	private Set<Roles> roles = new HashSet<>();

	public Users(String email, String password, String username) {
		this.email = email;
		this.password = password;
		this.username = username;
	}

	@OneToMany(cascade = CascadeType.ALL)
	@JoinColumn(name = "uid")
	@JsonIgnore
	private List<Posts> posts; 



}
