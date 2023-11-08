package com.zap_lib.core.resources

import com.zap_lib.core.models.DeZapable
import com.zap_lib.core.models.ZapPayload
import com.zap_lib.core.models.Zapable

class ZapUiComponent(
    val id: String,
    val event: Event,
    val value: String? = null,
) : Zapable {
    override val resource: ZapResource = ZapResource.UI_COMPONENT

    override fun toPayload(): ZapPayload = "$id,${event.key},$value"

    operator fun component1(): String = id
    operator fun component2(): Event = event
    operator fun component3(): String? = value

    companion object : DeZapable {
        override fun fromPayload(payload: ZapPayload): ZapUiComponent {
            val (id, event, value) = payload.split(",")
            return ZapUiComponent(id, Event.from(event), value)
        }
    }

    enum class Event(val key: String) {
        CLICK_DOWN("CLICK_DOWN"),
        CLICK_PRESS("CLICK_PRESS"),
        CLICK_UP("CLICK_UP");

        companion object {
            fun from(str: String): Event =
                when (str) {
                    CLICK_DOWN.key -> CLICK_DOWN
                    CLICK_PRESS.key -> CLICK_DOWN
                    CLICK_UP.key -> CLICK_UP
                    else -> throw Exception("Unknown event")
                }
        }
    }
}

