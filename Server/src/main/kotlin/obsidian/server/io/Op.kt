/*
 * Obsidian
 * Copyright (C) 2021 Mixtape-Bot
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */

package obsidian.server.io

import kotlinx.serialization.KSerializer
import kotlinx.serialization.Serializable
import kotlinx.serialization.descriptors.PrimitiveKind
import kotlinx.serialization.descriptors.PrimitiveSerialDescriptor
import kotlinx.serialization.descriptors.SerialDescriptor
import kotlinx.serialization.encoding.Decoder
import kotlinx.serialization.encoding.Encoder

@Serializable(with = Op.Serializer::class)
enum class Op(val code: Int) {
  Unknown(Int.MIN_VALUE),
  SubmitVoiceUpdate(0),

  // obsidian related.
  Stats(1),

  // player information.
  PlayerEvent(2),
  PlayerUpdate(3),

  // player control.
  PlayTrack(4),
  StopTrack(5),
  Pause(6),
  Filters(7),
  Seek(8);

  companion object Serializer : KSerializer<Op> {
    /**
     * Finds the Op for the provided [code]
     *
     * @param code The operation code.
     */
    operator fun get(code: Int): Op? =
      values().firstOrNull { it.code == code }

    override val descriptor: SerialDescriptor
      get() = PrimitiveSerialDescriptor("op", PrimitiveKind.INT)

    override fun deserialize(decoder: Decoder): Op =
      this[decoder.decodeInt()] ?: Unknown

    override fun serialize(encoder: Encoder, value: Op) =
      encoder.encodeInt(value.code)
  }

}