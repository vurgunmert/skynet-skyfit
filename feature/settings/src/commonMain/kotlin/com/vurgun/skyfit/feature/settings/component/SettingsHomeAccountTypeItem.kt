package com.vurgun.skyfit.feature.settings.component

import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Divider
import androidx.compose.material.Icon
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.vurgun.skyfit.data.core.domain.model.UserAccountType
import com.vurgun.skyfit.ui.core.components.image.CircleNetworkImage
import com.vurgun.skyfit.ui.core.components.menu.MobileSettingsMenuItemDividerComponent
import com.vurgun.skyfit.ui.core.components.text.BodyMediumSemiboldText
import com.vurgun.skyfit.ui.core.styling.SkyFitColor
import org.jetbrains.compose.resources.painterResource
import skyfit.ui.core.generated.resources.Res
import skyfit.ui.core.generated.resources.ic_checkbox_fill

@Composable
internal fun SettingsHomeAccountTypeItem(
    item: UserAccountType,
    selected: Boolean,
    modifier: Modifier = Modifier
) {
    Row(
        modifier.fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically
    ) {
        CircleNetworkImage(
            url = item.profilePhoto.takeUnless { it.isNullOrEmpty() } ?: "https://cdn-icons-png.flaticon.com/512/3135/3135715.png",
            size = 40.dp,
            modifier = Modifier
        )
        Spacer(Modifier.width(16.dp))
        BodyMediumSemiboldText(
            text = "${item.fullName} (${item.typeName})",
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
internal fun SettingsHomeAccountTypesColumn(
    accounts: List<UserAccountType>,
    selectedTypeId: Int,
    onSelectType: (Int) -> Unit
) {
    if (accounts.size > 1) {
        MobileSettingsMenuItemDividerComponent()

        Column(
            Modifier
                .fillMaxWidth()
                .border(1.dp, SkyFitColor.border.secondaryButtonDisabled, RoundedCornerShape(20.dp))
                .padding(16.dp)
        ) {
            accounts.forEachIndexed { index, item ->

                SettingsHomeAccountTypeItem(
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
