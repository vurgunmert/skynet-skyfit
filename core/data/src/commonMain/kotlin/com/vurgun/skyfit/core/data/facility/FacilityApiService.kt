package com.vurgun.skyfit.core.data.facility

import com.vurgun.skyfit.core.network.ApiClient

class FacilityApiService(private val apiClient: ApiClient) {

    private object Endpoint {
        // Membership Package
        const val GET_PACKAGES = "facility/membership/packages"
        const val CREATE_PACKAGE = "facility/membership/package"
        const val UPDATE_PACKAGE = "facility/membership/package"
        const val DELETE_PACKAGE = "facility/membership/package"
        // Branch
        const val GET_BRANCHES = "facility/membership/branches"
        const val CREATE_BRANCH = "facility/membership/branch"
        const val UPDATE_BRANCH = "facility/membership/branch"
        const val DELETE_BRANCH = "facility/membership/branch"
        // Trainer
        const val ADD_STAFF_TRAINER = "facility/staff/trainer"
        const val DELETE_STAFF_TRAINER = "facility/staff/trainer"
        // Member
        const val ADD_MEMBER = "facility/member"
        const val DELETE_MEMBER = "facility/member"
        const val UPDATE_MEMBER_PACKAGE = "facility/member/package"
        const val DELETE_MEMBER_PACKAGE = "facility/member/package"
        // Membership
        const val REQUEST_MEMBERSHIP = "facility/membership/request"
        const val RESPOND_TO_MEMBERSHIP = "user/membership/response"
    }


}