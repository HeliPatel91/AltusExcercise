package com.altus;

import static org.junit.Assert.assertTrue;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.fileUpload;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.Charset;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import com.altus.dto.ValidFileDTO;
import com.altus.unpackager.XMLFileUnpackager;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
public class ExerciseApplicationTests {

	private static final String FILE_DIRECTORY = "/submission/";

	@Autowired
	private CsvParser csvParser;
	
	@Autowired
	private MockMvc mvc;

	private static final Logger LOG = LoggerFactory.getLogger(ExerciseApplicationTests.class);

	@Test
	public void compareGeneratedFiles() throws Exception {
		final File file = new File(getClass().getResource(FILE_DIRECTORY + "inputFile.xml").toURI());
		final ValidFileDTO inputFileDTO = new ValidFileDTO(file, "inputFile");
		final XMLFileUnpackager fileUnpackager = new XMLFileUnpackager(inputFileDTO);
		final ValidFileDTO fileDTO;
		final long startTime;
		final long duration;

		startTime = System.currentTimeMillis();

		fileDTO = fileUnpackager.splitFile();

		duration = System.currentTimeMillis() - startTime;

		try (InputStream actualStream = fileDTO.getInputStream()) {
			assertTrue("expected and actual result don't match",
					csvParser.compareResult(FILE_DIRECTORY + "outputFile.csv", actualStream));
		} catch (IOException e) {
			LOG.error("Error in parsing the result file.", e);
		}
		LOG.info("Export time : {} ms", duration);

		assertTrue("Export is too slow, find a way to process below 1s", duration < 1000);
	}
	
	@Test
	public void convertXmlFile() throws Exception {
		
		final File file = new File(getClass().getResource(FILE_DIRECTORY + "inputFile.xml").toURI());
		MockMultipartFile multipartFile = new MockMultipartFile("inputFile",new FileInputStream(file));
        MvcResult mvcResult = this.mvc.perform(fileUpload("/convert").file(multipartFile))
        		.andDo(print())
                .andReturn();
        
        InputStream inputStream = new ByteArrayInputStream(mvcResult.getResponse().getContentAsString().getBytes(Charset.forName("UTF-8")));

        assertTrue("expected and actual result don't match",
				csvParser.compareResult(FILE_DIRECTORY + "outputFile.csv", inputStream));
	}
	
}
