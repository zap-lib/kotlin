package com.github.zap_lib.resources

import com.github.zap_lib.appendIfNotNull
import com.github.zap_lib.models.*

/**
 * Represent data related to event raised by the user interface.
 *
 * @property uiId An identifier for the UI.
 * @property event A type of event occurring for the UI.
 * @property value A value changed due to the event.
 */
class ZapUiEvent(
    val uiId: String,
    val event: Event,
    val value: String? = null,
) : Zapable {
    override val resource: ZapResource = ZapResource.UI_EVENT

    override fun toPayload(): ZappPayload =
        ZappPayload.wrap("$uiId,${event.key}".appendIfNotNull(value).toByteArray())

    operator fun component1(): String = uiId
    operator fun component2(): Event = event
    operator fun component3(): String? = value

    companion object : DeZapable {
        override fun from(payload: ZappPayload): ZapUiEvent {
            val (uiId, event, value) = payload.readAsString().split(",")
            return ZapUiEvent(uiId, Event.from(event), value)
        }
    }

    enum class Event(val key: String) {
        CLICK("CLICK"),
        CLICK_DOWN("CLICK_DOWN"),
        CLICK_UP("CLICK_UP");

        companion object {
            fun from(key: String): Event =
                when (key) {
                    CLICK.key -> CLICK
                    CLICK_DOWN.key -> CLICK_DOWN
                    CLICK_UP.key -> CLICK_UP
                    else -> throw Exception("Unknown event")
                }
        }
    }
}
