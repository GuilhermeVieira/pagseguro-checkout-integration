package com.example.myshop.client

import com.example.myshop.model.Checkout
import com.example.myshop.model.CheckoutResponse
import com.example.myshop.model.CheckoutResponseError
import com.fasterxml.jackson.xml.XmlMapper
import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Value
import org.springframework.http.*
import org.springframework.stereotype.Component
import org.springframework.web.client.HttpClientErrorException
import org.springframework.web.client.RestTemplate

@Component
class CheckoutClient(private val restTemplate: RestTemplate) {

    @Value("\${api.url}")
    private lateinit var resourceURL: String

    private val logger = LoggerFactory.getLogger(CheckoutClient::class.java)

    fun getCheckoutCode(checkout: Checkout): CheckoutResponse {
        val headers = HttpHeaders()
        headers.contentType = MediaType.APPLICATION_XML

        val xmlMapper = XmlMapper()

        val entity = HttpEntity<String>(xmlMapper.writeValueAsString(checkout), headers)

        val checkoutResponse: CheckoutResponse = try {
            val response: ResponseEntity<String> = restTemplate.exchange(
                    resourceURL,
                    HttpMethod.POST,
                    entity,
                    String::class.java)

            xmlMapper.readValue(
                    response.body, CheckoutResponse::class.java
            )
        } catch (e: HttpClientErrorException) {
            logger.error("{}", e)
            CheckoutResponse(null, null, xmlMapper.readValue(
                    e.responseBodyAsString, CheckoutResponseError::class.java
            ))
        }

        return checkoutResponse
    }
}