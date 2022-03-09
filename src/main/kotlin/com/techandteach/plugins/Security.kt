package com.techandteach.plugins

import com.techandteach.domain.security.TokenManager
import io.ktor.application.*
import io.ktor.auth.*
import io.ktor.auth.jwt.*

fun Application.configureSecurity(manager: TokenManager) {
    
    authentication {
            jwt {
                realm = manager.realm

                verifier(manager.accessVerifier)
                validate { credential -> manager.validateAccess(credential) }
            }
        }

}
