package com.altus.unpackager;


import com.altus.dto.ValidFileDTO;

public interface FileUnpackager {

	ValidFileDTO splitFile() throws Exception;

}
