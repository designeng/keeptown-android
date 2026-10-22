/*
 * Copyright (c) 2025 Element Creations Ltd.
 * Copyright 2025 New Vector Ltd.
 *
 * SPDX-License-Identifier: AGPL-3.0-only OR LicenseRef-Element-Commercial.
 * Please see LICENSE files in the repository root for full details.
 */

package io.element.android.features.location.api

import io.element.android.features.enterprise.api.remoteconfig.MapTilerConfig
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow

/**
 * Provides the map provider configuration and tells whether the location features can be used.
 */
interface LocationService {
    /**
     * The [MapTilerConfig] to use to render the maps, or null if MapTiler is not configured,
     * in which case the maps are rendered with the free OpenFreeMap tiles.
     * The configuration provided by the homeserver takes precedence over the one defined at build time.
     */
    val mapTilerConfig: StateFlow<MapTilerConfig?>

    /**
     * Whether location sharing and map rendering are available; `false` disables the whole feature in the UI.
     * Always available by default, since OpenFreeMap is used when MapTiler is not configured.
     */
    fun isServiceAvailable(): StateFlow<Boolean> = MutableStateFlow(true).asStateFlow()
}
