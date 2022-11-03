package com.cognixia.service;

import java.util.List;

import org.springframework.web.multipart.MultipartFile;

import com.cognixia.model.File;

public interface FileService {
	File saveFile(MultipartFile file) throws Exception;

	File getFile(String fileId) throws Exception;

	File toIntranet(File file) throws Exception;
	
}