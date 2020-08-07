package com.example.githubusersapp.view.widget

import android.content.Context
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.os.Binder
import android.widget.RemoteViews
import android.widget.RemoteViewsService
import androidx.core.os.bundleOf
import com.example.githubusersapp.R
import com.example.githubusersapp.data.database.FavoriteDatabase
import com.example.githubusersapp.helper.MappingHelper
import com.example.githubusersapp.model.User
import java.io.IOException
import java.net.URL


internal class StackRemoteViewsFactory(private val mContext: Context) :
    RemoteViewsService.RemoteViewsFactory {
    private var mWidgetItems = ArrayList<User>()

    override fun onCreate() {

    }

    override fun onDataSetChanged() {
        val identityToken = Binder.clearCallingIdentity()
        val appDatabase = FavoriteDatabase.init(mContext)
        val users = appDatabase.userFavoriteDao().getAllCursor()
        mWidgetItems = MappingHelper.mapCursorToArrayList(users)
        Binder.restoreCallingIdentity(identityToken)
    }

    override fun onDestroy() {

    }

    override fun getCount(): Int = mWidgetItems.size

    override fun getViewAt(position: Int): RemoteViews {
        val rv = RemoteViews(mContext.packageName, R.layout.widget_item)
        val bitmap = getBitmapFromUrl(mWidgetItems[position].avatar_url.toString())
        rv.setImageViewBitmap(R.id.iv_user_avatar, bitmap)
        rv.setTextViewText(R.id.tv_user_login, mWidgetItems[position].login)

        val extras = bundleOf(
            FavoriteStackWidget.EXTRA_ITEM to mWidgetItems[position].name
        )
        val fillInIntent = Intent()
        fillInIntent.putExtras(extras)

        rv.setOnClickFillInIntent(R.id.iv_user_avatar, fillInIntent)
        rv.setOnClickFillInIntent(R.id.tv_user_login, fillInIntent)
        return rv
    }

    override fun getLoadingView(): RemoteViews? = null

    override fun getViewTypeCount(): Int = 1

    override fun getItemId(i: Int): Long = 0

    override fun hasStableIds(): Boolean = false

    private fun getBitmapFromUrl(src: String) : Bitmap?{
        return try {
            val url = URL(src)
            BitmapFactory.decodeStream(url.openConnection().getInputStream())
        } catch (e: IOException) {
            null
        }
    }
}