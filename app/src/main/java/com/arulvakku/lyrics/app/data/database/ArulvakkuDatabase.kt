package com.arulvakku.lyrics.app.data.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.arulvakku.lyrics.app.ui.view.home.category.cache.SongCategoryCacheEntity
import com.arulvakku.lyrics.app.ui.view.home.category.cache.SongCategoryDao
import com.arulvakku.lyrics.app.ui.view.home.song.cache.SongCacheEntity
import com.arulvakku.lyrics.app.ui.view.home.song.cache.SongDao
import com.arulvakku.lyrics.app.ui.view.favourite.cache.Playlist
import com.arulvakku.lyrics.app.ui.view.favourite.cache.PlaylistSongCrossRef


@Database(
    entities = arrayOf(
        SongCategoryCacheEntity::class,
        SongCacheEntity::class,
        PlaylistSongCrossRef::class,
        Playlist::class
    ), version = 1, exportSchema = false
)
abstract class ArulvakkuDatabase : RoomDatabase() {
    abstract fun songCategoryDao(): SongCategoryDao
    abstract fun songDao(): SongDao
}