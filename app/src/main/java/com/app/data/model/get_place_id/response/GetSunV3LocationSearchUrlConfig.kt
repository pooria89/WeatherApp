package com.app.data.model.get_place_id.response


import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

data class GetSunV3LocationSearchUrlConfig(
    @Expose
    @SerializedName("languageFaIRlocationTypeLocalequery35681133651399406")
    val languageFaIRlocationTypeLocalequery35681133651399406: LanguageFaIRlocationTypeLocalequery35681133651399406,
    val data: Data?,
    val loaded: Boolean?,
    val loading: Boolean?,
    val status: Int?,
    val statusText: String?

)