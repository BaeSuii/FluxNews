package com.baesuii.fluxnews.presentation.settings

import android.content.Context
import android.content.res.Configuration.UI_MODE_NIGHT_YES
import androidx.compose.foundation.clickable
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Check
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.ListItem
import androidx.compose.material3.ListItemDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.baesuii.fluxnews.R
import com.baesuii.fluxnews.domain.model.AppTheme
import com.baesuii.fluxnews.domain.model.Settings
import com.baesuii.fluxnews.presentation.common.StatusBar
import com.baesuii.fluxnews.presentation.theme.Dimensions.paddingMedium
import com.baesuii.fluxnews.presentation.theme.Dimensions.paddingSemiMedium
import com.baesuii.fluxnews.presentation.theme.Dimensions.paddingSmall
import com.baesuii.fluxnews.presentation.theme.FluxNewsTheme
import com.baesuii.fluxnews.presentation.theme.Theme

@Composable
fun SettingsScreen(
    modifier: Modifier = Modifier,
    viewModel: SettingsViewModel = hiltViewModel()
) {
    val isDarkTheme = isSystemInDarkTheme()
    StatusBar(
        darkTheme = isDarkTheme,
        darkThemeColor = Color.Transparent,
        lightThemeColor = Color.Transparent
    )

    var shouldShowThemesDialog by rememberSaveable {
        mutableStateOf(false)
    }

    val theme by viewModel.theme.collectAsState()
    val context = LocalContext.current

    SettingsScreenContent(
        modifier = modifier,
        settings = context.settings(
            selectTheme = theme,
            appVersion = context.getAppVersionName()
        ),
        appTheme = context.themes(),
        shouldShowThemesDialog = shouldShowThemesDialog,
        selectedTheme = theme,
        onDismissThemesDialog = {
            shouldShowThemesDialog = false
        },
        updateTheme = {
            viewModel.updateTheme(it)
        },
        onClickOption = {
            when (it) {
                context.getString(R.string.theme) -> {
                    shouldShowThemesDialog = true
                }
            }
        }
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SettingsScreenContent(
    shouldShowThemesDialog: Boolean,
    selectedTheme: Int,
    onDismissThemesDialog: () -> Unit,
    updateTheme: (Int) -> Unit,
    onClickOption: (String) -> Unit,
    settings: List<Settings>,
    appTheme: List<AppTheme>,
    modifier: Modifier = Modifier
) {
    Scaffold(
        modifier = modifier
            .fillMaxSize()
            .statusBarsPadding(),
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
            modifier = Modifier.padding(it)
        ) {
            items(settings) { option ->
                SettingsOptionItem(
                    settings = option,
                    onClick = {
                        onClickOption(option.title)
                    }
                )
            }
        }
    }

    if (shouldShowThemesDialog) {
        ThemesDialog(
            appTheme = appTheme,
            selectedTheme = selectedTheme,
            onDismiss = onDismissThemesDialog,
            onSelectTheme = {
                onDismissThemesDialog()
                updateTheme(it)
            }
        )
    }
}

@Composable
fun SettingsOptionItem(
    settings: Settings,
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    ListItem(
        modifier = modifier
            .fillMaxWidth()
            .padding(vertical = paddingSmall, horizontal = paddingMedium)
            .clickable {
                onClick()
            },
        leadingContent = {
            Icon(
                modifier = Modifier.size(24.dp),
                painter = painterResource(id = settings.icon),
                contentDescription = settings.title
            )
        },
        headlineContent = {
            Text(
                text = settings.title,
                style = MaterialTheme.typography.bodyMedium,
                color = MaterialTheme.colorScheme.tertiary
            )
        },
        supportingContent = {
            Text(
                text = settings.description,
                style = MaterialTheme.typography.labelSmall,
                color = MaterialTheme.colorScheme.tertiary
            )
        },
        colors = ListItemDefaults.colors(
            containerColor = MaterialTheme.colorScheme.background
        )
    )
}

@Composable
fun ThemesDialog(
    appTheme: List<AppTheme>,
    onDismiss: () -> Unit,
    onSelectTheme: (Int) -> Unit,
    selectedTheme: Int,
    modifier: Modifier = Modifier
) {
    AlertDialog(
        modifier = modifier,
        containerColor = MaterialTheme.colorScheme.background,
        shape = RoundedCornerShape(0),
        onDismissRequest = { onDismiss() },
        title = {
            Text(
                text = stringResource(R.string.select_theme),
                style = MaterialTheme.typography.titleLarge
            )
        },
        text = {
            LazyColumn(
                verticalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                items(appTheme) { theme ->
                    ThemeItem(
                        name = theme.name,
                        value = theme.value,
                        icon = theme.icon,
                        onSelectTheme = onSelectTheme,
                        isSelected = theme.value == selectedTheme
                    )
                }
            }
        },
        confirmButton = {}
    )
}

@Composable
fun ThemeItem(
    name: String,
    value: Int,
    icon: Int,
    onSelectTheme: (Int) -> Unit,
    isSelected: Boolean,
    modifier: Modifier = Modifier
) {
    Row(
        modifier = modifier
            .fillMaxWidth()
            .clickable {
                onSelectTheme(value)
            },
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween,
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth(.75f),
            horizontalArrangement = Arrangement.spacedBy(16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Icon(
                modifier = Modifier
                    .size(24.dp),
                painter = painterResource(id = icon),
                contentDescription = null
            )
            Text(
                modifier = Modifier,
                text = name,
                style = MaterialTheme.typography.bodyMedium
            )
        }

        if (isSelected) {
            Icon(
                modifier = Modifier
                    .size(24.dp),
                imageVector = Icons.Default.Check,
                contentDescription = null,
            )
        }
    }
}

@Preview(showBackground = true)
@Preview(uiMode = UI_MODE_NIGHT_YES)
@Composable
private fun SettingsScreenPreview() {
    FluxNewsTheme {
        val context = LocalContext.current
        SettingsScreenContent(
            appTheme = context.themes(),
            settings = context.settings(
                selectTheme = Theme.FOLLOW_SYSTEM.value,
                appVersion = "1.0.0"
            ),
            shouldShowThemesDialog = false,
            selectedTheme = Theme.FOLLOW_SYSTEM.value,
            onDismissThemesDialog = {},
            updateTheme = {},
            onClickOption = {}
        )
    }
}

fun Context.settings(
    selectTheme: Int,
    appVersion: String,
) = listOf(
    Settings(
        title = getString(R.string.theme),
        description = selectTheme.getThemeName(context = this),
        icon = R.drawable.ic_mode,
    ),
    Settings(
        title = getString(R.string.app_version),
        description = appVersion,
        icon = R.drawable.ic_about,
    )
)

fun Context.themes() = listOf(
    AppTheme(
        name = getString(R.string.use_system_settings),
        value = Theme.FOLLOW_SYSTEM.value,
        icon = R.drawable.ic_mode
    ),
    AppTheme(
        name = getString(R.string.light_mode),
        value = Theme.LIGHT_THEME.value,
        icon = R.drawable.ic_mode_light
    ),
    AppTheme(
        name = getString(R.string.dark_mode),
        value = Theme.DARK_THEME.value,
        icon = R.drawable.ic_mode_dark
    )
)
