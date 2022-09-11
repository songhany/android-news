package com.songhany.news.database;

import androidx.room.Database;
import androidx.room.RoomDatabase;

import com.songhany.news.model.Article;

// "version" specifies a current version. Once we introduce/modify the new version, we have to increase the version and define the migration strategy.
// "entities" specifies the tables the database contains.
// "exportSchema" option is for dumping a database schema to file system. We do not need that.
@Database(entities = {Article.class}, version = 1, exportSchema = false)
public abstract class TinNewsDatabase extends RoomDatabase {

    public abstract ArticleDao articleDao();  //  We do not implement it, but the Room annotation processor will.
}
