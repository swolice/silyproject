/** 
 * 文件名：		QuickSort3.java 
 * 
 * 版本信息: 	v1.0
 * 日期：		2011-7-5 
 * Copyright:  	Copyright(c) 2010
 * Corporation:	2011 
 * Company：		广州正道科技有限公司  
 */
package cn.com.sily;

/** 
 * 名称：	QuickSort3 
 * 描述： 
 * 创建人：	sily
 * 创建时间：	2011-7-5 下午02:05:21
 * 修改人：	
 * 修改时间：	
 * 修改备注： 
 * @version 1.0
 */
public class QuickSort3 {
	
	public static void main(String[] args) {
		int maxSize = 16;
		ArrayIns ai = new ArrayIns(maxSize);
		for (int i = 0; i < maxSize; i++) {
			long l = (int)(Math.random()*99);
			ai.insert(l);
		}
		ai.display();
		ai.quickSort();
		ai.display();
	}
	
	static class ArrayIns{
		private long[] theArray;
		private int items;
		
		public ArrayIns(int max){
			this.theArray = new long[max];
			this.items = 0;
		}
		
		public void insert(long l){
			this.theArray[items++] = l;
		}
		
		public void display(){
			System.out.print(" A = ");
			for (int i = 0; i < this.theArray.length; i++) {
				System.out.print(theArray[i] + " ");
			}
			System.out.println();
		}
		
		public void quickSort(){
			recQuickSort(0,items-1);
		}
		
		
		public void recQuickSort(int left,int right){
			int size = right - left + 1;
			
			if(size <= 10){
				insertSort(left,right);
			}else{
				long median = medianOf3(left,right);
				
				int partition = partitionIt(left,right,median);
				
				recQuickSort(left, partition - 1);

				recQuickSort(partition+1, right);
			}
		}

		private void insertSort(int left, int right) {
			int index;
			
			for (int i = left+1; i <= right; i++) {
				long temp = theArray[i];
				index = i;
				
				while(index > left && theArray[index-1]>temp){
					theArray[index] = theArray[index-1];
					index--;
				}
				theArray[index] = temp;
			}
		}

		private long medianOf3(int left, int right) {
			int center = (left + right)/2;
			
			if(theArray[left]>theArray[center]){
				swap(left, center);
			}
			if(theArray[left]>theArray[right]){
				swap(left, right);
			}
			if(theArray[center]>theArray[right]){
				swap(center, right);
			}
			
			swap(center, right-1);
			return theArray[right-1];
		}

		private int partitionIt(int left, int right, long pivot) {
			int leftPtr = left;
			int rightPtr = right-1;
			
			while(true){
				while(theArray[++leftPtr] < pivot){
					;
				}
				while(theArray[--rightPtr] > pivot){
					;
				}
				if(leftPtr >= rightPtr){
					break;
				}else{
					swap(leftPtr,rightPtr);
				}
			}
			swap(leftPtr, right-1);
			return leftPtr;
		}

		private void swap(int leftPtr, int rightPtr) {
			long temp = theArray[leftPtr];
			theArray[leftPtr] = theArray[rightPtr];
			theArray[rightPtr] = temp;
		}
		
	}

}
