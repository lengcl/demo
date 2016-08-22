package leng;

import java.util.LinkedList;
import java.util.Scanner;

public class Queue {

	//realize queue with linklist
    private LinkedList<String> queue = new LinkedList<String>();
    //入队列
    public void enQueue(String t) {
        queue.add(t);
    }
    //出队列
    public Object deQueue() {
        return queue.removeFirst();
    }
    //判断队列是否为空
    public boolean isQueueEmpty() {
        return queue.isEmpty();
    }
    //判断队列是否包含t
    public boolean contains(String t) {
        return queue.contains(t);
    }
 
    //用于测试这个类
    public static void main(String[] args) {
        Queue qqq = new Queue();
        Scanner sc = new Scanner(System.in);
        System.out.println("[0] Input a object to Queue: ");
        System.out.println("[1] Output a object from Queue: ");
        System.out.println("[2] Test a object in or not in Queue: ");
        System.out.println("[3] If the Queue is empty: ");
        System.out.println("[4] Exit!"); 
        System.out.print("Enter a number: ");
        int opt = sc.nextInt();
        while (true) {
            switch (opt) {
                case 0: {
                    System.out.print("[0] Input a object to Queue: ");
                    sc = new Scanner(System.in);
                    String a = sc.nextLine();
                    System.out.println(a);
                    qqq.enQueue(a);
                    System.out.println(qqq);
                    break;
                }
                case 1: {
                    System.out.print("[1] Output a object from Queue: ");
                    String a = (String)qqq.deQueue();
                    System.out.println(a);
                    break;
                }
                case 2: {
                    System.out.print("[2] Test a object in or not in Queue: ");
                    sc = new Scanner(System.in);
                    String a = sc.nextLine();
                    if (qqq.contains(a))
                        System.out.print("true");
                    else
                        System.out.print("false");
                    break;
                }
                case 3: { 
                    System.out.println("[3] If the Queue is empty: ");
                    if (qqq.isQueueEmpty())
                        System.out.print("true");
                    else
                        System.out.print("false");
                    break;
                }
                case 4: return;
            }
            System.out.println("[0] Input a object to Queue: ");
            System.out.println("[1] Output a object from Queue: ");
            System.out.println("[2] Test a object in or not in Queue: ");
            System.out.println("[3] If the Queue is empty: ");
            System.out.println("[4] Exit!"); 
            System.out.print("Enter a number: ");
            sc = new Scanner(System.in);
            opt = sc.nextInt();
        }   
    }
}
