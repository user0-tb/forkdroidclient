package org.fdroid.index.v2

import kotlinx.serialization.Serializable

@Serializable
public data class PackageV2(
    val metadata: MetadataV2,
    val versions: Map<String, PackageVersionV2> = emptyMap(),
) {
    public fun walkFiles(fileConsumer: (FileV2?) -> Unit) {
        metadata.walkFiles(fileConsumer)
        versions.values.forEach { it.walkFiles(fileConsumer) }
    }
}

@Serializable
public data class MetadataV2(
    val name: LocalizedTextV2? = null,
    val summary: LocalizedTextV2? = null,
    val description: LocalizedTextV2? = null,
    val added: Long,
    val lastUpdated: Long,
    val webSite: String? = null,
    val changelog: String? = null,
    val license: String? = null,
    val sourceCode: String? = null,
    val issueTracker: String? = null,
    val translation: String? = null,
    val preferredSigner: String? = null,
    val categories: List<String> = emptyList(),
    val authorName: String? = null,
    val authorEmail: String? = null,
    val authorWebSite: String? = null,
    val authorPhone: String? = null,
    val donate: List<String> = emptyList(),
    val liberapayID: String? = null,
    val liberapay: String? = null,
    val openCollective: String? = null,
    val bitcoin: String? = null,
    val litecoin: String? = null,
    val flattrID: String? = null,
    val icon: LocalizedFileV2? = null,
    val featureGraphic: LocalizedFileV2? = null,
    val promoGraphic: LocalizedFileV2? = null,
    val tvBanner: LocalizedFileV2? = null,
    val video: LocalizedTextV2? = null,
    val screenshots: Screenshots? = null,
) {
    public fun walkFiles(fileConsumer: (FileV2?) -> Unit) {
        icon?.values?.forEach { fileConsumer(it) }
        featureGraphic?.values?.forEach { fileConsumer(it) }
        promoGraphic?.values?.forEach { fileConsumer(it) }
        tvBanner?.values?.forEach { fileConsumer(it) }
        screenshots?.phone?.values?.forEach { it.forEach(fileConsumer) }
        screenshots?.sevenInch?.values?.forEach { it.forEach(fileConsumer) }
        screenshots?.tenInch?.values?.forEach { it.forEach(fileConsumer) }
        screenshots?.wear?.values?.forEach { it.forEach(fileConsumer) }
        screenshots?.tv?.values?.forEach { it.forEach(fileConsumer) }
    }
}

@Serializable
public data class Screenshots(
    val phone: LocalizedFileListV2? = null,
    val sevenInch: LocalizedFileListV2? = null,
    val tenInch: LocalizedFileListV2? = null,
    val wear: LocalizedFileListV2? = null,
    val tv: LocalizedFileListV2? = null,
) {
    val isNull: Boolean
        get() = phone == null && sevenInch == null && tenInch == null && wear == null && tv == null
}

@Serializable
public data class PackageVersionV2(
    val added: Long,
    val file: FileV1,
    val src: FileV2? = null,
    val manifest: ManifestV2,
    val releaseChannels: List<String> = emptyList(),
    val antiFeatures: Map<String, LocalizedTextV2> = emptyMap(),
    val whatsNew: LocalizedTextV2 = emptyMap(),
) {
    public fun walkFiles(fileConsumer: (FileV2?) -> Unit) {
        fileConsumer(src)
    }
}

/**
 * Like [FileV2] with the only difference that the [sha256] hash can not be null.
 * Even in index-v1 this must exist, so we can use it as a primary key in the DB.
 */
@Serializable
public data class FileV1(
    val name: String,
    val sha256: String,
    val size: Long? = null,
)

@Serializable
public data class ManifestV2(
    val versionName: String,
    val versionCode: Long,
    val usesSdk: UsesSdkV2? = null,
    val maxSdkVersion: Int? = null,
    val signer: SignatureV2? = null, // TODO really null?
    val usesPermission: List<PermissionV2> = emptyList(),
    val usesPermissionSdk23: List<PermissionV2> = emptyList(),
    val nativecode: List<String> = emptyList(),
    val features: List<FeatureV2> = emptyList(),
)

@Serializable
public data class UsesSdkV2(
    val minSdkVersion: Int,
    val targetSdkVersion: Int,
)

@Serializable
public data class SignatureV2(
    val sha256: List<String>,
    val hasMultipleSigners: Boolean = false,
)

@Serializable
public data class PermissionV2(
    val name: String,
    val maxSdkVersion: Int? = null,
)

@Serializable
public data class FeatureV2(
    val name: String,
)
