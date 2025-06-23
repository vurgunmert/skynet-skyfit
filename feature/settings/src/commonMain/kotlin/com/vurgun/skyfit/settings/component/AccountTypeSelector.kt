package com.vurgun.skyfit.settings.component

import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Divider
import androidx.compose.material.Icon
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.vurgun.skyfit.core.data.v1.domain.account.model.AccountType
import com.vurgun.skyfit.core.data.v1.domain.global.model.AccountRole
import com.vurgun.skyfit.core.ui.components.image.SkyImage
import com.vurgun.skyfit.core.ui.components.image.SkyImageShape
import com.vurgun.skyfit.core.ui.components.image.SkyImageSize
import com.vurgun.skyfit.core.ui.components.text.SkyText
import com.vurgun.skyfit.core.ui.components.text.TextStyleType
import com.vurgun.skyfit.core.ui.styling.SkyFitColor
import org.jetbrains.compose.resources.painterResource
import skyfit.core.ui.generated.resources.Res
import skyfit.core.ui.generated.resources.ic_building
import skyfit.core.ui.generated.resources.ic_checkbox_fill
import skyfit.core.ui.generated.resources.ic_high_intensity_training
import skyfit.core.ui.generated.resources.ic_posture_fill
import skyfit.core.ui.generated.resources.ic_profile

@Composable
private fun AccountTypeSelectorItem(
    item: AccountType,
    selected: Boolean,
    modifier: Modifier = Modifier
) {
    Row(
        modifier = modifier.fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically
    ) {
        val errorPlaceholderRes = when(AccountRole.fromId(item.typeId)) {
            AccountRole.Facility -> Res.drawable.ic_building
            AccountRole.Trainer -> Res.drawable.ic_high_intensity_training
            AccountRole.User -> Res.drawable.ic_posture_fill
            AccountRole.Guest -> null
        }
        SkyImage(
            url = item.photoImageUrl,
            size = SkyImageSize.Size40,
            shape = SkyImageShape.Circle,
            error = errorPlaceholderRes
        )
        Spacer(Modifier.width(16.dp))
        SkyText(
            text = "${item.fullName} (${item.typeName})",
            styleType = TextStyleType.BodyMediumSemibold,
            modifier = Modifier.weight(1f)
        )
        Spacer(Modifier.width(24.dp))
        if (selected) {
            Icon(
                painter = painterResource(Res.drawable.ic_checkbox_fill),
                contentDescription = null,
                modifier = Modifier.size(16.dp),
                tint = SkyFitColor.icon.default
            )
        }
    }
}

@Composable
internal fun AccountTypeSelector(
    accounts: List<AccountType>,
    selectedTypeId: Int,
    onSelectType: (Int) -> Unit
) {
    if (accounts.size > 1) {
        Column(
            Modifier
                .fillMaxWidth()
                .border(1.dp, SkyFitColor.border.secondaryButtonDisabled, RoundedCornerShape(20.dp))
                .padding(16.dp)
        ) {
            accounts.forEachIndexed { index, item ->

                AccountTypeSelectorItem(
                    item = item,
                    selected = item.typeId == selectedTypeId,
                    modifier = Modifier.clickable { onSelectType(item.typeId) }
                )

                if (index < accounts.lastIndex) {
                    Spacer(Modifier.height(12.dp))
                    Divider(Modifier.fillMaxWidth(), SkyFitColor.border.default)
                    Spacer(Modifier.height(12.dp))
                }
            }
        }
    }
}
