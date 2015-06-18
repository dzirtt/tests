package syncTest;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class main {

	public static void main(String[] args) {
		
		List<Integer> arr = Arrays.asList(1,2,3,4,5,6,7);

		massReaderThreadManager manager = new massReaderThreadManagerImpl(2);
		
		manager.addReader(new massReaderImpl(arr, "Thread 1"));
		manager.addReader(new massReaderImpl(arr, "Thread 2"));
		
		manager.stop();
	}

}
