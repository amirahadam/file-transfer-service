package com.cognixia.service;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import com.cognixia.model.File;
import com.cognixia.repository.FileRepository;

@Service
public class FileServiceImpl implements FileService {
	
	private FileRepository fileRepository;
	
	private static final SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
	
	public FileServiceImpl(FileRepository fileRepository) {
		this.fileRepository = fileRepository;
	}

	
	@Override
	public File saveFile(MultipartFile file) throws Exception{
		String fileName= StringUtils.cleanPath(file.getOriginalFilename());
		try {
			if(fileName.contains("..")) {
				throw new Exception("Filename Error");
			}
			File files = new File(fileName,file.getContentType(),file.getBytes());
			return fileRepository.save(files);
		}catch(Exception e) {
			throw new Exception("Could not save file: " + fileName);
		}
	}


	@Override
	public File getFile(String fileId) throws Exception {
		// TODO Auto-generated method stub
		return fileRepository.findById(fileId).orElseThrow(() -> new Exception("File not found with Id: " + fileId));
	}
	

	@Override
	public File toIntranet(File file) throws Exception {
		// TODO Auto-generated method stub
		String fileId = file.getFileId();
		String fileName = file.getFileName();
		String fileType = file.getFileType();
		byte[] data = file.getData();
		int userid = file.getUploadedby();
		Date datetime = new Date();
		
		File toIntranet = new File(fileId,fileName,fileType,data, format.format(datetime), userid);
		return fileRepository.save(toIntranet);
	}
	
	public List<File> getFiles(){
		return fileRepository.findAll();
	}



}
