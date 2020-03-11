package com.example.myshop.repository

import com.example.myshop.model.Checkout
import org.springframework.data.jpa.repository.JpaRepository

interface CheckoutRepository: JpaRepository<Checkout, String>