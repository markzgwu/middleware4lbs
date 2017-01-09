package org.examples.v1;

import java.util.LinkedList;
import java.util.Queue;

/**
 * 23.6 �Ľ�QoS�ļ�����<p/>
 * �����㷨����Ȩ��ƽ���У�WeightedFairQueueing���㷨<p/>
 * @author HouLei
 *
 * @param <E>
 */
public class WeightedFairQueueing<E>{

	/**
	 * Ĭ�ϼ�Ȩ���еĴ�С
	 */
	public static final int DefaultSizeOfQueue = 8;
	/**
	 * ������������ͬ�����ݷ��������ͬ�Ķ��С�
	 *
	 * @param <E> ���ݷ���
	 */
	public static interface Classifier<E>{
		/**
		 * @param data ���ݷ������
		 * @return ���е�Index
		 */
		int classify(E data);
	}
	private int [] weights;//ÿ�����е�Ȩ��
	private int [] sizes;//ÿ�����еĴ�С
	private Classifier<E> classifier;//������
	private Queue<E> queues [] ;//����Ȩ����
	private int currentQueue = 0;//��ǰ����
	private int polledDatas = 0;//��ǰ��������ȡ���ķ�������
	
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
	 * ������в������������ݷ���
	 * @param e ������ķ���
	 * @return �Ƿ����ɹ�,δ�ɹ�����ζ�ű��β�������ķ��齫��������
	 */
	public synchronized boolean offer(E e){
		int num = classifier.classify(e);
		if(queues[num].size()<sizes[num]){
			return queues[num].offer(e);
		}
		return false;
	}

	/**
	 * �Ӷ�����ȡ�����ݷ��飬��������û�����ݷ��飬�򷵻�<code>null</code>��
	 * @return �Ӷ�����ȡ�������ݷ��顣
	 */
	public synchronized E poll(){
		E e = null;//��ȡ�������ݷ���
		//�ӵ�ǰ������ȡ�����ݷ���
		e = pollCurrentQueue();
		if(e!=null)return e;
		//��ǰ�����ǿն��еĴ���ȥ�������г���ȡ��
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
	 * ָ���¼���ݴ���<p/>
	 * ��ǰ���к͵�ǰ������ȡ�������� �����仯ʱ�����ݴ���ʹ֮����ѭ��ȡ��������
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
	 * �ӵ�ǰ������ȡ�����ݷ��飬����ǰ����Ϊ�գ��򷵻�null��
	 */
	private E pollCurrentQueue(){
		E e = null;//��ȡ�������ݷ���
		polledDatas++;
		e = queues[currentQueue].poll();
		ensureCycle();
		return e;
	}
	
	/**
	 * ��������
	 */
	public static void main(String[] args) {
		//�Զ������ݷ�����󣬼򵥵㣬num��Ϊ���������ж�������
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
		//��Ȩ���е�Ȩ��
		int weights[] = new int []{5,3,2};
		//�Զ���������������ݷ��������ͬ�Ķ��С�
		Classifier cf = new Classifier(){
			public int classify(Object data) {
				if(data instanceof DataPackage){
					return ((DataPackage)data).num;
				}
				return 0;
			}
		};
		//����WFQ����
		WeightedFairQueueing<DataPackage> wfq = 
					new WeightedFairQueueing<DataPackage>(weights,cf);
		//������ݷ���
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
		//ȡ����������
		for(int i=0;i<15;i++){
			System.out.println(wfq.poll());
		}
	}

}

