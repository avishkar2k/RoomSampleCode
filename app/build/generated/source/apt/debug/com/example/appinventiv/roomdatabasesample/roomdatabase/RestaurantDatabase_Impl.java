package com.example.appinventiv.roomdatabasesample.roomdatabase;

import android.arch.persistence.db.SupportSQLiteDatabase;
import android.arch.persistence.db.SupportSQLiteOpenHelper;
import android.arch.persistence.db.SupportSQLiteOpenHelper.Callback;
import android.arch.persistence.db.SupportSQLiteOpenHelper.Configuration;
import android.arch.persistence.room.DatabaseConfiguration;
import android.arch.persistence.room.InvalidationTracker;
import android.arch.persistence.room.RoomOpenHelper;
import android.arch.persistence.room.RoomOpenHelper.Delegate;
import android.arch.persistence.room.util.TableInfo;
import android.arch.persistence.room.util.TableInfo.Column;
import android.arch.persistence.room.util.TableInfo.ForeignKey;
import android.arch.persistence.room.util.TableInfo.Index;
import java.lang.IllegalStateException;
import java.lang.Override;
import java.lang.String;
import java.util.HashMap;
import java.util.HashSet;

public class RestaurantDatabase_Impl extends RestaurantDatabase {
  private volatile RestaurantDao _restaurantDao;

  protected SupportSQLiteOpenHelper createOpenHelper(DatabaseConfiguration configuration) {
    final SupportSQLiteOpenHelper.Callback _openCallback = new RoomOpenHelper(configuration, new RoomOpenHelper.Delegate(1) {
      public void createAllTables(SupportSQLiteDatabase _db) {
        _db.execSQL("CREATE TABLE IF NOT EXISTS `restaurant_table` (`restaurant_name` TEXT NOT NULL, `address` TEXT, `open_hours` TEXT, `ratings` TEXT, `image_url` TEXT, PRIMARY KEY(`restaurant_name`))");
        _db.execSQL("CREATE TABLE IF NOT EXISTS room_master_table (id INTEGER PRIMARY KEY,identity_hash TEXT)");
        _db.execSQL("INSERT OR REPLACE INTO room_master_table (id,identity_hash) VALUES(42, \"e7ca0417dd564e44618452f33240db90\")");
      }

      public void dropAllTables(SupportSQLiteDatabase _db) {
        _db.execSQL("DROP TABLE IF EXISTS `restaurant_table`");
      }

      protected void onCreate(SupportSQLiteDatabase _db) {
        if (mCallbacks != null) {
          for (int _i = 0, _size = mCallbacks.size(); _i < _size; _i++) {
            mCallbacks.get(_i).onCreate(_db);
          }
        }
      }

      public void onOpen(SupportSQLiteDatabase _db) {
        mDatabase = _db;
        internalInitInvalidationTracker(_db);
        if (mCallbacks != null) {
          for (int _i = 0, _size = mCallbacks.size(); _i < _size; _i++) {
            mCallbacks.get(_i).onOpen(_db);
          }
        }
      }

      protected void validateMigration(SupportSQLiteDatabase _db) {
        final HashMap<String, TableInfo.Column> _columnsRestaurantTable = new HashMap<String, TableInfo.Column>(5);
        _columnsRestaurantTable.put("restaurant_name", new TableInfo.Column("restaurant_name", "TEXT", true, 1));
        _columnsRestaurantTable.put("address", new TableInfo.Column("address", "TEXT", false, 0));
        _columnsRestaurantTable.put("open_hours", new TableInfo.Column("open_hours", "TEXT", false, 0));
        _columnsRestaurantTable.put("ratings", new TableInfo.Column("ratings", "TEXT", false, 0));
        _columnsRestaurantTable.put("image_url", new TableInfo.Column("image_url", "TEXT", false, 0));
        final HashSet<TableInfo.ForeignKey> _foreignKeysRestaurantTable = new HashSet<TableInfo.ForeignKey>(0);
        final HashSet<TableInfo.Index> _indicesRestaurantTable = new HashSet<TableInfo.Index>(0);
        final TableInfo _infoRestaurantTable = new TableInfo("restaurant_table", _columnsRestaurantTable, _foreignKeysRestaurantTable, _indicesRestaurantTable);
        final TableInfo _existingRestaurantTable = TableInfo.read(_db, "restaurant_table");
        if (! _infoRestaurantTable.equals(_existingRestaurantTable)) {
          throw new IllegalStateException("Migration didn't properly handle restaurant_table(com.example.appinventiv.roomdatabasesample.roomdatabase.RestaurantEntity).\n"
                  + " Expected:\n" + _infoRestaurantTable + "\n"
                  + " Found:\n" + _existingRestaurantTable);
        }
      }
    }, "e7ca0417dd564e44618452f33240db90");
    final SupportSQLiteOpenHelper.Configuration _sqliteConfig = SupportSQLiteOpenHelper.Configuration.builder(configuration.context)
        .name(configuration.name)
        .callback(_openCallback)
        .build();
    final SupportSQLiteOpenHelper _helper = configuration.sqliteOpenHelperFactory.create(_sqliteConfig);
    return _helper;
  }

  @Override
  protected InvalidationTracker createInvalidationTracker() {
    return new InvalidationTracker(this, "restaurant_table");
  }

  @Override
  public RestaurantDao restaurantDao() {
    if (_restaurantDao != null) {
      return _restaurantDao;
    } else {
      synchronized(this) {
        if(_restaurantDao == null) {
          _restaurantDao = new RestaurantDao_Impl(this);
        }
        return _restaurantDao;
      }
    }
  }
}
