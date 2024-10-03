package com.baesuii.fluxnews.presentation.settings

import android.content.res.Configuration.UI_MODE_NIGHT_YES
import androidx.compose.foundation.clickable
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.hilt.navigation.compose.hiltViewModel
import com.baesuii.fluxnews.R
import com.baesuii.fluxnews.domain.model.AppVersion
import com.baesuii.fluxnews.domain.model.DarkMode
import com.baesuii.fluxnews.domain.model.Timezone
import com.baesuii.fluxnews.presentation.common.GenericToggle
import com.baesuii.fluxnews.presentation.common.OptionDescriptionText
import com.baesuii.fluxnews.presentation.common.OptionTitleText
import com.baesuii.fluxnews.presentation.common.ScreenTitleTextLarge
import com.baesuii.fluxnews.presentation.home.HomeViewModel
import com.baesuii.fluxnews.presentation.settings.components.TimezoneDialog
import com.baesuii.fluxnews.presentation.theme.Dimensions.iconExtraLarge
import com.baesuii.fluxnews.presentation.theme.Dimensions.paddingMedium
import com.baesuii.fluxnews.presentation.theme.Dimensions.paddingSemiMedium
import com.baesuii.fluxnews.presentation.theme.Dimensions.paddingSmall
import com.baesuii.fluxnews.presentation.theme.FluxNewsTheme

@Composable
fun SettingsScreen(
    homeViewModel: HomeViewModel,  // To update city
    showTimezoneDialog: Boolean,
    onShowTimezoneDialog: () -> Unit,
    onDismissTimezoneDialog: () -> Unit,
    modifier: Modifier = Modifier,
    viewModel: SettingsViewModel = hiltViewModel()
) {
    val theme by viewModel.theme.collectAsState()
    val nickname by viewModel.nickname.collectAsState()
    val selectedEmoji by viewModel.selectedEmoji.collectAsState(initial = "\uD83D\uDE36")
    var editableNickname by rememberSaveable { mutableStateOf(nickname) }
    val selectedTimezone by viewModel.selectedTimezone.collectAsState()

    val context = LocalContext.current

    SettingsScreenContent(
        modifier = modifier,
        darkMode = context.darkMode(isDarkModeEnabled = theme),
        timezone = context.timeZone(selectedTimezone = selectedTimezone),
        appVersion  = context.appVersion(appVersion = context.getAppVersionName()),
        nickname = nickname,
        onNicknameChange = { newNickname ->
            editableNickname = newNickname
            viewModel.updateNickname(newNickname)
        },
        selectedEmoji = selectedEmoji,
        onEmojiChange = { newEmoji -> viewModel.updateSelectedEmoji(newEmoji) },
        onToggleDarkMode = { isChecked -> viewModel.updateDarkMode(isChecked) },
        selectedTimezone = selectedTimezone,
        onTimezoneChange = { timezone ->
            viewModel.updateTimezone(timezone)
            homeViewModel.updateCity(timezone)
        },
        onShowTimezoneDialog = onShowTimezoneDialog,
        onDismissTimezoneDialog = onDismissTimezoneDialog,
        showTimezoneDialog = showTimezoneDialog
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SettingsScreenContent(
    darkMode: DarkMode,
    timezone: Timezone,
    appVersion: AppVersion,
    nickname: String,
    onNicknameChange: (String) -> Unit,
    selectedEmoji: String,
    onEmojiChange: (String) -> Unit,
    onToggleDarkMode: (Boolean) -> Unit,
    selectedTimezone: String,
    onTimezoneChange: (String) -> Unit,
    onShowTimezoneDialog: () -> Unit,
    onDismissTimezoneDialog: () -> Unit,
    showTimezoneDialog: Boolean,
    modifier: Modifier = Modifier
) {
    Scaffold(
        modifier = modifier
            .fillMaxSize()
            .statusBarsPadding()
            .testTag("SettingsScreenContent"),
        topBar = {
            TopAppBar(
                modifier = Modifier
                    .padding(paddingSemiMedium),
                title = {
                    ScreenTitleTextLarge(textResId = R.string.settings)
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = Color.Transparent
                )
            )
        }
    ) {
        LazyColumn(
            modifier = Modifier
                .padding(it)
                .testTag("SettingsList")
        ) {
            item {

                UserProfileSection(
                    nickname = nickname,
                    onNicknameChange = onNicknameChange,
                    selectedEmoji = selectedEmoji,
                    onEmojiChange = onEmojiChange
                )

                SettingsOptionItem(
                    darkMode = darkMode,
                    timezone = timezone,
                    appVersion = appVersion,
                    onToggleDarkMode = onToggleDarkMode,
                    selectedTimezone = selectedTimezone,
                    onShowTimezoneDialog = onShowTimezoneDialog,
                )
            }
        }
    }

    if (showTimezoneDialog) {
        TimezoneDialog(
            onDismiss = onDismissTimezoneDialog,
            onSelectTimezone = onTimezoneChange
        )
    }
}

@Composable
fun SettingsOptionItem(
    darkMode: DarkMode,
    timezone: Timezone,
    appVersion: AppVersion,
    onToggleDarkMode: (Boolean) -> Unit,
    selectedTimezone: String,
    onShowTimezoneDialog: () -> Unit,
    modifier: Modifier = Modifier
) {

    ListItem(
        modifier = modifier
            .fillMaxWidth()
            .padding(vertical = paddingSmall, horizontal = paddingMedium),
        leadingContent = {
            Icon(
                painter = painterResource(id = darkMode.icon),
                contentDescription = darkMode.title,
                modifier = Modifier.size(iconExtraLarge)
            )
        },
        headlineContent = {
            OptionTitleText(text = darkMode.title)
        },
        trailingContent = {
            GenericToggle(
                modifier = Modifier.testTag("DarkModeToggle"),
                isChecked = darkMode.isDarkModeEnabled,
                onCheckedChange = { isChecked ->
                    onToggleDarkMode(isChecked)
                }

            )
        },
        colors = ListItemDefaults.colors(
            containerColor = Color.Transparent
        )
    )

    // Timezone
    ListItem(
        modifier = modifier
            .fillMaxWidth()
            .clickable { onShowTimezoneDialog() }
            .padding(vertical = paddingSmall, horizontal = paddingMedium),
        leadingContent = {
            Icon(
                painter = painterResource(id = timezone.icon),
                contentDescription = timezone.title,
                modifier = Modifier.size(iconExtraLarge)
            )
        },
        headlineContent = {
            OptionTitleText(text = timezone.title)
        },
        supportingContent = {
            OptionDescriptionText(
                text = selectedTimezone,
                color = MaterialTheme.colorScheme.primary
            )
        },
        colors = ListItemDefaults.colors(
            containerColor = Color.Transparent
        )
    )

    // App version
    ListItem(
        modifier = modifier
            .fillMaxWidth()
            .padding(vertical = paddingSmall, horizontal = paddingMedium),
        leadingContent = {
            Icon(
                painter = painterResource(id = appVersion.icon),
                contentDescription = appVersion.title,
                modifier = Modifier.size(iconExtraLarge)
            )
        },
        headlineContent = {
            OptionTitleText(text = appVersion.title)
        },
        supportingContent = {
            OptionDescriptionText(
                text = appVersion.description,
                color = MaterialTheme.colorScheme.secondary
            )
        },
        colors = ListItemDefaults.colors(
            containerColor = Color.Transparent
        )
    )
}

@Preview(showBackground = true)
@Preview(uiMode = UI_MODE_NIGHT_YES)
@Composable
private fun SettingsScreenPreview() {
    FluxNewsTheme {
        val context = LocalContext.current
        SettingsScreenContent(
            darkMode = context.darkMode(isDarkModeEnabled = isSystemInDarkTheme()),
            timezone = context.timeZone(selectedTimezone = "America/New_York"),
            appVersion  = context.appVersion(appVersion = "1.0.0"),
            nickname = "John Doe",
            onNicknameChange = {},
            selectedEmoji = "\uD83D\uDE36",
            onEmojiChange = {},
            onToggleDarkMode = {},
            selectedTimezone = "America/New_York",
            onTimezoneChange = {},
            onShowTimezoneDialog = {},
            onDismissTimezoneDialog = {},
            showTimezoneDialog = false
        )
    }
}