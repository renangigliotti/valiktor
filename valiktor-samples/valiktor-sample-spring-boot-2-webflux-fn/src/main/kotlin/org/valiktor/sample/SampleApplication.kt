/*
 * Copyright 2018-2019 https://www.valiktor.org
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package org.valiktor.sample

import com.fasterxml.jackson.module.kotlin.KotlinModule
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.context.annotation.Bean
import org.springframework.http.MediaType.APPLICATION_JSON
import org.springframework.web.reactive.function.server.router
import org.zalando.jackson.datatype.money.MoneyModule

@SpringBootApplication
class SampleApplication {

    @Bean
    fun kotlinModule() = KotlinModule()

    @Bean
    fun moneyModule() = MoneyModule()

    @Bean
    fun router(handler: EmployeeHandler) = router {
        accept(APPLICATION_JSON).nest {
            "/employees".nest {
                POST("/", handler::create)
            }
        }
    }
}

fun main(args: Array<String>) {
    runApplication<SampleApplication>(*args)
}