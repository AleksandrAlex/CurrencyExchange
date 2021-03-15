package ru.suslovalex.exchangerate.model


import com.google.gson.annotations.SerializedName
import ru.suslovalex.exchangerate.model.valutes.*

data class Valute(
    @SerializedName("AUD")
    val aUD: AUD,
    @SerializedName("AZN")
    val aZN: AZN,
    @SerializedName("GBP")
    val gBP: GBP,
    @SerializedName("AMD")
    val aMD: AMD,
    @SerializedName("BYN")
    val bYN: BYN,
    @SerializedName("BGN")
    val bGN: BGN,
    @SerializedName("BRL")
    val bRL: BRL,
    @SerializedName("HUF")
    val hUF: HUF,
    @SerializedName("HKD")
    val hKD: HKD,
    @SerializedName("DKK")
    val dKK: DKK,
    @SerializedName("USD")
    val uSD: USD,
    @SerializedName("EUR")
    val eUR: EUR,
    @SerializedName("INR")
    val iNR: INR,
    @SerializedName("KZT")
    val kZT: KZT,
    @SerializedName("CAD")
    val cAD: CAD,
    @SerializedName("KGS")
    val kGS: KGS,
    @SerializedName("CNY")
    val cNY: CNY,
    @SerializedName("MDL")
    val mDL: MDL,
    @SerializedName("NOK")
    val nOK: NOK,
    @SerializedName("PLN")
    val pLN: PLN,
    @SerializedName("RON")
    val rON: RON,
    @SerializedName("XDR")
    val xDR: XDR,
    @SerializedName("SGD")
    val sGD: SGD,
    @SerializedName("TJS")
    val tJS: TJS,
    @SerializedName("TRY")
    val tRY: TRY,
    @SerializedName("TMT")
    val tMT: TMT,
    @SerializedName("UZS")
    val uZS: UZS,
    @SerializedName("UAH")
    val uAH: UAH,
    @SerializedName("CZK")
    val cZK: CZK,
    @SerializedName("SEK")
    val sEK: SEK,
    @SerializedName("CHF")
    val cHF: CHF,
    @SerializedName("ZAR")
    val zAR: ZAR,
    @SerializedName("KRW")
    val kRW: KRW,
    @SerializedName("JPY")
    val jPY: JPY
)