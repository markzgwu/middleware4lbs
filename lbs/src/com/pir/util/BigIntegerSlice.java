package com.pir.util;

import java.math.BigInteger;
import java.util.*;

public class BigIntegerSlice implements List, java.io.Serializable {
	private List list;
	private int slice;
	private int width;
	private transient BigInteger mask;
	
	private void readObject(java.io.ObjectInputStream in) throws java.io.IOException, ClassNotFoundException {
		in.defaultReadObject();
		mask = BigInteger.ONE.shiftLeft(width).subtract(BigInteger.ONE);
	}
	
	private BigInteger getSlice(BigInteger src, int slice, int width) {
		return src.shiftRight(slice * width).and(mask);
	}
	
	private BigInteger setSlice(BigInteger src, BigInteger dst, int slice, int width) {
		return dst.and(mask.shiftLeft(slice * width).not()).or(src.and(mask).shiftLeft(slice * width));
	}
	
	private class SliceIterator implements ListIterator {
		private ListIterator it;
		private BigInteger last;
		
		public SliceIterator() {it = list.listIterator();}
		public SliceIterator(int index) {it = list.listIterator(index);}
		public boolean hasNext() {return it.hasNext();}
		public Object next() {
			last = (BigInteger)it.next();
			return getSlice(last, slice, width);
		}
		public boolean hasPrevious() {return it.hasPrevious();}
		public Object previous() {
			last = (BigInteger)it.previous();
			return getSlice(last, slice, width);
		}
		public int nextIndex() {return it.nextIndex();}
		public int previousIndex() {return it.previousIndex();}
		public void remove() {
			if (last == null)
				throw new IllegalStateException();
			last = null;
			it.remove();
		}
		public void set(Object o) {
			if (last == null)
				throw new IllegalStateException();
			it.set(setSlice((BigInteger)o, last, slice, width));
		}
		public void add(Object o) {
			if (last == null)
				throw new IllegalStateException();
			last = null;
			it.add(setSlice((BigInteger)o, BigInteger.ZERO, slice, width));
		}
	}
	
	private class SliceElement {
		private Object obj;
		private Object sliceObj;
		public SliceElement(Object obj) {this.obj = obj; this.sliceObj = null;}
		public boolean equals(Object o) {
			if (obj == null && o == null)
				return true;
			if (!(o instanceof BigInteger))
				return false;
			if (sliceObj == null)
				sliceObj = getSlice((BigInteger)obj, slice, width);
			return sliceObj.equals(getSlice((BigInteger)o, slice, width));
		}
	}
	
	public BigIntegerSlice(List list, int slice, int width) {
		this.list = list;
		this.slice = slice;
		this.width = width;
		this.mask = BigInteger.ONE.shiftLeft(width).subtract(BigInteger.ONE);
	}
	
	public int size() {return list.size();}
	public boolean isEmpty() {return list.isEmpty();}
	public boolean contains(Object o) {
		return (list.contains(new SliceElement(o)));
	}
	public Iterator iterator() {
		return new SliceIterator();
	}
	public Object[] toArray() {
		Object[] result = new Object[list.size()];
		int i = 0;
		Iterator it = list.iterator();
		while (it.hasNext()) {
			result[i++] = getSlice((BigInteger)it.next(), slice, width);
		}
		return result;
	}
	public Object[] toArray(Object[] result) {
		if (result.length < list.size())
			return toArray();
		int i = 0;
		Iterator it = list.iterator();
		while (it.hasNext()) {
			result[i++] = getSlice((BigInteger)it.next(), slice, width);
		}
		return result;
	}
	public boolean add(Object o) {
		return list.add(setSlice((BigInteger)o, BigInteger.ZERO, slice, width));
	}
	public boolean remove(Object o) {
		return list.remove(new SliceElement(o));
	}
	public boolean containsAll(Collection c) {
		Iterator it = c.iterator();
		while (it.hasNext()) {
			if (!contains(it.next()))
				return false;
		}
		return true;
	}
	public boolean addAll(Collection c) {
		boolean result = false;
		Iterator it = c.iterator();
		while (it.hasNext()) {
			result = result || add(it.next());
		}
		return result;
	}
	public boolean addAll(int index, Collection c) {
		int size = size();
		Iterator it = c.iterator();
		while (it.hasNext()) {
			add(index++, it.next());
		}
		return (size != size());
	}
	public boolean removeAll(Collection c) {
		boolean result = false;
		Iterator it = c.iterator();
		while (it.hasNext()) {
			Object o = it.next();
			while (remove(o))
				result = true;
		}
		return result;
	}
	public boolean retainAll(Collection c) {
		boolean result = false;
		Iterator it = list.iterator();
		while (it.hasNext()) {
			Object o = ((List)it.next()).get(slice);
			if (!c.contains(o)) {
				it.remove();
				result = true;
			}
		}
		
		return result;
	}
	public void clear() {
		list.clear();
	}
	public boolean equals(Object o) {
		if (!(o instanceof BigIntegerSlice))
			return false;
		BigIntegerSlice s = (BigIntegerSlice)o;
		return (slice == s.slice && width == s.width && list.equals(s.list));
	}
	public int hashCode() {
		return list.hashCode() + slice + width * 11;
	}
	public Object get(int index) {
		return getSlice((BigInteger)list.get(index), slice, width);
	}
	public Object set(int index, Object element) {
		return list.set(index, setSlice((BigInteger)element, (BigInteger)list.get(index), slice, width));
	}
	public void add(int index, Object element) {
		list.add(index, setSlice((BigInteger)element, BigInteger.ZERO, slice, width));
	}
	public Object remove(int index) {
		return getSlice((BigInteger)list.remove(index), slice, width);
	}
	public int indexOf(Object o) {
		return list.indexOf(new SliceElement(o));
	}
	public int lastIndexOf(Object o) {
		return list.lastIndexOf(new SliceElement(o));
	}
	public ListIterator listIterator() {
		return new SliceIterator();
	}
	public ListIterator listIterator(int index) {
		return new SliceIterator(index);
	}
	public List subList(int fromIndex, int toIndex) {
		return new BigIntegerSlice(list.subList(fromIndex, toIndex), slice, width);
	}
}
