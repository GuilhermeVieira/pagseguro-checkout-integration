package com.example.myshop.model

import com.fasterxml.jackson.xml.annotate.JacksonXmlProperty
import javax.persistence.Embeddable

@Embeddable
data class CheckoutResponse(
        @JacksonXmlProperty(localName = "code")
        val code: String?,
        @JacksonXmlProperty(localName = "date")
        val date: String?,
        @JacksonXmlProperty(localName = "bad_request")
        val badRequest: CheckoutResponseError? = null
)

@Embeddable
data class CheckoutResponseError (
        @JacksonXmlProperty(localName = "error")
        val errors: CheckoutResponseErrors?
)

@Embeddable
data class CheckoutResponseErrors (
        @JacksonXmlProperty(localName = "code")
        val code_error: String?,
        @JacksonXmlProperty(localName = "message")
        val message: String?
)