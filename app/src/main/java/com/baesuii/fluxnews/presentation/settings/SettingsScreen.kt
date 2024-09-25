package com.baesuii.fluxnews.presentation.settings

import android.content.Context
import android.content.res.Configuration.UI_MODE_NIGHT_YES
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
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.hilt.navigation.compose.hiltViewModel
import com.baesuii.fluxnews.R
import com.baesuii.fluxnews.domain.model.AppVersion
import com.baesuii.fluxnews.domain.model.DarkMode
import com.baesuii.fluxnews.presentation.common.GenericToggle
import com.baesuii.fluxnews.presentation.theme.Dimensions.iconExtraLarge
import com.baesuii.fluxnews.presentation.theme.Dimensions.paddingMedium
import com.baesuii.fluxnews.presentation.theme.Dimensions.paddingSemiMedium
import com.baesuii.fluxnews.presentation.theme.Dimensions.paddingSmall
import com.baesuii.fluxnews.presentation.theme.FluxNewsTheme

@Composable
fun SettingsScreen(
    modifier: Modifier = Modifier,
    viewModel: SettingsViewModel = hiltViewModel()
) {
    val theme by viewModel.theme.collectAsState()
    val nickname by viewModel.nickname.collectAsState()
    var nicknameInput by remember { mutableStateOf(nickname) }
    val selectedEmoji by viewModel.selectedEmoji.collectAsState(initial = "\uD83D\uDE36")

    val context = LocalContext.current

    SettingsScreenContent(
        modifier = modifier,
        darkMode = context.darkMode(isDarkModeEnabled = theme),
        appVersion  = context.appVersion(appVersion = context.getAppVersionName()),
        nickname = nicknameInput,  // Pass the nickname
        onNicknameChange = { newNickname ->
            nicknameInput = newNickname
            viewModel.updateNickname(newNickname) // Update nickname in DataStore
        },
        selectedEmoji = selectedEmoji,
        onEmojiChange = { newEmoji ->
            viewModel.updateSelectedEmoji(newEmoji)
        },
        onToggleDarkMode = { isChecked ->
            viewModel.updateDarkMode(isChecked)
        }
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SettingsScreenContent(
    darkMode: DarkMode,
    appVersion: AppVersion,
    nickname: String,
    onNicknameChange: (String) -> Unit,
    selectedEmoji: String,
    onEmojiChange: (String) -> Unit,
    onToggleDarkMode: (Boolean) -> Unit,
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
                    Text(
                        text = stringResource(R.string.settings),
                        style = MaterialTheme.typography.headlineLarge,
                        color = MaterialTheme.colorScheme.tertiary
                    )
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
                    appVersion = appVersion,
                    onToggleDarkMode = onToggleDarkMode
                )
            }
        }
    }
}

@Composable
fun SettingsOptionItem(
    darkMode: DarkMode,
    appVersion: AppVersion,
    onToggleDarkMode: (Boolean) -> Unit,
    modifier: Modifier = Modifier
) {
    if (darkMode.title == stringResource(R.string.dark_mode)) {
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
                Text(
                    text = darkMode.title,
                    style = MaterialTheme.typography.bodyMedium,
                    color = MaterialTheme.colorScheme.tertiary
                )
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
                Text(
                    text = appVersion.title,
                    style = MaterialTheme.typography.bodyMedium,
                    color = MaterialTheme.colorScheme.tertiary
                )
            },
            supportingContent = {
                Text(
                    text = appVersion.description,
                    style = MaterialTheme.typography.labelSmall,
                    color = MaterialTheme.colorScheme.tertiary
                )
            },
            colors = ListItemDefaults.colors(
                containerColor = Color.Transparent
            )
        )
    }
}

fun Context.darkMode(
    isDarkModeEnabled: Boolean
) = DarkMode(
        title = getString(R.string.dark_mode),
        description =
            if (isDarkModeEnabled) getString(R.string.dark_mode_on)
            else getString(R.string.dark_mode_off),
        isDarkModeEnabled = isDarkModeEnabled,
        icon =
            if (isDarkModeEnabled) R.drawable.ic_mode_dark
            else R.drawable.ic_mode_light
)

fun Context.appVersion(
    appVersion: String,
) = AppVersion(
        title = getString(R.string.app_version),
        description = appVersion,
        icon = R.drawable.ic_about,
)

@Preview(showBackground = true)
@Preview(uiMode = UI_MODE_NIGHT_YES)
@Composable
private fun SettingsScreenPreview() {
    FluxNewsTheme {
        val context = LocalContext.current
        SettingsScreenContent(
            darkMode = context.darkMode(isDarkModeEnabled = isSystemInDarkTheme()),
            appVersion  = context.appVersion(appVersion = "1.0.0"),
            nickname = "John Doe",
            onNicknameChange = {},
            selectedEmoji = "\uD83D\uDE36",
            onEmojiChange = {},
            onToggleDarkMode = {}
        )
    }
}