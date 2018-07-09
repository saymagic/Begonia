package cn.saymagic.begonia.pojo

/**
 * Created by caoyanming on 2018/7/8.
 */
class AsyncResult {

    var result: Any? = null

    var error: Throwable? = null

    constructor() {
    }

    constructor(result: Any) {
        this.result = result
    }

    constructor(error: Throwable) {
        this.error = error
    }

    fun isSuccess() : Boolean = error == null

    fun isFailed() : Boolean = !isSuccess()

    companion object {
        fun success(): AsyncResult {
            return AsyncResult()
        }

        fun success(result: Any): AsyncResult {
            return AsyncResult(result)
        }

        fun error(err: Throwable): AsyncResult {
            return AsyncResult(err)
        }
    }
}

