/*
 * Copyright (c) 2023 Proton AG.
 * This file is part of Proton Drive.
 *
 * Proton Drive is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * Proton Drive is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with Proton Drive.  If not, see <https://www.gnu.org/licenses/>.
 */

package me.proton.android.drive.ui.test.flow.upload

import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.rule.GrantPermissionRule
import me.proton.android.drive.ui.robot.LauncherRobot
import me.proton.android.drive.ui.rules.ExternalFilesRule
import me.proton.android.drive.ui.rules.UserLoginRule
import me.proton.android.drive.ui.rules.WelcomeScreenRule
import me.proton.android.drive.ui.test.EmptyBaseTest
import me.proton.android.drive.ui.toolkits.getRandomString
import me.proton.core.test.android.instrumented.utils.StringUtils
import me.proton.core.test.quark.data.User
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import me.proton.core.drive.i18n.R as I18N

@RunWith(AndroidJUnit4::class)
class UploadViaThirdPartyAppFlowTest : EmptyBaseTest() {
    private val user
        get() = User(
            name = "proton_drive_${getRandomString(20)}"
        )

    @get:Rule
    val permissionRule: GrantPermissionRule = GrantPermissionRule.grant(
        android.Manifest.permission.READ_EXTERNAL_STORAGE,
        android.Manifest.permission.WRITE_EXTERNAL_STORAGE,
    )

    @get:Rule
    val externalFilesRule = ExternalFilesRule()

    @get:Rule
    val welcomeScreenRule = WelcomeScreenRule(false)

    @get:Rule
    val userLoginRule = UserLoginRule(testUser = user)

    @Test
    fun uploadEmptyFileViaThirdPartyApp() {
        val file = externalFilesRule.createFile("empty.txt")

        LauncherRobot.uploadTo(file)
            .verify {
            robotDisplayed()
            assertEmptyFolder() // TODO: Enable upload button when folder is loaded
        }
            .clickUpload()
            .verify {
                assertFilesBeingUploaded(
                    1,
                    StringUtils.stringFromResource(I18N.string.title_my_files)
                )
            }
    }
}
