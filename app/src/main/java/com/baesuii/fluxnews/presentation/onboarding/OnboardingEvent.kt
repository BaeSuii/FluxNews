package com.baesuii.fluxnews.presentation.onboarding

sealed class OnboardingEvent {
    object SaveAppEntry: OnboardingEvent()
}