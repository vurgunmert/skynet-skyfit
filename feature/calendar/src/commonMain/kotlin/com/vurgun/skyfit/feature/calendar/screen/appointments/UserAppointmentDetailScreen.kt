package com.vurgun.skyfit.feature.calendar.screen.appointments

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import cafe.adriel.voyager.core.screen.Screen
import cafe.adriel.voyager.koin.koinScreenModel
import cafe.adriel.voyager.navigator.LocalNavigator
import cafe.adriel.voyager.navigator.currentOrThrow
import com.vurgun.skyfit.core.ui.components.dialog.ErrorDialog
import com.vurgun.skyfit.core.ui.components.dialog.rememberErrorDialogState
import com.vurgun.skyfit.core.ui.components.loader.FullScreenLoaderContent
import com.vurgun.skyfit.core.ui.components.special.SkyFitMobileScaffold
import com.vurgun.skyfit.core.ui.screen.ErrorScreen
import com.vurgun.skyfit.core.ui.styling.SkyFitColor
import com.vurgun.skyfit.core.ui.styling.SkyFitTypography
import com.vurgun.skyfit.core.ui.utils.CollectEffect
import org.jetbrains.compose.resources.DrawableResource
import org.jetbrains.compose.resources.painterResource
import org.jetbrains.compose.resources.stringResource
import skyfit.core.ui.generated.resources.Res
import skyfit.core.ui.generated.resources.cancel_action
import skyfit.core.ui.generated.resources.error_cancel_appointment_title
import skyfit.core.ui.generated.resources.ic_check_circle
import skyfit.core.ui.generated.resources.ic_chevron_left
import skyfit.core.ui.generated.resources.ic_clock
import skyfit.core.ui.generated.resources.ic_location_pin
import skyfit.core.ui.generated.resources.ic_profile
import skyfit.core.ui.generated.resources.trainer_note_label

class UserAppointmentDetailScreen(private val lpId: Int) : Screen {

    @Composable
    override fun Content() {
        val navigator = LocalNavigator.currentOrThrow
        val viewModel = koinScreenModel<UserAppointmentDetailViewModel>()
        val cancelDialog = rememberErrorDialogState()

        CollectEffect(viewModel.effect) { effect ->
            when (effect) {
                UserAppointmentDetailEffect.NavigateToBack -> navigator.pop()
                is UserAppointmentDetailEffect.ShowCancelError -> cancelDialog.show(effect.message)
            }
        }

        LaunchedEffect(Unit) {
            viewModel.loadAppointment(lpId)
        }

        MobileUserAppointmentDetailScreen(viewModel)

        cancelDialog.message?.let { message ->
            ErrorDialog(
                title = stringResource(Res.string.error_cancel_appointment_title),
                message = message,
                onDismiss = cancelDialog::dismiss
            )
        }
    }
}

@Composable
private fun MobileUserAppointmentDetailScreen(viewModel: UserAppointmentDetailViewModel) {
    val uiState by viewModel.uiState.collectAsState()

    when (uiState) {
        UserAppointmentDetailUiState.Loading -> FullScreenLoaderContent()
        is UserAppointmentDetailUiState.Error -> {
            val message = (uiState as UserAppointmentDetailUiState.Error).message
            ErrorScreen(
                message = message,
                onConfirm = { viewModel.onAction(UserAppointmentDetailAction.NavigateToBack) })
        }

        is UserAppointmentDetailUiState.Content -> {
            val content = (uiState as UserAppointmentDetailUiState.Content)
            MobileUserAppointmentDetailComponent.Content(content, viewModel::onAction)
        }
    }
}

private object MobileUserAppointmentDetailComponent {

    @Composable
    fun Content(
        content: UserAppointmentDetailUiState.Content,
        onAction: (UserAppointmentDetailAction) -> Unit
    ) {
        SkyFitMobileScaffold(
            topBar = {
                MobileAppointmentDetailComponent.MobileUserAppointmentDetail_TopBar(
                    title = content.appointment.title,
                    onClickBack = { onAction(UserAppointmentDetailAction.NavigateToBack) },
                    status = content.appointment.status,
                    statusName = content.appointment.statusName
                )
            }
        ) {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(horizontal = 16.dp)
            ) {
                MobileAppointmentDetailComponent.MobileUserAppointmentDetail_InfoGrid(
                    startDate = content.appointment.startDate.toString(),
                    startTime = content.appointment.startTime.toString(),
                    endTime = content.appointment.endTime.toString(),
                    trainerFullName = content.appointment.trainerFullName,
                    facilityName = content.appointment.gymName,
                    participantCount = content.appointment.participantCount
                )
                MobileAppointmentDetailComponent.MobileUserAppointmentDetail_TrainerNote(content.appointment.trainerNote)
                Spacer(Modifier.weight(1f))

                if (content.appointment.status == 1) {
                    CancelAction(
                        onClick = { onAction(UserAppointmentDetailAction.CancelAppointment) },
                        modifier = Modifier.padding(16.dp)
                    )
                }
            }
        }
    }


    @Composable
    fun CancelAction(
        onClick: () -> Unit,
        modifier: Modifier
    ) {
        Box(
            modifier
                .clip(CircleShape)
                .clickable { onClick() }
                .fillMaxWidth()
                .background(SkyFitColor.specialty.secondaryButtonRest, CircleShape)
                .border(1.dp, SkyFitColor.border.critical, CircleShape)
                .padding(vertical = 12.dp),
            contentAlignment = Alignment.Center
        ) {
            Text(
                text = stringResource(Res.string.cancel_action),
                color = SkyFitColor.text.criticalOnBgFill,
                style = SkyFitTypography.bodyMediumSemibold
            )
        }
    }
}

object MobileAppointmentDetailComponent {
    @Composable
    fun MobileUserAppointmentDetail_TopBar(
        title: String,
        status: Int,
        statusName: String,
        onClickBack: () -> Unit
    ) {
        Box(Modifier.fillMaxWidth().padding(horizontal = 24.dp, vertical = 8.dp)) {
            Icon(
                painter = painterResource(Res.drawable.ic_chevron_left),
                contentDescription = "Back",
                tint = SkyFitColor.text.default,
                modifier = Modifier.align(Alignment.CenterStart).size(16.dp).clickable(onClick = onClickBack)
            )

            Text(
                text = title,
                style = SkyFitTypography.bodyLargeSemibold,
                modifier = Modifier.align(Alignment.Center)
            )

            AppointmentStatusChip(status, statusName, modifier = Modifier.align(Alignment.CenterEnd))
        }
    }


    @Composable
    fun MobileUserAppointmentDetail_InfoGrid(
        startDate: String,
        startTime: String,
        endTime: String,
        trainerFullName: String,
        facilityName: String,
        participantCount: Int
    ) {
        Column(Modifier.fillMaxWidth().padding(vertical = 16.dp)) {
            AppointmentDetailRow(
                leftIconRes = Res.drawable.ic_clock,
                leftTitle = "Tarih - Saat",
                leftValue = "${startDate}\n${startTime}-${endTime}",
                rightIconRes = Res.drawable.ic_profile,
                rightTitle = "Eğitmen",
                rightValue = trainerFullName
            )

            Spacer(Modifier.height(16.dp))

            AppointmentDetailRow(
                leftIconRes = Res.drawable.ic_location_pin,
                leftTitle = "Studio",
                leftValue = facilityName,
                rightIconRes = Res.drawable.ic_check_circle,
                rightTitle = "Toplam Katılımcı",
                rightValue = "$participantCount"
            )
        }
    }

    @Composable
    private fun AppointmentDetailRow(
        leftIconRes: DrawableResource,
        leftTitle: String,
        leftValue: String,
        rightIconRes: DrawableResource,
        rightTitle: String,
        rightValue: String
    ) {
        BoxWithConstraints(Modifier.fillMaxWidth()) {
            val maxHeight = remember { mutableStateOf(0.dp) } // Store max height

            Row(Modifier.fillMaxWidth()) {
                AppointmentDetailItemCard(leftIconRes, leftTitle, leftValue, Modifier.weight(1f), maxHeight)
                Spacer(Modifier.width(16.dp))
                AppointmentDetailItemCard(rightIconRes, rightTitle, rightValue, Modifier.weight(1f), maxHeight)
            }
        }
    }

    @Composable
    private fun AppointmentDetailItemCard(
        iconRes: DrawableResource,
        title: String,
        value: String,
        modifier: Modifier,
        maxHeight: MutableState<Dp>
    ) {
        val density = LocalDensity.current // Get current density for conversion

        Box(
            modifier
                .background(SkyFitColor.background.fillTransparent, RoundedCornerShape(16.dp))
                .heightIn(min = maxHeight.value) // Apply max height to sync cards
                .onGloballyPositioned { coordinates ->
                    val newHeight = with(density) { coordinates.size.height.toDp() } // Convert px → dp
                    if (newHeight > maxHeight.value) {
                        maxHeight.value = newHeight // Update max height
                    }
                }
                .padding(12.dp)
        ) {
            Column {
                Icon(
                    painter = painterResource(iconRes),
                    contentDescription = "Icon",
                    tint = SkyFitColor.text.default,
                    modifier = Modifier.size(16.dp)
                )
                Spacer(Modifier.height(8.dp))
                Text(
                    text = title,
                    style = SkyFitTypography.bodyMediumSemibold,
                    color = SkyFitColor.text.secondary
                )
                Spacer(Modifier.height(8.dp))
                Text(
                    text = value,
                    style = SkyFitTypography.bodyLargeSemibold
                )
            }
        }
    }


    // 📝 **Trainer Note**
    @Composable
    fun MobileUserAppointmentDetail_TrainerNote(note: String?) {
        if (!note.isNullOrEmpty()) {
            Text(
                text = stringResource(Res.string.trainer_note_label),
                style = SkyFitTypography.bodyMediumSemibold,
                modifier = Modifier.padding(top = 16.dp, start = 8.dp)
            )
            Text(
                text = note,
                style = SkyFitTypography.bodyMediumRegular,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 8.dp)
                    .background(SkyFitColor.background.fillTransparent, shape = RoundedCornerShape(8.dp))
                    .padding(16.dp)
            )
        }
    }

    @Composable
    private fun AppointmentStatusChip(status: Int, statusName: String, modifier: Modifier = Modifier) {
        val chipColor = when (status) {
            1 -> SkyFitColor.background.surfaceSuccessActive
            6 -> SkyFitColor.background.surfaceCriticalActive
            5 -> SkyFitColor.background.surfaceSecondary
            else -> SkyFitColor.background.surface
        }

        val textColor = when (status) {
            1 -> SkyFitColor.text.successOnBgFill
            6 -> SkyFitColor.text.criticalOnBgFill
            5 -> SkyFitColor.text.inverseSecondary
            else -> SkyFitColor.text.default
        }

        Box(
            modifier = modifier
                .height(28.dp)
                .clip(RoundedCornerShape(8.dp))
                .background(chipColor)
                .padding(horizontal = 8.dp, vertical = 4.dp)
        ) {
            Text(
                text = statusName,
                color = textColor,
                style = SkyFitTypography.bodyMediumMedium,
                modifier = Modifier.align(Alignment.Center)
            )
        }
    }
}


