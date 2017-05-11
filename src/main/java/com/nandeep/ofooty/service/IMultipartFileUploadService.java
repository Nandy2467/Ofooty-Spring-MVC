package com.nandeep.ofooty.service;

import java.io.File;

import org.springframework.web.multipart.MultipartFile;


public interface IMultipartFileUploadService {
	
	public boolean store(MultipartFile file, int playerID);
	public File load(String filename);
}
