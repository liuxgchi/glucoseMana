package com.common.library.bean.local;

import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author Abdi
 * @description
 * @created Mar 27, 2013 8:52:05 PM
 */
public class CacheMap<K, V> {
	private int maxSize;

    private ConcurrentHashMap<K, Node> cache;

    private class Node {
        Node prev, next;
        K key;
        V item;
        int size;
    }

    private Node head, tail;

    public CacheMap(int maxSize) {
        this.maxSize = maxSize;
        clear();
    }

    public int getSize() {
        return cache.size();
    }

    public int getMaxSize() {
        return maxSize;
    }
    
    public Set<K> keySet()
    {
    	return cache.keySet();
    }

    public void put(K key, V item) {
        put(key, item , 1);
    }

    private void put(K key, V item, int size) {
        if (cache.containsKey(key))
            remove(key);
        while (cache.size() + size > maxSize) {
            if (!deleteLRU())
                break;
        }
        if (cache.size() + size > maxSize)
            return;
        Node node = new Node();
        node.key = key; 
        node.item = item; 
        node.size = size;
        cache.put(key, node);
        insertNode(node);
    }


    public V get(K key) {
        Node node = (Node)cache.get(key);
        if (node == null)
            return null;
        deleteNode(node);
        insertNode(node); // Move to the front of the list.
        return node.item;
    } 
  
    public V remove(K key) {
        Node node = (Node)cache.remove(key);
        if (node == null)
            return null;
        deleteNode(node);
        return node.item;
    }

    public boolean containsKey(K key) {
        return cache.containsKey(key);
    }

    public void clear() {
        cache = new ConcurrentHashMap<K, Node>();
        head = tail = null;
    }

    private void insertNode(Node node) {
        node.next = head;
        node.prev = null;
        if (head != null)
            head.prev = node;
        head = node;
        if (tail == null)
            tail = node;
    }

    private void deleteNode(Node node) {
        if (node.prev != null)
            node.prev.next = node.next;
        else
            head = node.next;
        if (node.next != null)
            node.next.prev = node.prev;
        else
            tail = node.prev;
    } 
    
    private boolean deleteLRU() {
        if (tail == null)
            return false;
        cache.remove(tail.key);
        deleteNode(tail);
        return true;
    }

    @Override
	public String toString() {
        StringBuffer buf = new StringBuffer();
        buf.append("LRU ");
        buf.append("/");
        buf.append(maxSize);
        buf.append(" Order: ");
        Node n = head;
        while (n != null) {
            buf.append(n.key);
            if (n.next != null)
                buf.append(", ");
            n = n.next;
        }
        return buf.toString();
    }
    
    public static void main(String[] args)
    {
    	CacheMap<String, Object> map = new CacheMap<String, Object>(3);
    	map.put("id", null);
    	map.put("1", new java.util.Date());
    	map.put("2", "3");
    	map.put("2", "33");
    	
    	System.out.println(map.getSize());
    }
}
