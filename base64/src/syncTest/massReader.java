package syncTest;

import java.util.List;

public interface massReader extends Runnable {
	
	@Override
	void run();
	
	public void setArray(List<Integer> array);
	public List<Integer> getArray();

}
