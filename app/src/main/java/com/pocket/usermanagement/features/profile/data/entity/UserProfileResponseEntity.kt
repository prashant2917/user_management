package com.pocket.usermanagement.features.profile.data.entity

import com.pocket.usermanagement.features.profile.data.model.Address
import com.pocket.usermanagement.features.profile.data.model.Bank
import com.pocket.usermanagement.features.profile.data.model.Company
import com.pocket.usermanagement.features.profile.data.model.Coordinates
import com.pocket.usermanagement.features.profile.data.model.Crypto
import com.pocket.usermanagement.features.profile.data.model.Hair
import com.pocket.usermanagement.features.profile.data.model.UserProfileResponse

data class UserProfileResponseEntity(
    val address: AddressEntity?,
    val age: Int?,
    val bank: BankEntity?,
    val birthDate: String?,
    val bloodGroup: String?,
    val company: CompanyEntity?,
    val crypto: CryptoEntity?,
    val ein: String?,
    val email: String?,
    val eyeColor: String?,
    val firstName: String?,
    val gender: String?,
    val hair: HairEntity?,
    val height: Double?,
    val id: Int?,
    val image: String?,
    val ip: String?,
    val lastName: String?,
    val macAddress: String?,
    val maidenName: String?,
    val password: String?,
    val phone: String?,
    val role: String?,
    val ssn: String?,
    val university: String?,
    val userAgent: String?,
    val username: String?,
    val weight: Double?
)

data class CoordinatesEntity(
    val lat: Double?,
    val lng: Double?
)

data class BankEntity(
    val cardExpire: String?,
    val cardNumber: String?,
    val cardType: String?,
    val currency: String?,
    val iban: String?
)

data class CompanyEntity(
    val address: AddressEntity?,
    val department: String?,
    val name: String?,
    val title: String?
)

data class AddressEntity(
    val address: String?,
    val city: String?,
    val coordinates: CoordinatesEntity?,
    val country: String?,
    val postalCode: String?,
    val state: String?,
    val stateCode: String?
)


data class CryptoEntity(
    val coin: String?,
    val network: String?,
    val wallet: String?
)

data class HairEntity(
    val color: String?,
    val type: String?
)

//Response to Entity Mapper
fun UserProfileResponse.mapUserProfileResponseToUserProfileResponseEntity(): UserProfileResponseEntity {
    return UserProfileResponseEntity(
        address = this.address?.mapAddressToAddressEntity(),
        age = this.age ?: 0,
        bank = this.bank?.mapBankToBankEntity(),
        birthDate = this.birthDate ?: "",
        bloodGroup = this.bloodGroup ?: "",
        company = this.company?.mapCompanyToCompanyEntity(),
        crypto = this.crypto?.mapCryptoToCryptoEntity(),
        ein = this.ein ?: "",
        email = this.email ?: "",
        eyeColor = this.eyeColor ?: "",
        firstName = this.firstName ?: "",
        gender = this.gender ?: "",
        hair = this.hair?.mapHairToHairEntity(),
        height = this.height ?: 0.0,
        id = this.id ?: 0,
        image = this.image ?: "",
        ip = this.ip ?: "",
        lastName = this.lastName ?: "",
        macAddress = this.macAddress ?: "",
        maidenName = this.maidenName ?: "",
        password = this.password ?: "",
        phone = this.phone ?: "",
        role = this.role ?: "",
        ssn = this.ssn ?: "",
        university = this.university ?: "",
        userAgent = this.userAgent ?: "",
        username = this.username ?: "",
        weight = this.weight ?: 0.0
    )


}

fun Address.mapAddressToAddressEntity(): AddressEntity {
    return AddressEntity(
        address = this.address ?: "",
        city = this.city ?: "",
        coordinates = this.coordinates?.mapCoordinatesToCoordinatesEntity(),
        country = this.country ?: "",
        postalCode = this.postalCode ?: "",
        state = this.state ?: "",
        stateCode = this.stateCode ?: ""
    )


}

fun Coordinates.mapCoordinatesToCoordinatesEntity(): CoordinatesEntity {
    return CoordinatesEntity(
        lat = this.lat ?: 0.0,
        lng = this.lng ?: 0.0
    )
}

fun Bank.mapBankToBankEntity(): BankEntity {
    return BankEntity(
        cardExpire = this.cardExpire ?: "",
        cardNumber = this.cardNumber ?: "",
        cardType = this.cardType ?: "",
        currency = this.currency ?: "",
        iban = this.iban ?: ""
    )
}


fun Company.mapCompanyToCompanyEntity(): CompanyEntity {
    return CompanyEntity(
        address = this.address?.mapAddressToAddressEntity(),
        department = this.department ?: "",
        name = this.name ?: "",
        title = this.title ?: ""
    )

}

fun Crypto.mapCryptoToCryptoEntity(): CryptoEntity {
    return CryptoEntity(
        coin = this.coin ?: "",
        network = this.network ?: "",
        wallet = this.wallet ?: ""
    )
}

fun Hair.mapHairToHairEntity(): HairEntity? {
    return HairEntity(
        color = this.color ?: "",
        type = this.type ?: ""
    )
}