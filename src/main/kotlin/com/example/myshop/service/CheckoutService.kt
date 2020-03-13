package com.example.myshop.service

import com.example.myshop.client.CheckoutClient
import com.example.myshop.model.Checkout
import com.example.myshop.repository.CheckoutRepository
import org.slf4j.LoggerFactory
import org.springframework.stereotype.Service

@Service
class CheckoutService(
        private val checkoutClient: CheckoutClient,
        private val checkoutRepository: CheckoutRepository
) {
    private val logger = LoggerFactory.getLogger(CheckoutService::class.java)

    fun processNewCheckout(checkout: Checkout): String? {
        val checkoutResponse = checkoutClient.getCheckoutCode(checkout)

        return when {
            checkoutResponse.badRequest != null -> {
                val error: String = "Bad request! Code: " + checkoutResponse.badRequest.errors?.code_error + " Error: " + checkoutResponse.badRequest.errors?.message
                logger.error(error)

                error
            }
            checkoutResponse.code != null -> {
                logger.info("Checkout accepted")
                checkout.checkoutResponse = checkoutResponse
                checkoutRepository.save(checkout)

                "https://pagseguro.uol.com.br/v2/checkout/payment.html?code=" + checkout.checkoutResponse?.code
            }
            else -> {
                "Something went wrong"
            }
        }
    }

}