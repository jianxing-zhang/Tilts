package com.cloud.titls.ex;

public class EmptyFileException extends FileUploadException {

    public EmptyFileException() {
        super();
    }

    public EmptyFileException(String message) {
        super(message);
    }

    public EmptyFileException(String message, Throwable cause) {
        super(message, cause);
    }

    public EmptyFileException(Throwable cause) {
        super(cause);
    }
}
