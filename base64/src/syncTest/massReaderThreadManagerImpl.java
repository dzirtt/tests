package syncTest;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

public class massReaderThreadManagerImpl implements massReaderThreadManager {

	private ExecutorService executor;
	
	public massReaderThreadManagerImpl(int threads) {
		
		executor = Executors.newFixedThreadPool(threads);
	}
	
	@Override
	public void addReader(massReader read) {
		executor.execute((Runnable)read);
	}

	@Override
	public void start() {
		
	}

	@Override
	public void stop() {
		executor.shutdown();		
	}

	@Override
	public void removeAll() {
		
	}
	
	@Override
	public boolean waitTermination() throws InterruptedException{
		return executor.isShutdown();
	}

}
