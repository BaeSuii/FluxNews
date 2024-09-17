package com.baesuii.fluxnews.presentation.settings

import android.annotation.SuppressLint
import android.content.res.Configuration.UI_MODE_NIGHT_YES
import androidx.activity.compose.BackHandler
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.animateDpAsState
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.zIndex
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.constraintlayout.compose.ConstraintSet
import androidx.constraintlayout.compose.layoutId
import com.baesuii.fluxnews.R
import com.baesuii.fluxnews.presentation.theme.Dimensions.iconExtraLarge
import com.baesuii.fluxnews.presentation.theme.Dimensions.nicknameInputHeight
import com.baesuii.fluxnews.presentation.theme.Dimensions.paddingExtraSmall
import com.baesuii.fluxnews.presentation.theme.Dimensions.paddingMedium
import com.baesuii.fluxnews.presentation.theme.Dimensions.paddingNormal
import com.baesuii.fluxnews.presentation.theme.Dimensions.paddingSmall
import com.baesuii.fluxnews.presentation.theme.FluxNewsTheme
import com.baesuii.fluxnews.util.Constants

@SuppressLint("UnusedBoxWithConstraintsScope")
@Composable
fun UserProfileSection(
    nickname: String,
    onNicknameChange: (String) -> Unit,
    selectedEmoji: String,
    onEmojiChange: (String) -> Unit
) {

    var isEditing by remember { mutableStateOf(false) }
    var editableNickname by remember { mutableStateOf(nickname) }

    var emojiIndex by remember { mutableIntStateOf(Constants.emojis.indexOf(selectedEmoji)) }

    val focusManager = LocalFocusManager.current
    val keyboardController = LocalSoftwareKeyboardController.current

    val focusRequester = remember { FocusRequester() }
    LaunchedEffect(isEditing) {
        if (isEditing) { focusRequester.requestFocus() }
    }

    val animatedTextColor by animateFloatAsState(targetValue = if (isEditing) 0.8f else 0.4f, label = "")

    val charWidthDp = 11.dp
    val enterNicknameText = stringResource(id = R.string.enter_nickname)
    val nicknameWidth = charWidthDp * stringResource(id = R.string.nickname).length
    val enterNicknameWidth = charWidthDp * enterNicknameText.length

    val textWidth = when {
        editableNickname.isEmpty() && !isEditing -> nicknameWidth  // Use "Nickname" width if input is empty and editing is stopped
        !isEditing && editableNickname.isNotEmpty() -> charWidthDp * editableNickname.length  // If editing done, use input width
        else -> charWidthDp * editableNickname.length
    }
    val animatedWidth by animateDpAsState(
        targetValue = if (isEditing) enterNicknameWidth else textWidth,
        label = ""
    )

    BackHandler(enabled = isEditing) {
        isEditing = false
        editableNickname = "" // Reset
        keyboardController?.hide()
    }

    val constraints = ConstraintSet {
        val emoji = createRefFor("emoji")
        val box = createRefFor("box")

        // Positioning for the emoji
        constrain(emoji) {
            top.linkTo(parent.top)
            start.linkTo(parent.start)
            end.linkTo(parent.end)
        }

        // Positioning for the background box
        constrain(box) {
            top.linkTo(emoji.bottom)
            start.linkTo(parent.start)
            end.linkTo(parent.end)
        }
    }

    ConstraintLayout(
        constraints,
        modifier = Modifier
            .fillMaxWidth()
            .padding(paddingMedium)
    ) {

        Box(
            modifier = Modifier
                .layoutId("box")
                .fillMaxWidth()
                .height(nicknameInputHeight)
                .padding(paddingSmall)
                .background(
                    color = MaterialTheme.colorScheme.onSurface,
                    shape = RoundedCornerShape(paddingNormal)
                ),
            contentAlignment = Alignment.TopCenter
        ) {
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(nicknameInputHeight)
                    .padding(top = paddingSmall)
                    .padding(horizontal = paddingSmall)
            ) {


                // Edit button at the top right
                IconButton(
                    onClick = {
                        if (editableNickname.isEmpty()) {
                            editableNickname = ""
                        } else {
                            onNicknameChange(editableNickname)
                        }
                        isEditing = !isEditing
                    },
                    modifier = Modifier
                        .size(iconExtraLarge)
                        .align(Alignment.End)
                ) {
                    Icon(
                        painter = painterResource(id =
                            if (isEditing) R.drawable.ic_close
                            else R.drawable.ic_nickname_edit
                        ),
                        tint = MaterialTheme.colorScheme.secondary,
                        contentDescription = null
                    )
                }

                Spacer(modifier = Modifier.height(paddingExtraSmall))

                // Nickname text
                if (isEditing) {
                    BasicTextField(
                        value = editableNickname,
                        onValueChange = { newValue ->
                            if (newValue.all { it.isLetter() || it.isWhitespace() }) {
                                editableNickname = newValue
                            }
                        },
                        singleLine = true,
                        textStyle = MaterialTheme.typography.bodyMedium.copy(
                            color = MaterialTheme.colorScheme.tertiary
                        ),
                        decorationBox = { innerTextField ->
                            if (editableNickname.isEmpty()) {
                                Row {
                                    enterNicknameText.forEachIndexed { index, char ->
                                        AnimatedVisibility(
                                            visible = isEditing || editableNickname.isEmpty(),  // Visible only when editing or empty
                                            enter = fadeIn(animationSpec = tween(durationMillis = 300, delayMillis = index * 50)),  // Staggered fade in
                                            exit = fadeOut(animationSpec = tween(durationMillis = 300, delayMillis = index * 50))   // Staggered fade out
                                        ) {
                                            Text(
                                                text = char.toString(),
                                                color = MaterialTheme.colorScheme.tertiary.copy(alpha = animatedTextColor),
                                                style = MaterialTheme.typography.bodyMedium
                                            )
                                        }
                                    }
                                }
                            }
                            innerTextField()  // Draw the text field
                        },
                        keyboardOptions = KeyboardOptions.Default.copy(
                            imeAction = ImeAction.Done
                        ),
                        keyboardActions = KeyboardActions(
                            onDone = {
                                onNicknameChange(editableNickname)
                                isEditing = false
                                focusManager.clearFocus()
                            }
                        ),
                        modifier = Modifier
                            .width(animatedWidth)
                            .focusRequester(focusRequester)
                    )
                } else {
                    Text(
                        text = nickname.ifEmpty { stringResource(id = R.string.nickname) },
                        style = MaterialTheme.typography.bodyMedium.copy(
                            color = MaterialTheme.colorScheme.tertiary.copy(alpha = animatedTextColor)
                        ),
                        modifier = Modifier
                            .width(animatedWidth)
                    )
                }
            }

            Box(
                modifier = Modifier
                    .layoutId("emoji")
                    .align(Alignment.TopCenter)
                    .offset(y = (-46).dp)
                    .zIndex(1f)
            ) {
                Button(
                    onClick = {
                        emojiIndex = (emojiIndex + 1) % Constants.emojis.size  // Cycle through emojis
                        onEmojiChange(Constants.emojis[emojiIndex])  // Update emoji in ViewModel
                    },
                    modifier = Modifier.align(Alignment.TopCenter),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = Color.Transparent,
                        contentColor = Color.Unspecified,
                        disabledContainerColor = Color.Transparent,
                        disabledContentColor = Color.Unspecified
                    ),
                ) {
                    Text(
                        text = selectedEmoji,
                        fontSize = 48.sp
                    )
                }
            }
        }
    }
}

@Preview(showBackground = true)
@Preview(uiMode = UI_MODE_NIGHT_YES)
@Composable
private fun NicknameInputPreview() {
    FluxNewsTheme {
        UserProfileSection(
            nickname = "",
            onNicknameChange = {},
            selectedEmoji = "\uD83D\uDE36",
            onEmojiChange = {}
        )
    }
}