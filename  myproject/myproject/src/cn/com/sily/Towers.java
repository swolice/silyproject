package cn.com.sily;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Stack;

/**
 * 归并递归算法
 * 名称：	Towers 
 * 描述： 
 * 创建人：	吉仕军
 * 创建时间：	2011-6-22 上午10:30:23
 * 修改人：	
 * 修改时间：	
 * 修改备注： 
 * @version 1.0
 */
public class Towers {
	
	static Stack<String> arrA = new Stack<String>();
	static Stack<String> arrB = new Stack<String>();
	static Stack<String> arrC = new Stack<String>();
	
	public static void main(String[] args) {
		for (int i = 5; i > -1; i--) {
			arrA.push(""+(i+1));
		}
		display();
		doTower(6, 'A', 'B', 'C');
		
	}
	
	public static void doTower(int topN, char from, char inter, char to) {
		if (topN == 1) {
			System.out.println(" disk " + topN + " from " + from + " to " + to);
			swapDisk(from,to);
			display();
		} else {
			doTower(topN - 1, from, to, inter);

			System.out.println(" disk " + topN + " from " + from + " to " + to);
			swapDisk(from,to);
			display();
			
			doTower(topN - 1, inter, from, to);
		}
	}
	
	public static void swapDisk(char from ,char to){
		String temp = "";
		switch (from) {
			case 'A':
				temp = arrA.pop();
				break;
			case 'B':
				temp = arrB.pop();
				break;
			case 'C':
				temp = arrC.pop();
				break;
			default:
				break;
		}
		switch (to) {
			case 'A':
				temp = arrA.push(temp);
				break;
			case 'B':
				temp = arrB.push(temp);
				break;
			case 'C':
				temp = arrC.push(temp);
				break;
			default:
				break;
		}
	}
	
	public static void display(){
		System.out.print(" A -> disk ");
		displayStack(arrA);
		System.out.println();
		System.out.print(" B -> disk ");
		displayStack(arrB);
		System.out.println();
		System.out.print(" C -> disk ");
		displayStack(arrC);
		System.out.println();
	}
	
	
	public static void displayStack(Stack<String> stack){
		Iterator<String> it = stack.iterator();
		while(it.hasNext()){
			String disk = it.next();
			System.out.print(disk + " ");
		}
	}

}

