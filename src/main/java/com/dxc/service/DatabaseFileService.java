package com.dxc.service;

import java.io.IOException;
import java.net.MalformedURLException;
import java.nio.file.FileSystemNotFoundException;
import java.nio.file.Path;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import com.dxc.entity.DatabaseFile;
import com.dxc.entity.Posts;
import com.dxc.exception.FileNotFoundException;
import com.dxc.exception.FileStorageException;
import com.dxc.exception.ResourceNotFoundException;
import com.dxc.repository.DatabaseFileRepository;
import com.dxc.repository.PostRepository;

@Service
public class DatabaseFileService {
	@Autowired
	private DatabaseFileRepository dbFileRespository;
	@Autowired
	private PostRepository postRepository;
	
	private final Path fileStorageLocation = null;
	
	public DatabaseFile storeFile(MultipartFile file) {
		String fileName=StringUtils.cleanPath(file.getOriginalFilename());
		try {
			if(fileName.contains("..")) {
				throw new FileStorageException(
						"Sorry! FileName contains invalid path Sequence"+fileName
						);
			}

			//DatabaseFile dbFile=new DatabaseFile(null, fileName, file.getContentType() ,file.getBytes(), null);
			DatabaseFile dbFile=new DatabaseFile();
			dbFile.setFileName(fileName);
			dbFile.setFileType(file.getContentType());
			dbFile.setData(file.getBytes());   
			
		    return dbFileRespository.save(dbFile);
		}catch(IOException e) {
			throw new FileStorageException("Could not Store File "+fileName+" please try again",e);
		}
	}
	
	public DatabaseFile getFile(String fileName) {
		System.out.println(dbFileRespository.findByFileName(fileName));
		return dbFileRespository.findByFileName(fileName)
				.orElseThrow(
						()->new FileNotFoundException(
								"File Not found with file name "+fileName
								)
						);
	}
	
	public Resource loadFileAsResource(String fileName) {
        try {
            Path filePath = this.fileStorageLocation.resolve(fileName).normalize();
            Resource resource = new UrlResource(filePath.toUri());
            if(resource.exists()) {
                return resource;
            } else {
                throw new FileSystemNotFoundException("File not found " + fileName);
            }
        } catch (MalformedURLException ex) {
            throw new FileSystemNotFoundException("File not found " + fileName);
        }
    }
	
}
