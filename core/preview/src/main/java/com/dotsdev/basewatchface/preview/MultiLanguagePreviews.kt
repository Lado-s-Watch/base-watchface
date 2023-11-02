package com.dotsdev.basewatchface.preview

import androidx.compose.ui.tooling.preview.Preview

@Retention(AnnotationRetention.SOURCE)
@Target(AnnotationTarget.CLASS, AnnotationTarget.FUNCTION)
annotation class ShowcaseMultiplePreviewsWorkaround

@ShowcaseMultiplePreviewsWorkaround
object MultiLanguagePreviewDefinition {
    const val Group = "Language"

    object Vietnamese {
        const val Name = "Vietnamese"
        const val Locale = "vi_VN"
        const val Language = "vi"
    }

    object English {
        const val Name = "English"
        const val Locale = "en_US"
        const val Language = "en"
    }
}

@Preview(
    name = MultiLanguagePreviewDefinition.Vietnamese.Name,
    group = MultiLanguagePreviewDefinition.Group,
    locale = MultiLanguagePreviewDefinition.Vietnamese.Language,
)
@Preview(
    name = MultiLanguagePreviewDefinition.English.Name,
    group = MultiLanguagePreviewDefinition.Group,
    locale = MultiLanguagePreviewDefinition.English.Language,
)
annotation class MultiLanguagePreviews