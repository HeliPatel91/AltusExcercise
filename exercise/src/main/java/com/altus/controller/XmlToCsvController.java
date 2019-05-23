package com.altus.controller;

import java.io.File;
import java.nio.file.Files;

import org.springframework.core.io.InputStreamResource;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.altus.dto.ValidFileDTO;
import com.altus.unpackager.XMLFileUnpackager;

@RestController
public class XmlToCsvController {

	@RequestMapping(method = RequestMethod.POST,value= "/convert")
	public ResponseEntity<InputStreamResource> convertXml(@RequestParam("inputFile") MultipartFile multipartfile) throws Exception
	{
		File xmlFile = Files.createTempFile("", ".xml").toFile();
		multipartfile.transferTo(xmlFile);
		
		final ValidFileDTO inputFileDTO = new ValidFileDTO(xmlFile, "inputFile");
		final XMLFileUnpackager fileUnpackager = new XMLFileUnpackager(inputFileDTO);
		final ValidFileDTO fileDTO;

	
		fileDTO = fileUnpackager.splitFile();

		InputStreamResource resource = new InputStreamResource(fileDTO.getInputStream());
		 
		return ResponseEntity.ok()
	            .contentLength(fileDTO.getFile().length())
	            .contentType(MediaType.parseMediaType("application/octet-stream"))
	            .body(resource);
	}
}
