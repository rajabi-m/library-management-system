package org.example.models;

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

    private class Node{
        private final T data;
        private Node next;

        public Node(T data){
            this.data = data;
            this.next = null;
        }
    }
}
