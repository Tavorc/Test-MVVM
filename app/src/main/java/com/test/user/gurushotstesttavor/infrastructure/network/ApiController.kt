package com.test.user.tavorapplication.infrastructure.network


abstract class ApiController<T> {

    protected var mNetworkManager: NetworkManager = NetworkManager.instance

    protected var api: T

    abstract fun getEndpoint(): String

    abstract fun getApiClass(): Class<T>

    init {
        api = mNetworkManager.registerController(this)
    }
}
