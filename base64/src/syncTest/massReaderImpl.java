package syncTest;

import java.util.List;

public class massReaderImpl implements massReader {

	private List<Integer> array;
	private final String name;
	
	@Override
	public List<Integer> getArray() {
		
		return array;
	}
	@Override
	public void setArray(List<Integer> array) {
		this.array = array;
	}
	
	private void setInfo(String str){
		System.out.println(str);
	}

	public massReaderImpl(List<Integer> arr, String name) {
		this.name = name;
		setArray(arr);
	}
	
	private Integer getArrayItem(int Index){
		return getArray().get(Index);
	}
	
	private void setArrayItem(int index,int i){
		getArray().set(index, i);
	}

	@Override
	public void run() {
		Thread.currentThread().setName(name);
			
		for (int i = 0; i < getArray().size(); i++) {
			synchronized (array) {
				setInfo(name + ": " + getArrayItem(i));
				setArrayItem(i, i + 10);
				try {
					Thread.sleep(100);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
		}
	}

	
	


}
