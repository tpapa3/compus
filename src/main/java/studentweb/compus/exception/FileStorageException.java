package studentweb.compus.exception;



public class FileStorageException extends RuntimeException{

	private static final long serialVersionUID = 1L;

	public FileStorageException(String notStoreFile) {
		super(notStoreFile);
	}
}
