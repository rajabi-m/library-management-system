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
        Node firstBefore = null;
        Node first = this.head;
        Node secondBefore = null;
        Node second = null;

        if (this.head == null) {
            return;
        }

        // Bubble sort
        while (first != null) {
            second = first.next;
            secondBefore = first;
            while (second != null) {
                if (comparator.compare(first.data, second.data) > 0) {
                    if (first != this.head) {
                        firstBefore.next = second;
                    }
                    else {
                        this.head = second;
                    }

                    if (first.next != second){
                        secondBefore.next = first;
                        Node tmp = first.next;
                        first.next = second.next;
                        second.next = tmp;
                    }
                    else {
                        first.next = second.next;
                        second.next = first;
                    }

                    Node temp = first;
                    first = second;
                    second = temp;
                }
                secondBefore = second;
                second = second.next;
            }
            firstBefore = first;
            first = first.next;
        }
    }

    public T getHead() {
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
