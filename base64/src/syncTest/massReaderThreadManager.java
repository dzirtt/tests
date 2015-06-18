package syncTest;

public interface massReaderThreadManager {

	void addReader(massReader read);
	void start();
	void stop();
	void removeAll();
	boolean waitTermination() throws InterruptedException;
	
	
}
