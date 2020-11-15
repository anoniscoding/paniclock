package com.anoniscoding.paniclock.domain.base

import io.reactivex.Scheduler

interface PostExecutionThread {
    val scheduler: Scheduler
}