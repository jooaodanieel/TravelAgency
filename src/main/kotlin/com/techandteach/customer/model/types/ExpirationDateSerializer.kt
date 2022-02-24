package com.techandteach.customer.model.types

import kotlinx.serialization.KSerializer
import kotlinx.serialization.descriptors.PrimitiveKind
import kotlinx.serialization.descriptors.PrimitiveSerialDescriptor
import kotlinx.serialization.encoding.Decoder
import kotlinx.serialization.encoding.Encoder

object ExpirationDateSerializer : KSerializer<ExpirationDate> {
    override val descriptor = PrimitiveSerialDescriptor("ExpirationDate", PrimitiveKind.STRING)

    override fun deserialize(decoder: Decoder): ExpirationDate {
        return ExpirationDate.fromString(decoder.decodeString())
    }

    override fun serialize(encoder: Encoder, ed: ExpirationDate) {
        encoder.encodeString(ed.toString())
    }
}