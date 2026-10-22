/*
 * Copyright (c) 2026 Element Creations Ltd.
 *
 * SPDX-License-Identifier: AGPL-3.0-only OR LicenseRef-Element-Commercial.
 * Please see LICENSE files in the repository root for full details.
 */

package io.element.android.features.location.api.internal

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.unit.dp
import io.element.android.features.location.api.Location
import io.element.android.libraries.designsystem.components.LocationPin
import io.element.android.libraries.designsystem.components.PinVariant
import org.maplibre.compose.camera.CameraPosition
import org.maplibre.compose.interaction.MapInteractions
import org.maplibre.compose.map.AndroidRenderMode
import org.maplibre.compose.map.MapUiOptions
import org.maplibre.compose.map.MaplibreMap
import org.maplibre.compose.map.rememberMapState
import org.maplibre.compose.overlay.ExpandingAttributionButton
import org.maplibre.compose.style.BaseStyle
import org.maplibre.spatialk.geojson.Position

/**
 * A small, non-interactive MapLibre map centred on [location], used in place of a static map image
 * when no static maps API is available (i.e. MapTiler is not configured).
 *
 * The map ignores gestures and lets taps go through to the parent, so the timeline item stays clickable.
 */
@Composable
internal fun LiteMapView(
    location: Location,
    zoom: Double,
    pinVariant: PinVariant,
    darkMode: Boolean,
    modifier: Modifier = Modifier,
) {
    val mapState = rememberMapState(
        baseStyle = BaseStyle.Uri(OpenFreeMapDefaults.styleUrl(darkMode)),
        initialCameraPosition = location.toCameraPosition(zoom),
    )
    LaunchedEffect(location, zoom) {
        mapState.setCameraPosition(location.toCameraPosition(zoom))
    }
    Box(modifier = modifier) {
        MaplibreMap(
            state = mapState,
            interactions = MapInteractions.None,
            uiOptions = MapUiOptions(MapUiOptions.None) {
                // A TextureView behaves correctly inside scrolling lists, unlike a SurfaceView.
                renderMode = AndroidRenderMode.Texture
            },
            overlay = {
                // Swallows hit testing so the native map view never receives the touches,
                // without consuming them: the parent clickable still handles taps and long presses.
                Box(modifier = Modifier.fillMaxSize().pointerInput(Unit) {})
                // OpenStreetMap data requires a visible attribution.
                ExpandingAttributionButton(
                    modifier = Modifier
                        .align(Alignment.TopEnd)
                        .padding(4.dp),
                )
            },
            modifier = Modifier.fillMaxSize(),
        )
        LocationPin(variant = pinVariant, modifier = Modifier.centerBottomEdge(this))
    }
}

private fun Location.toCameraPosition(zoom: Double) = CameraPosition(
    center = Position(latitude = lat, longitude = lon),
    zoom = zoom,
)
