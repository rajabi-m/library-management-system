package org.example.model;

import java.util.Comparator;
import java.util.Iterator;

public class LinkedList<T> implements Iterable<T>{
    private Node head;

    public LinkedList() {
        this.head = null;
    }

    public void add(T data) {
        Node newNode = new Node(data);
        Node current = this.head;

        if (current == null) {
            this.head = newNode;
            return;
        }

        while (current.next != null) {
            current = current.next;
        }

        current.next = newNode;
    }

    public void remove(T data) {
        Node current = this.head;

        if (current == null) {
            return;
        }

        if (current.data.equals(data)) {
            this.head = current.next;
            return;
        }

        while (current.next != null) {
            if (current.next.data.equals(data)) {
                current.next = current.next.next;
                return;
            }
            current = current.next;
        }
    }


    @Override
    public Iterator<T> iterator() {
        return new Iterator<>() {
            private Node current = head;

            @Override
            public boolean hasNext() {
                return current != null;
            }

            @Override
            public T next() {
                T data = current.data;
                current = current.next;
                return data;
            }
        };
    }

    public void sort(Comparator<T> comparator){
        Node current = this.head;
        Node index;
        T temp;

        if (this.head == null) {
            return;
        }

        // Bubble sort
        while (current != null) {
            index = current.next;
            while (index != null) {
                if (comparator.compare(current.data, index.data) > 0) {
                    temp = current.data;
                    current.data = index.data;
                    index.data = temp;
                }
                index = index.next;
            }
            current = current.next;
        }
    }

    public boolean contains(T value){
        Node current = this.head;
        while (current != null) {
            if (current.data.equals(value)) {
                return true;
            }
            current = current.next;
        }
        return false;
    }

    public T get(int index){
        Node result = this.head;
        while (index > 0 && result != null){
            result = result.next;
            index --;
        }

        if (result == null){
            return null;
        }

        return result.data;
    }

    public T getHead() {
        if (this.head == null){
            return null;
        }
        return head.data;
    }

    private class Node{
        private T data;
        private Node next;

        public Node(T data){
            this.data = data;
            this.next = null;
        }
    }
}
