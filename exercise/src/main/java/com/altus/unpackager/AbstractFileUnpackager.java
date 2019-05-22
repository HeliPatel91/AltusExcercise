package com.altus.unpackager;

import com.altus.exception.UnpackingErrorDetails;
import java.util.ArrayList;
import java.util.List;

public abstract class AbstractFileUnpackager implements FileUnpackager {
	protected List<UnpackingErrorDetails> unpackingErrors = new ArrayList<>();

	protected void trackDetailsForError(String errorMessage, String errorCode, String fileName, String... messageParams) {
		final UnpackingErrorDetails errorDetails = new UnpackingErrorDetails();

		errorDetails.setErrorCode(errorCode);
		errorDetails.setMessage(errorMessage);
		errorDetails.setFileName(fileName);
		errorDetails.setParams(messageParams);

		unpackingErrors.add(errorDetails);
	}

}
