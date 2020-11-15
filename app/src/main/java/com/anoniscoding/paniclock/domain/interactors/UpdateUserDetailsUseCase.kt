package com.anoniscoding.paniclock.domain.interactors

import com.anoniscoding.paniclock.domain.base.SynchronousUseCase
import com.anoniscoding.paniclock.models.User
import com.anoniscoding.paniclock.domain.repositories.UserRepository
import javax.inject.Inject

class UpdateUserDetailsUseCase @Inject constructor(
    private val _userRepository: UserRepository
) : SynchronousUseCase<Unit, UpdateUserDetailsUseCase.Params>() {

    override fun buildUseCaseSynchronous(params: Params?): Unit {
        requireNotNull(params) { "Params can't be null" }
        return _userRepository.updateUserDetails(params.user)
    }

    data class Params constructor(val user: User) {
        companion object {
            fun forUpdateUserDetails(user: User): Params {
                return Params(user)
            }
        }
    }
}