package com.vurgun.skyfit.core.ui.components.form

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.unit.dp
import com.vurgun.skyfit.core.ui.components.icon.SkyIcon
import com.vurgun.skyfit.core.ui.components.icon.SkyIconSize
import com.vurgun.skyfit.core.ui.components.text.SkyInputTextField
import com.vurgun.skyfit.core.ui.components.text.SkyText
import com.vurgun.skyfit.core.ui.components.text.TextStyleType
import com.vurgun.skyfit.core.ui.styling.SkyFitColor
import org.jetbrains.compose.resources.DrawableResource
import org.jetbrains.compose.ui.tooling.preview.Preview

@Composable
fun SkyFormMultilineTextField(
    title: String,
    hint: String,
    value: String? = null,
    onValueChange: ((String) -> Unit)? = null,
    rightIconRes: DrawableResource? = null,
    focusRequester: FocusRequester = FocusRequester(),
    nextFocusRequester: FocusRequester? = null,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier.fillMaxWidth()
    ) {
        SkyText(
            text = title,
            styleType = TextStyleType.BodyMediumSemibold,
            modifier = Modifier.padding(horizontal = 8.dp)
        )

        Spacer(Modifier.height(8.dp))

        Row(
            modifier = modifier
                .fillMaxWidth()
                .background(
                    color = SkyFitColor.background.surfaceSecondary,
                    shape = RoundedCornerShape(12.dp)
                )
                .padding(horizontal = 16.dp, vertical = 12.dp)
        ) {
            SkyInputTextField(
                hint = hint,
                value = value,
                singleLine = false,
                onValueChange = onValueChange,
                focusRequester = focusRequester,
                nextFocusRequester = nextFocusRequester,
                modifier = Modifier
                    .weight(1f)
                    .heightIn(min = 64.dp)
            )

            if (rightIconRes != null) {
                Spacer(Modifier.width(8.dp))
                SkyIcon(rightIconRes, size = SkyIconSize.Small)
            }
        }
    }
}

@Preview
@Composable
private fun SkyFormMultilineTextFieldPreview_Empty() {
    SkyFormMultilineTextField(
        title = "Eğitmenin Notu",
        hint = "Açıklama ekle",
        value = null,
        onValueChange = {},
        modifier = Modifier.fillMaxWidth()
    )
}

@Preview
@Composable
private fun SkyFormMultilineTextFieldPreview() {
    SkyFormMultilineTextField(
        title = "Eğitmenin Notu",
        hint = "Açıklama ekle",
        value = "Su ve matlarınızı getirmeyi unutmayın!\nLine2\nLine3",
        onValueChange = {},
        modifier = Modifier.fillMaxWidth()
    )
}

@Preview
@Composable
private fun SkyFormMultilineTextFieldPreview_Large() {
    SkyFormMultilineTextField(
        title = "Lütfen aşağıdaki alanı doldururken kişisel bilgilerinizin gizliliğini korumak adına yalnızca gerekli ve doğru bilgileri girdiğinizden emin olunuz – aksi halde işlem tamamlanamayabilir veya doğrulama süreci uzayabilir.",
        hint = "Açıklama ekle",
        value = "Line 1:\n" +
                "\uD83D\uDC49 Egzersiz seansı boyunca sağlığınızı korumak ve maksimum verim almak adına kişisel su şişenizi ve egzersiz matınızı yanınızda getirmeniz önemle rica olunur.\n" +
                "\n" +
                "Line 2:\n" +
                "\uD83D\uDCCC Lütfen salona zamanında gelerek kayıt işlemlerini tamamlayınız; geç gelen katılımcıların derslere alınması mümkün olmayabilir.\n" +
                "\n" +
                "Line 3:\n" +
                "⚠\uFE0F Tesis içerisinde gürültü yapmaktan kaçınınız ve diğer üyelerin deneyimine saygı gösteriniz, özellikle ders saatleri sırasında sessiz olunuz.\n" +
                "\n" +
                "Line 4:\n" +
                "\uD83E\uDDFD Kullandığınız ekipmanları ders bitiminde hijyen kuralları çerçevesinde dezenfekte ederek yerine koymanız gerekmektedir.\n" +
                "\n" +
                "Line 5:\n" +
                "\uD83E\uDDD8 Seans sırasında telefon kullanımını minimumda tutmanız, dikkatinizin dağılmaması ve zihinsel farkındalık için tavsiye edilmektedir.\n" +
                "\n" +
                "Line 6:\n" +
                "✅ Herhangi bir sağlık probleminiz varsa, eğitmene önceden bilgi vermeniz güvenliğiniz açısından kritik öneme sahiptir.\n" +
                "\n",
        onValueChange = {},
        modifier = Modifier.fillMaxWidth()
    )
}