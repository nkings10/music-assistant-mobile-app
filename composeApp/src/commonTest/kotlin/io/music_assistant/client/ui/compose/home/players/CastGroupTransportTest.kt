package io.music_assistant.client.ui.compose.home.players

import io.music_assistant.client.data.model.client.PlaybackSource
import io.music_assistant.client.data.model.client.PlayerDataFixtures.playerData
import io.music_assistant.client.data.model.client.PlayerType
import io.music_assistant.client.ui.compose.common.DataState
import kotlin.test.Test
import kotlin.test.assertFalse
import kotlin.test.assertTrue

class CastGroupTransportTest {
    @Test
    fun `pause and next follow the playing source`() {
        val group = castGroup(canPlayPause = true, canNextPrevious = true)

        val transport = group.transportState(chapterProgressEnabled = false)

        assertTrue(transport.playerEnabled)
        assertTrue(transport.buttonsEnabled)
        assertTrue(transport.skipForwardEnabled)
        assertTrue(group.mainTransport().enabled)
    }

    @Test
    fun `a source that cannot pause or skip keeps the buttons off`() {
        val transport = castGroup(canPlayPause = false, canNextPrevious = false)
            .transportState(chapterProgressEnabled = false)

        assertFalse(transport.buttonsEnabled)
        assertFalse(transport.skipForwardEnabled)
    }

    private fun castGroup(canPlayPause: Boolean, canNextPrevious: Boolean) =
        playerData(queueId = "cast-group", playerType = PlayerType.GROUP).let { data ->
            val queue = (data.queue as DataState.Data).data
            data.copy(
                player = data.player.copy(
                    id = "cast-group",
                    sources = listOf(
                        PlaybackSource(
                            id = "cast-group",
                            passive = false,
                            canPlayPause = canPlayPause,
                            canNextPrevious = canNextPrevious,
                        ),
                    ),
                ),
                queue = DataState.Data(queue.copy(info = queue.info.copy(itemCount = 4))),
            )
        }
}
