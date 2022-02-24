package com.techandteach.customer.model.types

import kotlinx.serialization.KSerializer
import kotlinx.serialization.descriptors.PrimitiveKind
import kotlinx.serialization.descriptors.PrimitiveSerialDescriptor
import kotlinx.serialization.descriptors.SerialDescriptor
import kotlinx.serialization.encoding.Decoder
import kotlinx.serialization.encoding.Encoder

object CreditCardNumberSerializer : KSerializer<CreditCardNumber> {
    override val descriptor = PrimitiveSerialDescriptor("CreditCardNumber", PrimitiveKind.STRING)

    override fun deserialize(decoder: Decoder): CreditCardNumber {
        return CreditCardNumber.fromString(decoder.decodeString())
    }

    override fun serialize(encoder: Encoder, ccNumber: CreditCardNumber) {
        encoder.encodeString(ccNumber.toString())
    }
}