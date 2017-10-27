package com.picpay.httplogger.filter

import com.netflix.zuul.ZuulFilter
import com.netflix.zuul.context.RequestContext
import org.springframework.http.HttpMethod
import org.springframework.stereotype.Component
import java.io.BufferedReader

@Component
class DebugPreFilter : ZuulFilter() {

    override fun run(): Any? {
        val ctx = RequestContext.getCurrentContext()
        val req = ctx.request
        val path = req.requestURI
        val output =
            """
Request: $path
Method: ${req.method}
Request Body: ${printRequest(ctx)}
"""
        println(output)
        return null
    }

    override fun shouldFilter(): Boolean = true

    override fun filterType(): String = "pre"

    override fun filterOrder(): Int = 0

    val methods = arrayOf(HttpMethod.POST, HttpMethod.PATCH, HttpMethod.PUT, HttpMethod.DELETE).map { it.name }

    private fun printRequest(ctx: RequestContext): String {
        if (methods.contains(ctx.request.method)) {
            return try {
                val bufferedReader = ctx.request.reader
                bufferedReader.use(BufferedReader::readText)
            } catch (e: Exception) {
                "Empty"
            }
        }
        return ""
    }

}