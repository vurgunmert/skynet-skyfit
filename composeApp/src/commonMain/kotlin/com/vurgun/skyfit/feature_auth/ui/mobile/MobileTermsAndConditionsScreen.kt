package com.vurgun.skyfit.feature_auth.ui.mobile

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.vurgun.skyfit.core.ui.components.SkyFitScaffold
import com.vurgun.skyfit.core.ui.resources.SkyFitStyleGuide
import com.vurgun.skyfit.core.ui.resources.SkyFitTypography
import moe.tlaster.precompose.navigation.Navigator

@Composable
fun MobileTermsAndConditionsScreen(navigator: Navigator) {

    val termsAndConditionsSections = listOf(
        "1. Giriş" to "Bu hizmet şartları, SkyFit.ai uygulamasını kullanırken uyulması gereken kuralları ve koşulları belirler.",
        "2. Kullanım Koşulları" to "Kullanıcılar, uygulamayı yalnızca yasal ve etik amaçlarla kullanmalıdır.",
        "3. Hesap Güvenliği" to "Kullanıcılar, hesap bilgilerini gizli tutmalıdır. Başkalarının hesaplarına izinsiz erişmek yasaktır.",
        "4. Ücretlendirme ve Abonelik" to "Bazı hizmetler ücretli olabilir. Abonelikler otomatik olarak yenilenebilir ve kullanıcılar aboneliklerini istedikleri zaman iptal edebilir.",
        "5. İçerik Hakları" to "Kullanıcılar, yükledikleri içeriklerin yasal sahibi olduklarını kabul eder.",
        "6. Sorumluluk Reddi" to "Uygulama, sunulan içeriklerin doğruluğunu garanti etmez.",
        "7. Şartların Güncellenmesi" to "Bu hizmet şartları güncellenebilir ve değişiklikler bildirilecektir.",
        "8. İletişim" to "Hizmet şartları hakkında sorularınız için bizimle iletişime geçebilirsiniz."
    )

    SkyFitScaffold(
        topBar = {
            LegalScreenTopBar(title = "Hizmet Şartları", onClickClose = { navigator.popBackStack() })
        },
        content = { padding ->
            Column(
                modifier = Modifier
                    .widthIn(max = SkyFitStyleGuide.Mobile.maxWidth)
                    .padding(padding)
                    .padding(16.dp)
                    .verticalScroll(rememberScrollState()),
                horizontalAlignment = Alignment.Start,
                verticalArrangement = Arrangement.spacedBy(24.dp)
            ) {
                Text(
                    text = "Hizmet Şartları",
                    style = SkyFitTypography.heading4
                )

                termsAndConditionsSections.forEach { (sectionTitle, sectionContent) ->
                    LegalScreenSection(sectionTitle, sectionContent)
                }
                Spacer(modifier = Modifier.height(24.dp))
            }
        }
    )
}