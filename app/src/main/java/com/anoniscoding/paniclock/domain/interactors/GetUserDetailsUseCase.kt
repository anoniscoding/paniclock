package com.anoniscoding.paniclock.domain.interactors

import com.anoniscoding.paniclock.domain.base.SynchronousUseCase
import com.anoniscoding.paniclock.models.User
import com.anoniscoding.paniclock.domain.repositories.UserRepository
import javax.inject.Inject

class GetUserDetailsUseCase @Inject constructor(
    private val _userRepository: UserRepository
) : SynchronousUseCase<User, Nothing?>() {

    override fun buildUseCaseSynchronous(params: Nothing?): User {
        return _userRepository.getUserDetails()
    }
}