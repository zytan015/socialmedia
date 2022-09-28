package com.dxc.controller;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.dxc.entity.DatabaseFile;
import com.dxc.payload.Response;
import com.dxc.service.DatabaseFileService;

@RestController
@RequestMapping("/api")
public class FileUploadController {
	
	@Autowired
	private DatabaseFileService fileStorageService;
	
	@PostMapping("/uploadFile")
	public Response uploadFile(@RequestParam("file")MultipartFile file) throws IOException {
		
		DatabaseFile fileName=fileStorageService.storeFile(file);
		String fileDownloadUri=ServletUriComponentsBuilder.fromCurrentContextPath()
				               .path("/downloadFile/")
				               .path(fileName.getFileName())
				               .toUriString();
		return new Response(fileName.getId(), fileName.getFileName(), fileDownloadUri, file.getContentType());
	}
	
}
