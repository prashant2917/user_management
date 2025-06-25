package com.pocket.usermanagement.main

import com.pocket.usermanagement.features.profile.data.entity.UserProfileEntity

object ApplicationDataSource {

    private var userProfileEntity: UserProfileEntity? = null

    fun setUserProfileEntity(userProfileEntity: UserProfileEntity?) {
        this.userProfileEntity = userProfileEntity

    }

    fun getUserProfileEntity() = userProfileEntity

}