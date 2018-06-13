package com.frame.model.base.mvp;

import java.util.Arrays;
import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

import io.reactivex.disposables.Disposable;
import io.reactivex.exceptions.Exceptions;


/**
 * Created by MrZ on 2017/10/11. 14:56
 * project delin
 * Explain Subscription集合，方便后期引入darger
 */

public final class DisposableList implements Disposable{

    //private Set<Subscription> subscriptions;
    //private volatile boolean unsubscribed;

    private Set<Disposable> disposables;
    private volatile boolean undisposabled;

    public DisposableList() {
    }

    /*public DisposableList(final Subscription... subscriptions) {
        this.subscriptions = new HashSet<Subscription>(Arrays.asList(subscriptions));
    }*/

    public DisposableList(final Disposable... disposables){
        this.disposables = new HashSet<Disposable>(Arrays.asList(disposables));
    }

    /**
     * @description 取消本身和所有内部订阅
     * @author Andy.fang
     * @createDate
     * @version 1.0
     */
    @Override
    public void dispose() {
        if (!undisposabled) {
            Collection<Disposable> undisposable = null;
            synchronized (this) {
                if (undisposabled) {
                    return;
                }
                undisposabled = true;
                undisposable = disposables;
                disposables = null;
            }
            disposableFromAll(disposables);//只执行一次
        }
    }

    @Override
    public boolean isDisposed() {
        return undisposabled;
    }

   /* @Override
    public boolean isUnsubscribed() {
        return unsubscribed;
    }*/

    /**
     * @description 单个订阅加入集合
     * @author Andy.fang
     * @createDate
     * @version 1.0
     */
    public void add(final Disposable s) {
        if (s.isDisposed()) {
            return;
        }
        if (!undisposabled) {
            synchronized (this) {
                if (!undisposabled) {
                    if (disposables == null) {
                        disposables = new HashSet<Disposable>(4);
                    }
                    disposables.add(s);
                    return;
                }
            }
        }
        s.dispose();
    }

    /**
     * @description 把订阅添加到集合
     * @author Andy.fang
     * @createDate
     * @version 1.0
     */
    public void addAll(final Disposable... disposables) {
        if (!undisposabled) {
            synchronized (this) {
                if (!undisposabled) {
                    if (this.disposables == null) {
                        this.disposables = new HashSet<Disposable>(disposables.length);
                    }

                    for (Disposable s : disposables) {
                        if (!s.isDisposed()) {
                            this.disposables.add(s);
                        }
                    }
                    return;
                }
            }
        }

        for (Disposable s : disposables) {
            s.dispose();
        }
    }

    /**
     * @description 删除一个订阅
     * @author Andy.fang
     * @createDate
     * @version 1.0
     */
    public void remove(final Disposable s) {
        if (!undisposabled) {
            boolean undispose = false;
            synchronized (this) {
                if (undisposabled || disposables == null) {
                    return;
                }
                undispose = disposables.remove(s);
            }
            if (undispose) {
                s.dispose();
            }
        }
    }

    /**
     * @description 取消所有订阅
     * @author Andy.fang
     * @createDate
     * @version 1.0
     */
    public void clear() {
        if (!undisposabled) {
            Collection<Disposable> undispose = null;
            synchronized (this) {
                if (undisposabled || disposables == null) {
                    return;
                } else {
                    undispose = disposables;
                    disposables = null;
                }
            }
            disposableFromAll(disposables);
        }
    }


    /**
     * @description
     * @author Andy.fang
     * @createDate
     * @version 1.0
     */
    private static void disposableFromAll(Collection<Disposable> disposables) {
        if (disposables == null) {
            return;
        }
        for (Disposable s : disposables) {
            try {
                s.dispose();
            } catch (Throwable e) {
                Exceptions.throwIfFatal(e);
            }
        }
    }

    /**
     * @description 返回true,如果这个组合不是unsubscribed和subscriptions
     * @author Andy.fang
     * @createDate
     * @version 1.0
     */
    public boolean hasDisposeables() {
        if (!undisposabled) {
            synchronized (this) {
                return !undisposabled && disposables != null && !disposables.isEmpty();
            }
        }
        return false;
    }




}
