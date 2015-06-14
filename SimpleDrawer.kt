package com.namh.drawer

import android.content.Context
import android.content.res.Resources
import android.support.v4.app.Fragment
import android.support.v4.widget.DrawerLayout
import android.support.v7.app.ActionBar
import android.support.v7.app.ActionBarActivity
import android.support.v7.app.ActionBarDrawerToggle
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.support.v7.widget.Toolbar
import android.util.AttributeSet
import android.util.Log
import android.view.View
import android.view.ViewManager
import android.widget.ImageView
import android.widget.TextView
import com.namh.drawer.SimpleDrawer
import org.jetbrains.anko.__dslAddView

import com.namh.successhunch.R
import com.namh.successhunch.podlist.view.PodListFragment
import java.util.*
import kotlin.properties.Delegates

/**
 * Created by namh on 2015-06-14.
 */
fun ViewManager.simpleDrawer2(init: SimpleDrawer2.() -> Unit = {}) =
        __dslAddView({ SimpleDrawer2(it) }, init, this)



public class SimpleDrawer2:DrawerLayout, View.OnClickListener {
    private val USE_ACTION_BAR = true

    private var mMenuDrawer:MenuDrawer by Delegates.notNull()

    private var mDrawerToggle: ActionBarDrawerToggle by Delegates.notNull()
    private val mActionBar: ActionBar by Delegates.notNull()
    private val mContentFrameId = R.id.content_frame
    private var mNavMenuTitles:Array<String> by Delegates.notNull()
    private var mFragment: Fragment by Delegates.notNull()
    private var mToolbar: Toolbar by Delegates.notNull()

    private var mActivity: ActionBarActivity by Delegates.notNull()

    public constructor(context: Context) : super(context) {
        init(context, null, 0)
    }
    public constructor(context:Context, attrs: AttributeSet) : super(context, attrs) {
        init(context, attrs, 0)
    }
    public constructor(context:Context, attrs:AttributeSet, defStyle:Int) : super(context, attrs, defStyle) {
        init(context, attrs, defStyle)
    }


    private fun init(context:Context, attrs:AttributeSet?, defStyle:Int) {
        mActivity = context as ActionBarActivity
    }
    private fun setHomeIconPaddingLeftRight(iv: ImageView, padding:Int) {
        val scale = getResources().getDisplayMetrics().density
        val paddingDp = (padding * scale + 0.5f).toInt()
        iv.setPadding(paddingDp, 0, paddingDp, 0)
    }
    private fun createActionBarDrawerToggle
            (mActivity:ActionBarActivity, actionBar:ActionBar, toolbar:Toolbar):ActionBarDrawerToggle {
        val drawerToggle = object:ActionBarDrawerToggle(mActivity,
                this,
                toolbar,
                R.string.app_name, // nav drawer open - description for accessibility
                R.string.app_name) {
            public override fun onDrawerClosed(view:View) {
                toolbar.setTitle(mActivity.getTitle())
                // calling onPrepareOptionsMenu() to show action bar icons
                mActivity.invalidateOptionsMenu()
            }
            public override fun onDrawerOpened(drawerView:View) {
                toolbar.setTitle(mActivity.getTitle())
                // calling onPrepareOptionsMenu() to hide action bar icons
                mActivity.invalidateOptionsMenu()
                // mMenuDrawer.refreshLikeCount();
            }
        }
        return drawerToggle
    }


    protected override fun onFinishInflate() {
        super<DrawerLayout>.onFinishInflate()
        /**
         * Notice
         */
        if (USE_ACTION_BAR)
        {
            // Toolbar
            mToolbar = this.findViewById(R.id.tlbr_main) as Toolbar
            mActivity.setSupportActionBar(mToolbar)
            // mToolbar.setTitle() requires API-21
            mDrawerToggle = createActionBarDrawerToggle(mActivity, mActionBar, mToolbar)
            this.setDrawerListener(mDrawerToggle)
        }
        mNavMenuTitles = getResources().getStringArray(R.array.nav_drawer_items)
        // setting the nav drawer list adapter
        val adapter = NavDrawerListAdapter(this.getContext(), getDefaultDrawerMenuList(getResources()), this)
        mMenuDrawer = this.findViewById(R.id.list_slidermenu) as MenuDrawer
        if(mMenuDrawer != null){
            mMenuDrawer.setHasFixedSize(true) // Letting the system know that the list objects are of fixed size
            // And passing the titles,icons,header view name, header view email,
            // and header view profile picture
            mMenuDrawer.setAdapter(adapter) // Setting the adapter to RecyclerView
            mMenuDrawer.setLayoutManager(LinearLayoutManager(getContext())) // Creating and Setting the layout Manager
            mMenuDrawer.setAdapter(adapter)
        }

    }


    private fun getDefaultDrawerMenuList(resources: Resources): ArrayList<NavDrawerItem> {
        val menuList = ArrayList<NavDrawerItem>()
        val navMenuTitles = resources.getStringArray(R.array.nav_drawer_items)
        val navMenuIcons = resources.obtainTypedArray(R.array.nav_drawer_icons)
        val navMenuIconsInverse = resources.obtainTypedArray(R.array.nav_drawer_icons_inverse)
        val max = navMenuTitles.size()
        for (i in 0..max - 1)
        {
            val title = navMenuTitles[i]
            val drawerItem = NavDrawerItem(title, navMenuIcons.getResourceId(i, -1), navMenuIconsInverse.getResourceId(i, -1))
            menuList.add(drawerItem)
        }
        // Recycle the typed array
        navMenuIcons.recycle()
        return menuList
    }
    public fun getDrawerToggle():ActionBarDrawerToggle {
        return mDrawerToggle
    }
    public fun getMenuDrawer(): RecyclerView {
        return mMenuDrawer
    }
    public override fun onClick(view:View) {
        // display view for selected nav drawer item
        val text = (view.findViewById(R.id.title) as TextView).getText()
        val len = mNavMenuTitles.size()
        var i = 0
        while (i < len)
        {
            if (text == mNavMenuTitles[i])
            {
                processClick(i)
                return
            }
            i++
        }
    }
    public fun processClick(position:Int) {
        // update the main content by replacing fragments
        when (position) {
            0 -> if (!(mFragment is PodListFragment))
            {
                setUpSelectedFragment(position, PodListFragment())
            }


//            1 -> if (!(mFragment is Tube2MainFragment))
//            {
//                setUpSelectedFragment(position, Tube2MainFragment())
//            }
//            2 -> if (!(mFragment is FrCocChannelMainFragment))
//            {
//                setUpSelectedFragment(position, FrCocChannelMainFragment())
//            }
//            2 -> if (!(mFragment is GermanCocChannelMainFragment))
//            {
//                setUpSelectedFragment(position, GermanCocChannelMainFragment())
//            }
//            4 -> if (!(mFragment is KrCocChannelMainFragment))
//            {
//                setUpSelectedFragment(position, KrCocChannelMainFragment())
//            }
//            5 -> if (!(mFragment is ProtatoMainFragment))
//            {
//                setUpSelectedFragment(position, ProtatoMainFragment())
//            }
//            5 -> if (!(mFragment is MyListPrefFragment))
//            {
//                setUpSelectedFragment(position, MyListPrefFragment())
//            }
//            3 -> if (!(mFragment is RecentVideoListFragment))
//            {
//                setUpSelectedFragment(position, RecentVideoListFragment())
//            }
//            4 // LIKE_MENU_ITEM_INDEX {@link MenuDrawer.refreshLikeCount}
//            -> if (!(mFragment is LikeVideoListFragment))
//            {
//                setUpSelectedFragment(position, LikeVideoListFragment())
//            }
//            5 -> {
//                val emailIntent = IntentUtils.getIntentComposeEmail(mActivity.getString(R.string.report_email_address), mActivity.getString(R.string.sending_a_memo_using))
//                mActivity.startActivity(emailIntent)
//                mMenuDrawer.setSelectionWithPrevious()
//                closeDrawer(mMenuDrawer)
//            }
//            4 -> // show selection view
//                if (!(mFragment is VideoPlayListFragment))
//                {
//                    setUpSelectedFragment(position, VideoPlayListFragment())
//                }
            else ->{}

        }
    }
    private fun setUpSelectedFragment(position:Int, fragment:Fragment) {
        if (fragment == null)
        {
            Log.e("SimpleDrawer", "Error in creating fragment")
            return
        }
        mActivity.setTitle(mNavMenuTitles[position])
        replaceFragment(fragment)
        mMenuDrawer.setAdapterSelect(position)
        closeDrawer(mMenuDrawer)
    }
    private fun replaceFragment(newFragment:Fragment) {
        mFragment = newFragment
        val fragmentManager = mActivity.getSupportFragmentManager()
        fragmentManager.beginTransaction().replace(mContentFrameId, newFragment).commit()
    }
    public fun setInitFragment(position:Int) {
        // update the main content by replacing fragments
        when (position) {
            0 -> mFragment = PodListFragment()
        // case 1:
        // mFragment = new LikeVideoListFragment();
        // break;
            else -> {}
        }
        if (mFragment == null)
        {
            // error in creating fragment
            Log.e("SimpleDrawer", "Error in creating fragment")
        }
        replaceFragment(mFragment)
        mMenuDrawer.setItemCheckedAndSelect(position)
        closeDrawer(mMenuDrawer)
    }

}