package com.jmedia.routes

import io.ktor.server.config.*
import io.ktor.server.engine.*
import io.ktor.server.testing.*

abstract class BaseApiTest {
    protected fun runTest(block: suspend ApplicationTestBuilder.() -> Unit) = testApplication {
        environment {
            config = MapApplicationConfig()
            connector {
                port = 8080
            }
        }
        block()
    }
}