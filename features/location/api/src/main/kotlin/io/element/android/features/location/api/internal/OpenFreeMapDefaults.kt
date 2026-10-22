/*
 * Copyright (c) 2026 Element Creations Ltd.
 *
 * SPDX-License-Identifier: AGPL-3.0-only OR LicenseRef-Element-Commercial.
 * Please see LICENSE files in the repository root for full details.
 */

package io.element.android.features.location.api.internal

/**
 * Free OpenFreeMap vector styles, used when no MapTiler configuration is available.
 * They require no API key, but the OpenStreetMap attribution must stay visible on the map.
 *
 * https://openfreemap.org
 */
internal object OpenFreeMapDefaults {
    const val LIGHT_STYLE_URL = "https://tiles.openfreemap.org/styles/liberty"
    const val DARK_STYLE_URL = "https://tiles.openfreemap.org/styles/dark"

    fun styleUrl(darkMode: Boolean): String = if (darkMode) DARK_STYLE_URL else LIGHT_STYLE_URL
}
