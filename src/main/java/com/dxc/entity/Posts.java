package com.dxc.entity;

import javax.persistence.CascadeType;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import com.dxc.audit.Auditable;
import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor

@Entity
@Table(name="posts")
public class Posts extends Auditable<String>{
	
	@Id
	@Column(name="pid", nullable = false)
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long pid;
	private int views;
	private String description; // caption
	private String link;
	
	@ManyToOne
	@OnDelete(action = OnDeleteAction.CASCADE)
    @JoinColumn(name="uid")
	@JsonIgnore
    private Users users;
	
	@OneToOne(cascade = CascadeType.MERGE)
	@JoinColumn(name = "fid", referencedColumnName = "id")
	private DatabaseFile dbFile;
	
}
