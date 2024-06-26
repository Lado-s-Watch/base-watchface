package com.dotsdev.basewatchface.feature.wear.component

import androidx.compose.foundation.focusable
import androidx.compose.foundation.gestures.awaitEachGesture
import androidx.compose.foundation.gestures.awaitFirstDown
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.rememberUpdatedState
import androidx.compose.runtime.snapshotFlow
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.input.pointer.pointerInput
import androidx.wear.compose.foundation.ExperimentalWearFoundationApi
import androidx.wear.compose.foundation.HierarchicalFocusCoordinator
import androidx.wear.compose.foundation.rememberActiveFocusRequester
import androidx.wear.compose.material.MaterialTheme
import androidx.wear.compose.material.Picker
import androidx.wear.compose.material.Text
import androidx.wear.compose.material.rememberPickerState

@OptIn(ExperimentalWearFoundationApi::class)
@Composable
@Suppress("LongParameterList")
fun ComponentPicker(
    isSelected: Boolean,
    onSelected: () -> Unit,
    initialComponentValue: Int,
    setComponentValue: (Int) -> Unit,
    contentDescription: String,
    modifier: Modifier = Modifier,
) {
    val pickerState = rememberPickerState(
        initialNumberOfOptions = 256,
        initiallySelectedOption = remember { initialComponentValue },
    )
    val currentSetComponentValue by rememberUpdatedState(setComponentValue)

    val coroutineScope = rememberCoroutineScope()

    LaunchedEffect(pickerState) {
        snapshotFlow { pickerState.selectedOption }
            .collect {
                currentSetComponentValue(it)
            }
    }

    HierarchicalFocusCoordinator(
        requiresFocus = { isSelected },
    ) {
        val focusRequester = rememberActiveFocusRequester()
        Picker(
            state = pickerState,
            contentDescription = contentDescription,
            onSelected = onSelected,
            modifier = modifier
//                .onRotaryInputAccumulated {
//                    coroutineScope.launch {
//                        pickerState.scrollToOption(pickerState.selectedOption + if (it > 0) 1 else -1)
//                    }
//                }
                .focusRequester(focusRequester)
                .focusable(),
        ) { optionIndex ->
            Box(
                contentAlignment = Alignment.Center,
                modifier = Modifier
                    .fillMaxSize()
                    .pointerInput(Unit) {
                        awaitEachGesture {
                            awaitFirstDown()
                            onSelected()
                        }
                    },
            ) {
                Text(
                    text = "%02X".format(optionIndex),
                    style = MaterialTheme.typography.display2,
                    modifier = Modifier.alpha(if (isSelected) 1f else 0.5f),
                )
            }
        }
    }
}