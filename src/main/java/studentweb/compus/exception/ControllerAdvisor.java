package studentweb.compus.exception;

import java.io.FileNotFoundException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.multipart.MaxUploadSizeExceededException;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@ControllerAdvice
public class ControllerAdvisor extends ResponseEntityExceptionHandler{
	  @Value("${spring.servlet.multipart.max-file-size}")
	    private String maxAllowedSize;

	    @ExceptionHandler(MaxUploadSizeExceededException.class)
	    public ResponseEntity<String> handleMaxUploadSizeExceededException( MaxUploadSizeExceededException exception) {
	        String message = "Attempt to upload file with the size exceeded max allowed value = " + maxAllowedSize + " byte(s).";

	        return ResponseEntity.status(HttpStatus.PAYLOAD_TOO_LARGE).body(message);
	    }
	    

	    @ExceptionHandler(FileNotFoundException.class)
	    public ResponseEntity<String> handleFileFoundException(FileNotFoundException ex) {
	    	String message = ex.getMessage();

	        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(message);
	    }
	    

	    @ExceptionHandler(FileStorageException.class)
	    public ResponseEntity<String> handleFileStoreException(FileStorageException ex) {
	    	String message = ex.getMessage();

	        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(message);
	    }
	    
	    @ExceptionHandler(NoDataFoundException.class)
	    public ResponseEntity<String> handleFileAllException(NoDataFoundException ex) {
	    	String message = ex.getMessage();

	        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(message);
	    }
	    
	    @ExceptionHandler(UsernameNotFoundException.class)
	    public ResponseEntity<String> handleUsernameNotFoundException(NoDataFoundException ex) {
	    	String message = ex.getMessage();

	        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(message);
	    }
	    
	    

}
