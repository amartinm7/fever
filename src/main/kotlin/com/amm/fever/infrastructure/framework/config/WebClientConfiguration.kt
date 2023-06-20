package com.amm.fever.infrastructure.framework.config

import io.netty.channel.ChannelOption
import io.netty.handler.timeout.ReadTimeoutHandler
import io.netty.handler.timeout.WriteTimeoutHandler
import org.springframework.beans.factory.annotation.Value
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.context.annotation.Scope
import org.springframework.http.HttpHeaders.ACCEPT
import org.springframework.http.HttpHeaders.AUTHORIZATION
import org.springframework.http.HttpHeaders.CONTENT_TYPE
import org.springframework.http.MediaType.APPLICATION_JSON_VALUE
import org.springframework.http.client.reactive.ReactorClientHttpConnector
import java.util.Base64
import org.springframework.web.reactive.function.client.WebClient
import reactor.netty.http.client.HttpClient
import reactor.netty.tcp.TcpClient

@Configuration
class WebClientConfiguration {

  @Value("\${app.ms-provider-event.url}")
  private lateinit var providerUrl: String

  @Value("\${app.ms-provider-event.user}")
  private lateinit var userCredentials: String

  @Value("\${app.ms-provider-event.password}")
  private lateinit var passCredentials: String

  @Bean
  fun tcpClient(): TcpClient = TcpClient.create()
    .option(ChannelOption.CONNECT_TIMEOUT_MILLIS, TIME_OUT_MILLIS)
    .doOnConnected { connection ->
      connection.addHandlerLast(ReadTimeoutHandler(TIME_OUT_SECONDS))
        .addHandlerLast(WriteTimeoutHandler(TIME_OUT_SECONDS))
    }

  @Bean
  @Scope("prototype")
  fun eventExtProviderWebClient(tcpClient: TcpClient): WebClient =
    WebClient.builder()
      .baseUrl(providerUrl)
      .defaultHeader(CONTENT_TYPE, APPLICATION_JSON_VALUE)
      .defaultHeader(ACCEPT, APPLICATION_JSON_VALUE)
      .defaultHeader(AUTHORIZATION, basicAuthHeader())
      .clientConnector(ReactorClientHttpConnector(HttpClient.from(tcpClient)))
      .build()

  private fun encodeToString() = Base64.getEncoder().encodeToString("$userCredentials:$passCredentials".toByteArray())
  private fun basicAuthHeader() = "basic " + encodeToString()

  companion object {
    private const val TIME_OUT_MILLIS = 500
    private const val TIME_OUT_SECONDS = 1000
  }
}
