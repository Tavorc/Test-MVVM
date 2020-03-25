package com.test.user.tavorapplication.infrastructure.network

import io.reactivex.*
import io.reactivex.schedulers.Schedulers
import retrofit2.Call
import retrofit2.CallAdapter
import retrofit2.Retrofit
import java.lang.reflect.Type

class ObserveOnMainCallAdapterFactory(internal val scheduler: Scheduler) : CallAdapter.Factory() {

    override fun get(returnType: Type, annotations: Array<Annotation>, retrofit: Retrofit): CallAdapter<*, *>? {

        if (getRawType(returnType) != Observable::class.java && getRawType(returnType) != Single::class.java && getRawType(returnType) != Completable::class.java && getRawType(returnType) != Flowable::class.java) {
            return null
        }
        when(getRawType(returnType)){
            Observable::class.java ->{
                return handleObservable(retrofit, returnType, annotations)
            }
            Single::class.java ->{
                return handleSingle(retrofit, returnType, annotations)
            }
            Completable::class.java ->{
                return handleCompletable(retrofit, returnType, annotations)
            }
            Flowable::class.java ->{
                return handleFlowable(retrofit, returnType, annotations)
            }
        }
        return null
    }

    /**
     * adapt call with the Single(type of observable response
     * And set the observeOn and subscribeOn
     */
    private fun handleSingle(retrofit: Retrofit, type: Type, annotations: Array<Annotation>): CallAdapter<Any, Single<*>> {

        val delegate = retrofit.nextCallAdapter(this, type, annotations) as CallAdapter<Any, Single<*>>

        return object : CallAdapter<Any, Single<*>> {
            override fun responseType(): Type {
                return delegate.responseType()
            }

            override fun adapt(call: Call<Any>): Single<*> {
                val s = delegate.adapt(call)

                return s
                    .observeOn(scheduler)
                    .subscribeOn(Schedulers.io())
            }
        }
    }

    /**
     * adapt call with the Completable(type of observable response
     * And set the observeOn and subscribeOn
     */
    private fun handleCompletable(retrofit: Retrofit, type: Type, annotations: Array<Annotation>): CallAdapter<Any, Completable> {
        val delegate = retrofit.nextCallAdapter(this, type, annotations) as CallAdapter<Any, Completable>

        return object : CallAdapter<Any, Completable> {
            override fun responseType(): Type {
                return delegate.responseType()
            }

            override fun adapt(call: Call<Any>): Completable {
                val s = delegate.adapt(call)

                return s.observeOn(scheduler).subscribeOn(Schedulers.io())
            }
        }
    }
    /**
     * adapt call with the Flowable(type of observable response
     * And set the observeOn and subscribeOn
     */
    private fun handleFlowable(retrofit: Retrofit, type: Type, annotations: Array<Annotation>): CallAdapter<Any, Flowable<*>> {
        val delegate = retrofit.nextCallAdapter(this, type, annotations) as CallAdapter<Any, Flowable<*>>

        return object : CallAdapter<Any, Flowable<*>> {
            override fun responseType(): Type {
                return delegate.responseType()
            }

            override fun adapt(call: Call<Any>): Flowable<*> {
                val s = delegate.adapt(call)

                return s.observeOn(scheduler).subscribeOn(Schedulers.io())
            }
        }
    }

    /**
     * adapt call with the Observable(type of observable response
     * And set the observeOn and subscribeOn
     */
    private fun handleObservable(retrofit: Retrofit, type: Type, annotations: Array<Annotation>): CallAdapter<Any, Observable<*>> {
        val delegate = retrofit.nextCallAdapter(this, type, annotations) as CallAdapter<Any, Observable<*>>

        return object : CallAdapter<Any, Observable<*>> {
            override fun responseType(): Type {
                return delegate.responseType()
            }

            override fun adapt(call: Call<Any>): Observable<*> {
                val s = delegate.adapt(call)

                return s.observeOn(scheduler).subscribeOn(Schedulers.io())
            }
        }
    }


}