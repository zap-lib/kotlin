package com.github.zap_lib.resources

import com.github.zap_lib.models.DeZapable
import com.github.zap_lib.models.ZapPayload
import com.github.zap_lib.models.Zapable
import com.github.zap_lib.models.appendIfNotNull

class ZapUiEvent(
    val uiId: String,
    val event: Event,
    val value: String? = null,
) : Zapable {
    override val resource: ZapResource = ZapResource.UI_EVENT

    override fun toPayload(): ZapPayload = "$uiId,${event.key}".appendIfNotNull(value)

    operator fun component1(): String = uiId
    operator fun component2(): Event = event
    operator fun component3(): String? = value

    companion object : DeZapable {
        override fun fromPayload(payload: ZapPayload): ZapUiEvent {
            val (uiId, event, value) = payload.split(",")
            return ZapUiEvent(uiId, Event.from(event), value)
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