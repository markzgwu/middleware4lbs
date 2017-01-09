package org.examples.v1;

import java.util.LinkedList;
import java.util.Queue;

/**
 * 23.6 改进QoS的技术。<p/>
 * 调度算法：加权公平队列（WeightedFairQueueing）算法<p/>
 * @author HouLei
 *
 * @param <E>
 */
public class WeightedFairQueueing<E>{

	/**
	 * 默认加权队列的大小
	 */
	public static final int DefaultSizeOfQueue = 8;
	/**
	 * 分类器。将不同的数据分组分往不同的队列。
	 *
	 * @param <E> 数据分组
	 */
	public static interface Classifier<E>{
		/**
		 * @param data 数据分组对象
		 * @return 队列的Index
		 */
		int classify(E data);
	}
	private int [] weights;//每个队列的权重
	private int [] sizes;//每个队列的大小
	private Classifier<E> classifier;//分类器
	private Queue<E> queues [] ;//优先权队列
	private int currentQueue = 0;//当前队列
	private int polledDatas = 0;//当前队列中已取出的分组数量
	
	public WeightedFairQueueing(int[] weights, int[] sizes, Classifier classifier) {
		init(weights, sizes, classifier);
	}
	
	public WeightedFairQueueing(int[] weights, Classifier classifier) {
		int sizes [] = new int[weights.length];
		for(int i=0;i<sizes.length;i++){
			sizes[i]=DefaultSizeOfQueue;
		}
		init(weights,sizes,classifier);
	}

	@SuppressWarnings("unchecked")
	private void init(int[] weights, int[] sizes, Classifier classifier) {
		if(weights==null || sizes==null || weights.length!=sizes.length){
			throw new IllegalArgumentException();
		}
		this.weights = weights;
		this.sizes = sizes;
		this.classifier = classifier;
		queues = new Queue[weights.length];
		for(int i=0;i<weights.length;i++){
			queues[i]=new LinkedList<E>();
		}
	}
	
	/**
	 * 向队列中插入待处理的数据分组
	 * @param e 待处理的分组
	 * @return 是否插入成功,未成功则意味着本次插入操作的分组将被丢弃。
	 */
	public synchronized boolean offer(E e){
		int num = classifier.classify(e);
		if(queues[num].size()<sizes[num]){
			return queues[num].offer(e);
		}
		return false;
	}

	/**
	 * 从队列中取出数据分组，若队列中没有数据分组，则返回<code>null</code>。
	 * @return 从队列中取出的数据分组。
	 */
	public synchronized E poll(){
		E e = null;//待取出的数据分组
		//从当前队列中取出数据分组
		e = pollCurrentQueue();
		if(e!=null)return e;
		//当前队列是空队列的处理，去其他队列尝试取数
		for(int i=1;i<queues.length;i++){
			polledDatas=0;currentQueue++;
			ensureCycle();
			if((e=pollCurrentQueue())!=null){
				break;
			}
		}
		return e;
	}
	
	/**
	 * 指针记录的容错处理。<p/>
	 * 当前队列和当前队列已取出分组数 发生变化时，做容错处理，使之构成循环取数操作。
	 */
	private void ensureCycle(){
		if(currentQueue>=weights.length){
			currentQueue=0;polledDatas=0;
		}
		if(polledDatas>=weights[currentQueue]){
			polledDatas=0;currentQueue++;
		}
		if(currentQueue>=weights.length){
			currentQueue=0;polledDatas=0;
		}
	}

	/**
	 * 从当前队列中取出数据分组，若当前队列为空，则返回null。
	 */
	private E pollCurrentQueue(){
		E e = null;//待取出的数据分组
		polledDatas++;
		e = queues[currentQueue].poll();
		ensureCycle();
		return e;
	}
	
	/**
	 * 测试用例
	 */
	public static void main(String[] args) {
		//自定义数据分组对象，简单点，num作为分类器的判断条件。
		class DataPackage{
			int num;
			Object other;
			public DataPackage(int right,Object other){
				this.num=right;this.other=other;
			}
			public String toString(){
				return "num= "+num+" /tother= "+other;
			}
		}
		//加权队列的权重
		int weights[] = new int []{5,3,2};
		//自定义分类器，将数据分组分往不同的队列。
		Classifier cf = new Classifier(){
			public int classify(Object data) {
				if(data instanceof DataPackage){
					return ((DataPackage)data).num;
				}
				return 0;
			}
		};
		//创建WFQ对象
		WeightedFairQueueing<DataPackage> wfq = 
					new WeightedFairQueueing<DataPackage>(weights,cf);
		//添加数据分组
		int index=1;
		for(int i=0;i<6;i++){
			wfq.offer(new DataPackage(0,index++));
		}
		for(int i=0;i<4;i++){
			wfq.offer(new DataPackage(1,index++));
		}
		for(int i=0;i<3;i++){
			wfq.offer(new DataPackage(2,index++));
		}
		//取出分组数据
		for(int i=0;i<15;i++){
			System.out.println(wfq.poll());
		}
	}

}

