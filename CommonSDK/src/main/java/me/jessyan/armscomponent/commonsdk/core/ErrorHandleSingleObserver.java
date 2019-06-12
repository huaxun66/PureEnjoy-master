package me.jessyan.armscomponent.commonsdk.core;

import io.reactivex.SingleObserver;
import io.reactivex.annotations.NonNull;
import io.reactivex.disposables.Disposable;
import me.jessyan.rxerrorhandler.core.RxErrorHandler;
import me.jessyan.rxerrorhandler.handler.ErrorHandlerFactory;

public abstract class ErrorHandleSingleObserver<T> implements SingleObserver<T> {
    private ErrorHandlerFactory mHandlerFactory;

    public ErrorHandleSingleObserver(RxErrorHandler rxErrorHandler){
        this.mHandlerFactory = rxErrorHandler.getHandlerFactory();
    }


    @Override
    public void onSubscribe(@NonNull Disposable d) {
    }

    @Override
    public void onSuccess(@NonNull T t) {
    }


    @Override
    public void onError(@NonNull Throwable t) {
        t.printStackTrace();
        //如果你某个地方不想使用全局错误处理,则重写 onError(Throwable) 并将 super.onError(e); 删掉
        //如果你不仅想使用全局错误处理,还想加入自己的逻辑,则重写 onError(Throwable) 并在 super.onError(e); 后面加入自己的逻辑
        mHandlerFactory.handleError(t);
    }
}
