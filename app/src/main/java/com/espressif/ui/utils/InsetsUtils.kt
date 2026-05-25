// Copyright 2026 Espressif Systems (Shanghai) PTE LTD
//
// Licensed under the Apache License, Version 2.0 (the "License");
// you may not use this file except in compliance with the License.
// You may obtain a copy of the License at
//
//     http://www.apache.org/licenses/LICENSE-2.0
//
// Unless required by applicable law or agreed to in writing, software
// distributed under the License is distributed on an "AS IS" BASIS,
// WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
// See the License for the specific language governing permissions and
// limitations under the License.

package com.espressif.ui.utils

import android.view.View
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.core.view.updatePadding

/**
 * Applies system bar + display cutout insets as padding to this view.
 *
 * Needed on Android 15+ (API 35, edge-to-edge enforced) so that toolbars are
 * not drawn behind the status bar and bottom-anchored views are not drawn
 * behind the navigation bar.
 */
fun View.applySystemBarsAsPadding() {
    ViewCompat.setOnApplyWindowInsetsListener(this) { v, insets ->
        val bars = insets.getInsets(
            WindowInsetsCompat.Type.systemBars()
                    or WindowInsetsCompat.Type.displayCutout()
        )
        v.updatePadding(bars.left, bars.top, bars.right, bars.bottom)
        WindowInsetsCompat.CONSUMED
    }
    // Defensive: force a redispatch in case the view was already attached
    // (e.g. on configuration change) when the listener was installed.
    ViewCompat.requestApplyInsets(this)
}

/**
 * Like [applySystemBarsAsPadding] but skips the top inset, for activities
 * that use the framework/Material ActionBar (which already accounts for the
 * status bar height itself).
 */
fun View.applySystemBarsAsPaddingExceptTop() {
    ViewCompat.setOnApplyWindowInsetsListener(this) { v, insets ->
        val bars = insets.getInsets(
            WindowInsetsCompat.Type.systemBars()
                    or WindowInsetsCompat.Type.displayCutout()
        )
        v.updatePadding(bars.left, v.paddingTop, bars.right, bars.bottom)
        WindowInsetsCompat.CONSUMED
    }
    ViewCompat.requestApplyInsets(this)
}
