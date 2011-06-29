package cn.com.sily;

/**
 * 希尔排序java实现
 * 名称：	ArraySh 
 * 描述： 
 * 创建人：	sily
 * 创建时间：	2011-6-28 上午09:20:51
 * 修改人：	
 * 修改时间：	
 * 修改备注： 
 * @version 1.0
 */
public class ArraySh {

	private long[] theArray;
	private int nElems;
	
	public ArraySh(int max){
		theArray = new long[max];
		this.nElems = 0;
	}
	
	public void putData(long data){
		this.theArray[this.nElems] = data;
		this.nElems++;
	}
	
	public void displayData(){
		for (int i = 0; i < this.theArray.length; i++) {
			System.out.print(this.theArray[i]+"\t");
		}
		System.out.println();
	}
	
	public void shellSort(){
		int h=0;
		while(h<nElems){
			h = 3*h + 1;
		}
		while(h>0){
			for (int outer = h; outer < nElems; outer++) {
				long temp = theArray[outer]; 
				int inner = outer; 
				while(inner > h-1 && theArray[inner-h]>temp){
					theArray[inner] = theArray[inner-h];
					inner -= h;
				}
				theArray[inner] = temp;
			}
			h = (h-1)/3;
		}
	}
	
	public static void main(String[] args) {
		ArraySh ash = new ArraySh(10);
		for (int i = 0; i < 10; i++) {
			ash.putData((int)(Math.random()*100));
		}
		ash.displayData();
		
		ash.shellSort();
		
		ash.displayData();
	}
}
