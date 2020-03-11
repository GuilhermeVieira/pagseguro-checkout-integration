package com.example.myshop.api

import com.example.myshop.client.CheckoutClient
import com.example.myshop.model.Checkout
import com.example.myshop.service.CheckoutService
import org.slf4j.LoggerFactory
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RequestMapping("api/checkouts")
@RestController
class CheckoutController(private val checkoutService: CheckoutService,
                         private val checkoutClient: CheckoutClient) {
    private val logger = LoggerFactory.getLogger(CheckoutController::class.java)

    @PostMapping
    fun beginTransaction(@RequestBody checkout: Checkout): String? {
        return checkoutService.processNewCheckout(checkout)
    }

    /*
    @GetMapping(path = ["/{id}"])
    fun getCheckoutByID(@PathVariable("id") id: UUID): Checkout? {
        return checkoutDAO.selectCheckoutByID(id)
    }
     */
}