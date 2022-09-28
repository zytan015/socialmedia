package com.dxc.payload;

import javax.persistence.Lob;

public class Response {
	private String id;
	private String fileName;
	private String fileDownload;
	private String fileType;
//	private byte[] fileByte;

	public Response(String id, String fileName, String fileDownload, String fileType) {
		super();
		this.id = id;
		this.fileName = fileName;
		this.fileDownload = fileDownload;
		this.fileType = fileType;
//		this.fileByte = fileByte;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getFileName() {
		return fileName;
	}

	public void setFileName(String fileName) {
		this.fileName = fileName;
	}

	public String getFileDownload() {
		return fileDownload;
	}

	public void setFileDownload(String fileDownload) {
		this.fileDownload = fileDownload;
	}

	public String getFileType() {
		return fileType;
	}

	public void setFileType(String fileType) {
		this.fileType = fileType;
	}

}
