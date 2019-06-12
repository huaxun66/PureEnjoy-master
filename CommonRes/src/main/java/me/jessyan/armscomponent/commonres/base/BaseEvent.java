package me.jessyan.armscomponent.commonres.base;

public class BaseEvent<T>{

    public BaseEvent() {
        super();
    }

    public BaseEvent(T t) {
        super();
        this.t = t;
    }

    /**
     * event泛型对象
     */
    private T t;

    public T getT() {
        return t;
    }

    public void setT(T t) {
        this.t = t;
    }
}