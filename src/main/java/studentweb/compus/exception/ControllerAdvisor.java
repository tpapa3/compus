package studentweb.compus.exception;


import org.springframework.beans.factory.annotation.Value;
import org.springframework.dao.DataIntegrityViolationException;
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
	    	    

	    @ExceptionHandler(FileStorageException.class)
	    public ResponseEntity<String> handleFileStoreException(FileStorageException ex) {
	    	String message = ex.getMessage();

	        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(message);
	    }
	   
	    @ExceptionHandler(value = UsernameNotFoundException.class)
	    public ResponseEntity<String> handleUsernameNotFoundException(UsernameNotFoundException ex) {
	        
	        return  ResponseEntity.status( HttpStatus.NOT_FOUND).body(ex.getMessage());
	    }
	    
	    @ExceptionHandler(DataIntegrityViolationException.class)
	    public ResponseEntity<String> handleNullValue(DataIntegrityViolationException ex) {
	    	String message = ex.getMessage();

	        return ResponseEntity.status(HttpStatus.CONFLICT).body(message);
	    }

}
