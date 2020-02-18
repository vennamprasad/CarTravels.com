package com.cartravels.adapters

import android.content.Context
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter
import com.cartravels.R
import com.cartravels.screens.fragments.MaterialThemeInCodeFragment
import com.cartravels.screens.fragments.MaterialThemeInXmlFragment
import java.lang.ref.WeakReference

/**
 * View pager adapter used on home page to show themed fragments.
 *
 * @author Steven Byle
 */
class HomePagerAdapter(context: Context, fm: FragmentManager?) : FragmentPagerAdapter(fm!!) {
    private val mContextRef: WeakReference<Context>
    override fun getCount(): Int {
        return HomePage.values().size
    }

    override fun getItem(position: Int): Fragment {
        val homePage = getWhichHomePage(position)
        return when (homePage) {
            HomePage.THEME_IN_XML -> MaterialThemeInXmlFragment.newInstance()
            HomePage.THEME_IN_CODE -> MaterialThemeInCodeFragment.newInstance()
        }
    }

    override fun getPageTitle(position: Int): CharSequence? {
        val homePage = getWhichHomePage(position)
        val context = mContextRef.get()
        return if (context != null) {
            when (homePage) {
                HomePage.THEME_IN_XML -> context.getString(R.string.home_tab_xml)
                HomePage.THEME_IN_CODE -> context.getString(R.string.home_tab_code)
            }
        } else null
    }

    private fun getWhichHomePage(position: Int): HomePage {
        return HomePage.values()[position]
    }

    private enum class HomePage {
        THEME_IN_XML, THEME_IN_CODE
    }

    init {
        mContextRef = WeakReference(context)
    }
}