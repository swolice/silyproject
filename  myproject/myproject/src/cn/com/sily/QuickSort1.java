/** 
 * 文件名：		QuickSort1.java 
 * 
 * 版本信息: 	v1.0
 * 日期：		2011-7-5 
 * Copyright:  	Copyright(c) 2010
 * Corporation:	2011 
 * Company：		广州正道科技有限公司  
 */
package cn.com.sily;

/** 
 * 名称：	QuickSort1 
 * 描述： 
 * 创建人：	sily
 * 创建时间：	2011-7-5 下午02:05:21
 * 修改人：	
 * 修改时间：	
 * 修改备注： 
 * @version 1.0
 */
public class QuickSort1 {
	
	
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
			if(left >= right){
				return;
			}else{
				long pivot = theArray[right];
				
				int partition = partitionIt(left,right,pivot);
				
				recQuickSort(left, partition - 1);

				recQuickSort(partition+1, right);
			}
			
			
		}

		private int partitionIt(int left, int right, long pivot) {
			int leftPtr = left-1;
			int rightPtr = right;
			
			while(true){
				while(theArray[++leftPtr] < pivot){
					;
				}
				while(rightPtr>0&&theArray[--rightPtr] > pivot){
					;
				}
				if(leftPtr >= rightPtr){
					break;
				}else{
					swap(leftPtr,rightPtr);
				}
			}
			swap(leftPtr, right);
			return leftPtr;
		}

		private void swap(int leftPtr, int rightPtr) {
			long temp = theArray[leftPtr];
			theArray[leftPtr] = theArray[rightPtr];
			theArray[rightPtr] = temp;
		}
		
	}

}
