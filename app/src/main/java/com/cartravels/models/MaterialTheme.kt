package com.cartravels.models

import androidx.annotation.StringRes
import androidx.annotation.StyleRes
import com.cartravels.R
import java.io.Serializable

/**
 * Material theme object to be used to display and set themes.
 *
 * @author Steven Byle
 */
class MaterialTheme(@field:StringRes @param:StringRes val nameResId: Int, @field:StyleRes @param:StyleRes val themeResId: Int) : Serializable {

    override fun equals(other: Any?): Boolean {
        if (this === other) {
            return true
        }
        if (other == null || javaClass != other.javaClass) {
            return false
        }
        val that = other as MaterialTheme
        return if (nameResId != that.nameResId) {
            false
        } else themeResId == that.themeResId
    }

    override fun hashCode(): Int {
        var result = nameResId
        result = 31 * result + themeResId
        return result
    }

    companion object {
        // App themes
        val THEME_RED = MaterialTheme(R.string.material_theme_red, R.style.AppTheme_Red)
        val THEME_ORANGE = MaterialTheme(R.string.material_theme_orange, R.style.AppTheme_Orange)
        val THEME_YELLOW = MaterialTheme(R.string.material_theme_lime, R.style.AppTheme_Lime)
        val THEME_GREEN = MaterialTheme(R.string.material_theme_green, R.style.AppTheme_Green)
        val THEME_TEAL = MaterialTheme(R.string.material_theme_teal, R.style.AppTheme_Teal)
        val THEME_BLUE = MaterialTheme(R.string.material_theme_blue, R.style.AppTheme_Blue)
        val THEME_PURPLE = MaterialTheme(R.string.material_theme_purple, R.style.AppTheme_Purple)
        // Dialog themes
        val THEME_DIALOG_RED = MaterialTheme(R.string.material_theme_red, R.style.AppTheme_Dialog_Red)
        val THEME_DIALOG_ORANGE = MaterialTheme(R.string.material_theme_orange, R.style.AppTheme_Dialog_Orange)
        val THEME_DIALOG_YELLOW = MaterialTheme(R.string.material_theme_lime, R.style.AppTheme_Dialog_Lime)
        val THEME_DIALOG_GREEN = MaterialTheme(R.string.material_theme_green, R.style.AppTheme_Dialog_Green)
        val THEME_DIALOG_TEAL = MaterialTheme(R.string.material_theme_teal, R.style.AppTheme_Dialog_Teal)
        val THEME_DIALOG_BLUE = MaterialTheme(R.string.material_theme_blue, R.style.AppTheme_Dialog_Blue)
        val THEME_DIALOG_PURPLE = MaterialTheme(R.string.material_theme_purple, R.style.AppTheme_Dialog_Purple)
        // Alert dialog themes
        val THEME_ALERT_DIALOG_RED = MaterialTheme(R.string.material_theme_red, R.style.AppTheme_Dialog_Alert_Red)
        val THEME_ALERT_DIALOG_ORANGE = MaterialTheme(R.string.material_theme_orange, R.style.AppTheme_Dialog_Alert_Orange)
        val THEME_ALERT_DIALOG_YELLOW = MaterialTheme(R.string.material_theme_lime, R.style.AppTheme_Dialog_Alert_Lime)
        @JvmField
        val THEME_ALERT_DIALOG_GREEN = MaterialTheme(R.string.material_theme_green, R.style.AppTheme_Dialog_Alert_Green)
        val THEME_ALERT_DIALOG_TEAL = MaterialTheme(R.string.material_theme_teal, R.style.AppTheme_Dialog_Alert_Teal)
        @JvmField
        val THEME_ALERT_DIALOG_BLUE = MaterialTheme(R.string.material_theme_blue, R.style.AppTheme_Dialog_Alert_Blue)
        val THEME_ALERT_DIALOG_PURPLE = MaterialTheme(R.string.material_theme_purple, R.style.AppTheme_Dialog_Alert_Purple)
        private var sThemeList = mutableListOf<MaterialTheme>()
        private var sDialogThemeList = mutableListOf<MaterialTheme>()
        private var sAlertDialogThemeList = mutableListOf<MaterialTheme>()
        val themeList: List<MaterialTheme>?
            get() {
                if (sThemeList.isNullOrEmpty()) {
                    sThemeList = mutableListOf()
                    sThemeList.add(THEME_RED)
                    sThemeList.add(THEME_ORANGE)
                    sThemeList.add(THEME_YELLOW)
                    sThemeList.add(THEME_GREEN)
                    sThemeList.add(THEME_TEAL)
                    sThemeList.add(THEME_BLUE)
                    sThemeList.add(THEME_PURPLE)
                }
                return sThemeList
            }

        val dialogThemeList: List<MaterialTheme>?
            get() {
                if (sDialogThemeList.isNullOrEmpty()) {
                    sDialogThemeList = mutableListOf<MaterialTheme>()
                    sDialogThemeList.add(THEME_DIALOG_RED)
                    sDialogThemeList.add(THEME_DIALOG_ORANGE)
                    sDialogThemeList.add(THEME_DIALOG_YELLOW)
                    sDialogThemeList.add(THEME_DIALOG_GREEN)
                    sDialogThemeList.add(THEME_DIALOG_TEAL)
                    sDialogThemeList.add(THEME_DIALOG_BLUE)
                    sDialogThemeList.add(THEME_DIALOG_PURPLE)
                }
                return sDialogThemeList
            }

        val alertDialogThemeList: List<MaterialTheme>?
            get() {
                if (sAlertDialogThemeList.isNullOrEmpty()) {
                    sAlertDialogThemeList = mutableListOf<MaterialTheme>()
                    sAlertDialogThemeList.add(THEME_ALERT_DIALOG_RED)
                    sAlertDialogThemeList.add(THEME_ALERT_DIALOG_ORANGE)
                    sAlertDialogThemeList.add(THEME_ALERT_DIALOG_YELLOW)
                    sAlertDialogThemeList.add(THEME_ALERT_DIALOG_GREEN)
                    sAlertDialogThemeList.add(THEME_ALERT_DIALOG_TEAL)
                    sAlertDialogThemeList.add(THEME_ALERT_DIALOG_BLUE)
                    sAlertDialogThemeList.add(THEME_ALERT_DIALOG_PURPLE)
                }
                return sAlertDialogThemeList
            }
    }

}