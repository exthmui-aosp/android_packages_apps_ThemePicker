/*
 * Copyright (C) 2024 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.android.wallpaper

import androidx.test.core.app.ApplicationProvider
import com.android.customization.model.color.ColorCustomizationManager
import com.android.customization.model.theme.OverlayManagerCompat
import com.android.customization.module.CustomizationInjector
import com.android.customization.module.CustomizationPreferences
import com.android.customization.module.logging.TestThemesUserEventLogger
import com.android.customization.module.logging.ThemesUserEventLogger
import com.android.customization.testing.TestCustomizationInjector
import com.android.customization.testing.TestDefaultCustomizationPreferences
import com.android.wallpaper.module.Injector
import com.android.wallpaper.module.PartnerProvider
import com.android.wallpaper.module.WallpaperPreferences
import com.android.wallpaper.module.logging.TestUserEventLogger
import com.android.wallpaper.module.logging.UserEventLogger
import com.android.wallpaper.modules.ThemePickerAppModule
import com.android.wallpaper.picker.preview.data.util.DefaultLiveWallpaperDownloader
import com.android.wallpaper.picker.preview.data.util.LiveWallpaperDownloader
import com.android.wallpaper.testing.TestInjector
import com.android.wallpaper.testing.TestPartnerProvider
import com.android.wallpaper.testing.TestWallpaperPreferences
import com.android.wallpaper.util.converter.DefaultWallpaperModelFactory
import com.android.wallpaper.util.converter.WallpaperModelFactory
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.hilt.components.SingletonComponent
import dagger.hilt.testing.TestInstallIn
import javax.inject.Singleton

@Module
@TestInstallIn(components = [SingletonComponent::class], replaces = [ThemePickerAppModule::class])
abstract class ThemePickerTestModule {
    //// WallpaperPicker2 prod

    @Binds @Singleton abstract fun bindInjector(impl: TestCustomizationInjector): Injector

    @Binds @Singleton abstract fun bindUserEventLogger(impl: TestUserEventLogger): UserEventLogger

    @Binds
    @Singleton
    abstract fun bindThemesUserEventLogger(impl: TestThemesUserEventLogger): ThemesUserEventLogger

    @Binds
    @Singleton
    abstract fun bindWallpaperPrefs(impl: TestDefaultCustomizationPreferences): WallpaperPreferences

    //// WallpaperPicker2 test

    @Binds @Singleton abstract fun bindTestInjector(impl: TestCustomizationInjector): TestInjector

    @Binds
    @Singleton
    abstract fun bindTestWallpaperPrefs(
        impl: TestDefaultCustomizationPreferences
    ): TestWallpaperPreferences

    //// ThemePicker prod

    @Binds
    @Singleton
    abstract fun bindCustomizationInjector(impl: TestCustomizationInjector): CustomizationInjector

    @Binds
    @Singleton
    abstract fun bindCustomizationPrefs(
        impl: TestDefaultCustomizationPreferences
    ): CustomizationPreferences

    @Binds
    @Singleton
    abstract fun bindWallpaperModelFactory(
        impl: DefaultWallpaperModelFactory
    ): WallpaperModelFactory

    @Binds
    @Singleton
    abstract fun bindLiveWallpaperDownloader(
        impl: DefaultLiveWallpaperDownloader
    ): LiveWallpaperDownloader

    @Binds
    @Singleton
    abstract fun providePartnerProvider(impl: TestPartnerProvider): PartnerProvider

    companion object {
        @Provides
        @Singleton
        fun provideColorCustomizationManager(): ColorCustomizationManager {
            return ColorCustomizationManager.getInstance(
                ApplicationProvider.getApplicationContext(),
                OverlayManagerCompat(ApplicationProvider.getApplicationContext())
            )
        }
    }
}