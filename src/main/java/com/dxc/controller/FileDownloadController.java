package com.dxc.controller;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import com.dxc.entity.DatabaseFile;
import com.dxc.service.DatabaseFileService;

@RestController
public class FileDownloadController {
	
	@Autowired
	private DatabaseFileService fileStorageService;
	
	private static final Logger logger = LoggerFactory.getLogger(FileDownloadController.class);
	
	@GetMapping("/downloadFile/{fileName:.+}")
	public ResponseEntity<Resource> downloadFile(@PathVariable String fileName,HttpServletRequest request){
		
		DatabaseFile databaseFile = fileStorageService.getFile(fileName);
		System.out.println(databaseFile);
		
		return ResponseEntity.ok()
				.contentType(MediaType.parseMediaType(databaseFile.getFileType()))
				.header(HttpHeaders.CONTENT_DISPOSITION,"attachment:filename=\""+ databaseFile.getFileName() +"\"")
                .body(new ByteArrayResource(databaseFile.getData()));
	}
}
