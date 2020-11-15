package com.anoniscoding.paniclock.domain.base

abstract class SynchronousUseCase<out T, in Params> {

    abstract fun buildUseCaseSynchronous(params: Params? = null): T

    open fun execute(params: Params? = null): T {
        return this.buildUseCaseSynchronous(params)
    }

}