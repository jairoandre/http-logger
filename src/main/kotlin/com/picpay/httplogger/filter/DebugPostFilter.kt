package com.picpay.httplogger.filter

import com.netflix.zuul.ZuulFilter
import com.netflix.zuul.context.RequestContext
import org.springframework.stereotype.Component
import java.io.BufferedReader
import java.util.zip.GZIPInputStream

@Component
class DebugPostFilter : ZuulFilter() {

    override fun run(): Any? {
        val ctx = RequestContext.getCurrentContext()
        if (ctx == null) {
            println("No context")
        } else {
            try {
                if (ctx.responseDataStream != null) {
                    val stream = when (ctx.responseGZipped) {
                        true -> GZIPInputStream(ctx.responseDataStream)
                        false -> ctx.responseDataStream
                    }
                    val response = stream.bufferedReader().use(BufferedReader::readText)
                    println("Response (${ctx.request.requestURI}):\n$response")
                    ctx.responseBody = response
                }
            } catch (e: Exception) {
                println("Error!")
            }
        }
        return null
    }

    override fun shouldFilter(): Boolean = true

    override fun filterType(): String = "post"

    override fun filterOrder(): Int = 0

}