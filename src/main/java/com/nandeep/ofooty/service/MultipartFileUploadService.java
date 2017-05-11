package com.nandeep.ofooty.service;

import java.io.File;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.nandeep.ofooty.util.ProfilePicPath;
import com.nandeep.ofooty.util.Role;

@Service
public class MultipartFileUploadService  implements IMultipartFileUploadService {
	
	@Autowired
	private ProfilePicPath proPic;
	
	@Override
	public boolean store(MultipartFile file, int playerId) {
		
		File desPath = new File(proPic.getProPicDest() + Role.PLAYER.toString() + String.valueOf(playerId) + proPic.getPicType());
		try {
			file.transferTo(desPath);
			return true;
		} catch (IOException e) {
			System.out.println(e.getMessage());
			return false;
		}
	}

	@Override
	public File load(String filename) {	
		String filepath = proPic.getProPicDest() + filename + proPic.getPicType(); 
		Path path = Paths.get(filepath);
		return path.toFile();
	}

}
