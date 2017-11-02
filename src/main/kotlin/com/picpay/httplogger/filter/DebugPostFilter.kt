package com.picpay.httplogger.filter

import com.netflix.zuul.ZuulFilter
import com.netflix.zuul.context.RequestContext
import com.picpay.httplogger.service.LoggerService
import com.picpay.httplogger.utils.Colors
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Component
import java.io.BufferedReader
import java.util.zip.GZIPInputStream

@Component
class DebugPostFilter : ZuulFilter() {

    @Autowired
    lateinit var logger : LoggerService

    override fun run(): Any? {
        val ctx = RequestContext.getCurrentContext()
        val sessionId = ctx.request.session.id
        val header = logger.map[sessionId]
        logger.map.remove(sessionId)
        println(header)
        if (ctx == null) {
            println("${Colors.white("RESPONSE")}: No response")
        } else {
            try {
                if (ctx.responseDataStream != null) {
                    val stream = when (ctx.responseGZipped) {
                        true -> GZIPInputStream(ctx.responseDataStream)
                        false -> ctx.responseDataStream
                    }
                    val response = stream.bufferedReader().use(BufferedReader::readText)
                    println("${Colors.white("RESPONSE")}:\n$response")
                    ctx.responseBody = response
                }
            } catch (e: Exception) {
                println("${Colors.white("RESPONSE")}: Error")
            }
        }
        val begin = logger.times[sessionId]
        logger.times.remove(sessionId)
        val delta = System.currentTimeMillis() - begin!!
        println(Colors.cyan("Total time: $delta ms"))
        return null
    }

    override fun shouldFilter(): Boolean = true

    override fun filterType(): String = "post"

    override fun filterOrder(): Int = 0

}