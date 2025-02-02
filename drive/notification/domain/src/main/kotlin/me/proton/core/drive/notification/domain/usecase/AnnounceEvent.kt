/*
 * Copyright (c) 2022-2023 Proton AG.
 * This file is part of Proton Core.
 *
 * Proton Core is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * Proton Core is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with Proton Core.  If not, see <https://www.gnu.org/licenses/>.
 */
package me.proton.core.drive.notification.domain.usecase

import me.proton.core.domain.entity.UserId
import me.proton.core.drive.notification.domain.entity.NotificationEvent
import me.proton.core.drive.notification.domain.handler.NotificationEventHandler
import javax.inject.Inject

class AnnounceEvent @Inject constructor(
    private val notificationEventHandler: NotificationEventHandler,
) {
    suspend operator fun invoke(
        userId: UserId,
        notificationEvent: NotificationEvent,
    ) = with (notificationEventHandler) {
        val notificationId = createUserNotificationId(userId, notificationEvent)
        onNotificationEvent(
            notificationId = notificationId,
            event = notificationEvent,
        )
    }

    suspend operator fun invoke(
        notificationEvent: NotificationEvent,
    ) = with (notificationEventHandler) {
        val notificationId = createAppNotificationId(notificationEvent)
        onNotificationEvent(
            notificationId = notificationId,
            event = notificationEvent,
        )
    }
}
