package com.vurgun.skyfit.feature.access.legal

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import cafe.adriel.voyager.core.screen.Screen
import cafe.adriel.voyager.navigator.LocalNavigator
import cafe.adriel.voyager.navigator.currentOrThrow
import com.vurgun.skyfit.core.ui.components.special.SkyFitMobileScaffold
import com.vurgun.skyfit.core.ui.styling.SkyFitColor
import com.vurgun.skyfit.core.ui.styling.SkyFitTypography
import org.jetbrains.compose.resources.painterResource
import skyfit.core.ui.generated.resources.Res
import skyfit.core.ui.generated.resources.ic_close

class PrivacyPolicyScreen: Screen {

    @Composable
    override fun Content() {
        val appNavigator = LocalNavigator.currentOrThrow

        MobilePrivacyPolicyScreen(
            goToBack = { appNavigator.pop() }
        )
    }

}

@Composable
private fun MobilePrivacyPolicyScreen(
    goToBack: () -> Unit
) {
    val privacyPolicySections = listOf(
        "1. Giriş" to "Skyfit.ai olarak, gizliliğinize önem veriyoruz. Bu gizlilik politikası, uygulamamızı kullanırken kişisel verilerinizin nasıl toplandığını, kullanıldığını ve korunduğunu açıklar.",
        "2. Toplanan Veriler" to "Uygulamamız aşağıdaki bilgileri toplayabilir:\n- Ad, soyad, e-posta adresi, telefon numarası\n- Egzersiz verileri, vücut analizi, aktivite geçmişi\n- Cihaz bilgileri, IP adresi, çerez verileri",
        "3. Verilerin Kullanım Amaçları" to "Toplanan veriler şu amaçlarla kullanılır:\n- Kişiselleştirilmiş antrenman planları sunmak\n- Kullanıcı deneyimini geliştirmek\n- Uygulama güvenliğini sağlamak",
        "4. Verilerin Korunması" to "Verilerinizi güvende tutmak için teknik ve idari güvenlik önlemleri alıyoruz.",
        "5. Üçüncü Taraf Paylaşımları" to "Kişisel verileriniz, açık rızanız olmadan üçüncü taraflarla paylaşılmaz. Ancak yasal gereklilikler nedeniyle paylaşım olabilir.",
        "6. Kullanıcı Hakları" to "Kullanıcı olarak şunlara sahipsiniz:\n- Verilerinize erişme ve güncelleme\n- Verilerinizin silinmesini talep etme\n- İşlenmesine itiraz etme",
        "7. Çerezler ve Takip Teknolojileri" to "Uygulama deneyiminizi geliştirmek için çerezler kullanılmaktadır.",
        "8. Politika Güncellemeleri" to "Bu gizlilik politikası zaman zaman güncellenebilir.",
        "9. İletişim" to "Gizlilik politikamız hakkında sorularınız varsa, [e-posta adresi] üzerinden ulaşabilirsiniz."
    )


    SkyFitMobileScaffold(
        topBar = {
            LegalScreenTopBar(title = "Gizlilik Politikası", onClickClose = goToBack)
        },
        content = { padding ->
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(padding)
                    .padding(16.dp)
                    .verticalScroll(rememberScrollState()),
                horizontalAlignment = Alignment.Start,
                verticalArrangement = Arrangement.spacedBy(24.dp)
            ) {
                Text(
                    text = "Gizlilik Politikası",
                    style = SkyFitTypography.heading4
                )

                privacyPolicySections.forEach { (sectionTitle, sectionContent) ->
                    LegalScreenSection(sectionTitle, sectionContent)
                }

                Spacer(modifier = Modifier.height(24.dp))
            }
        }
    )
}

@Composable
fun LegalScreenTopBar(title: String, onClickClose: () -> Unit) {
    Box(Modifier.fillMaxWidth().padding(vertical = 32.dp, horizontal = 20.dp)) {
        Icon(
            painter = painterResource(Res.drawable.ic_close),
            contentDescription = "Close",
            tint = SkyFitColor.icon.default,
            modifier = Modifier.size(20.dp).align(Alignment.CenterStart).clickable(onClick = onClickClose)
        )

        Text(
            title,
            style = SkyFitTypography.bodyLargeSemibold,
            modifier = Modifier.align(Alignment.Center)
        )
    }
}

@Composable
fun LegalScreenSection(title: String, content: String) {
    Column(modifier = Modifier.fillMaxWidth()) {
        Text(text = title, style = SkyFitTypography.heading6)
        Spacer(modifier = Modifier.height(8.dp))
        Text(text = content, style = SkyFitTypography.bodyLarge)
    }
}