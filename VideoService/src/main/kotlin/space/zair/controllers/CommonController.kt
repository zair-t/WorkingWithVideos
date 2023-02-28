package space.zair.controllers

import io.micronaut.http.annotation.Controller
import io.micronaut.http.annotation.Get

@Controller
class CommonController {
    @Get("/state")
    fun getState(): String = "I'm working"
}