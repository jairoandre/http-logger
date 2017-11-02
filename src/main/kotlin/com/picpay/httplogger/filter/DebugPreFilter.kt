package com.picpay.httplogger.filter

import com.netflix.zuul.ZuulFilter
import com.netflix.zuul.context.RequestContext
import com.picpay.httplogger.service.LoggerService
import com.picpay.httplogger.utils.Colors
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.HttpMethod
import org.springframework.stereotype.Component
import java.io.BufferedReader

@Component
class DebugPreFilter : ZuulFilter() {

    @Autowired
    lateinit var logger : LoggerService

    override fun run(): Any? {
        val begin = System.currentTimeMillis()
        val ctx = RequestContext.getCurrentContext()
        val req = ctx.request
        val sessionId = req.session.id
        logger.times.put(sessionId, begin)
        val path = req.requestURI
        val headerNames = req.headerNames
        val headers = headerNames.toList().map { "${Colors.yellow(it)}: ${req.getHeader(it)}"}.reduce { acc, next -> "$acc\n$next" }
        val output = """
${Colors.white("PATH")}: ${Colors.cyan(path)}
${Colors.white("METHOD")}: ${Colors.red(req.method)}
${Colors.white("BODY")}: ${printRequest(ctx)}

${Colors.white("HEADER:")}
$headers
"""
        logger.map.put(sessionId, output)
        return null
    }

    override fun shouldFilter(): Boolean = true

    override fun filterType(): String = "pre"

    override fun filterOrder(): Int = 0

    val methods = arrayOf(HttpMethod.GET, HttpMethod.POST, HttpMethod.PATCH, HttpMethod.PUT, HttpMethod.DELETE).map { it.name }

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