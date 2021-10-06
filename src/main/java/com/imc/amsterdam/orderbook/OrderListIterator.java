package com.imc.amsterdam.orderbook;

import java.util.Iterator;
import java.util.NoSuchElementException;

public class OrderListIterator implements Iterator<IOrder>
{
    IOrder next = null;

    protected OrderListIterator(IOrder head) {
        this.next = head;
    }

    @Override
    public boolean hasNext() {
        return next != null;
    }

    @Override
    public IOrder next() {
        if(next != null) {
            IOrder next = this.next;
            this.next = next.getNext();
            return next;
        }
        throw new NoSuchElementException();
    }

    @Override
    public void remove() {
        throw new UnsupportedOperationException("Use OrderList:remove(IOrder)");
    }
}
