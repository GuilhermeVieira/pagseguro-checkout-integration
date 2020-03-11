package com.example.myshop.model

import com.fasterxml.jackson.annotation.JsonProperty
import com.fasterxml.jackson.xml.annotate.JacksonXmlElementWrapper
import com.fasterxml.jackson.xml.annotate.JacksonXmlRootElement
import java.util.*
import javax.persistence.*

private const val ID_PREFIX = "CHECKOUT_"

@Entity
@Table(name = "checkouts")
@JacksonXmlRootElement(localName = "checkout")
data class Checkout(
        @Id
        @Column(name = "cod_checkout")
        val id: String = ID_PREFIX + UUID.randomUUID().toString().toUpperCase(),
        var checkoutResponse: CheckoutResponse? = null,
        @OneToOne(mappedBy = "checkout", cascade = [CascadeType.ALL])
        val sender: Sender,
        val reference: String?,
        val shipping: Shipping,
        val timeout: String?,
        val currency: String,
        @JacksonXmlElementWrapper(localName = "items")
        @ElementCollection(fetch = FetchType.EAGER)
        @CollectionTable(
                joinColumns = [JoinColumn(name = "cod_checkout", referencedColumnName = "cod_checkout")]
        )
        val item: MutableList<Item>,
        @JsonProperty("redirect_URL")
        val redirectURL: String?,
        val extraAmount: String?,
        @JsonProperty("max_age")
        val maxAge: String?,
        @JsonProperty("max_users")
        val maxUsers: String?,
        @JsonProperty("enable_recover")
        val enableRecover: String
)

@Entity
data class Sender(
        @Id
        @Column(name = "cod_sender")
        val id: String = UUID.randomUUID().toString().toUpperCase(),
        @OneToOne
        @JoinColumn(name = "cod_checkout")
        val checkout: Checkout? = null,
        val name: String,
        val email: String,
        val phone: Phone,
        @JacksonXmlElementWrapper(localName = "documents")
        @ElementCollection(fetch = FetchType.LAZY)
        @CollectionTable(
                joinColumns = [JoinColumn(name = "cod_sender", referencedColumnName = "cod_sender")]
        )
        val document: MutableList<Document>
)

@Embeddable
data class Phone(
        val area_code: String,
        @Column(name = "phone_number")
        val number: String
)

@Embeddable
data class Document(
        val type: String,
        val value: String
)

@Embeddable
data class Item(
        val id: String,
        val description: String,
        val amount: String,
        val quantity: String,
        val weight: String,
        val shippingCost: String?
)

@Embeddable
data class Shipping(
        val address: Address?,
        val type: Int?,
        val cost: String,
        val addressRequired: String
)

@Embeddable
data class Address(
        val street: String?,
        val district: String?,
        val complement: String?,
        val city: String?,
        val state: String?,
        val country: String?,
        val number: String?,
        val postalCode: String?
)