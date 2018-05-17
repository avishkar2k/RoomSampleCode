package com.example.appinventiv.roomdatabasesample.roomdatabase;

import android.arch.lifecycle.ComputableLiveData;
import android.arch.lifecycle.LiveData;
import android.arch.persistence.db.SupportSQLiteStatement;
import android.arch.persistence.room.EntityDeletionOrUpdateAdapter;
import android.arch.persistence.room.EntityInsertionAdapter;
import android.arch.persistence.room.InvalidationTracker.Observer;
import android.arch.persistence.room.RoomDatabase;
import android.arch.persistence.room.RoomSQLiteQuery;
import android.arch.persistence.room.SharedSQLiteStatement;
import android.database.Cursor;
import android.support.annotation.NonNull;
import java.lang.Override;
import java.lang.String;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

public class RestaurantDao_Impl implements RestaurantDao {
  private final RoomDatabase __db;

  private final EntityInsertionAdapter __insertionAdapterOfRestaurantEntity;

  private final EntityDeletionOrUpdateAdapter __updateAdapterOfRestaurantEntity;

  private final SharedSQLiteStatement __preparedStmtOfDeleteAll;

  private final SharedSQLiteStatement __preparedStmtOfDelete;

  public RestaurantDao_Impl(RoomDatabase __db) {
    this.__db = __db;
    this.__insertionAdapterOfRestaurantEntity = new EntityInsertionAdapter<RestaurantEntity>(__db) {
      @Override
      public String createQuery() {
        return "INSERT OR REPLACE INTO `restaurant_table`(`restaurant_name`,`address`,`open_hours`,`ratings`,`image_url`) VALUES (?,?,?,?,?)";
      }

      @Override
      public void bind(SupportSQLiteStatement stmt, RestaurantEntity value) {
        if (value.getRestaurantName() == null) {
          stmt.bindNull(1);
        } else {
          stmt.bindString(1, value.getRestaurantName());
        }
        if (value.getLocationAddress() == null) {
          stmt.bindNull(2);
        } else {
          stmt.bindString(2, value.getLocationAddress());
        }
        if (value.getOpeningHours() == null) {
          stmt.bindNull(3);
        } else {
          stmt.bindString(3, value.getOpeningHours());
        }
        if (value.getRatings() == null) {
          stmt.bindNull(4);
        } else {
          stmt.bindString(4, value.getRatings());
        }
        if (value.getImageUrl() == null) {
          stmt.bindNull(5);
        } else {
          stmt.bindString(5, value.getImageUrl());
        }
      }
    };
    this.__updateAdapterOfRestaurantEntity = new EntityDeletionOrUpdateAdapter<RestaurantEntity>(__db) {
      @Override
      public String createQuery() {
        return "UPDATE OR ABORT `restaurant_table` SET `restaurant_name` = ?,`address` = ?,`open_hours` = ?,`ratings` = ?,`image_url` = ? WHERE `restaurant_name` = ?";
      }

      @Override
      public void bind(SupportSQLiteStatement stmt, RestaurantEntity value) {
        if (value.getRestaurantName() == null) {
          stmt.bindNull(1);
        } else {
          stmt.bindString(1, value.getRestaurantName());
        }
        if (value.getLocationAddress() == null) {
          stmt.bindNull(2);
        } else {
          stmt.bindString(2, value.getLocationAddress());
        }
        if (value.getOpeningHours() == null) {
          stmt.bindNull(3);
        } else {
          stmt.bindString(3, value.getOpeningHours());
        }
        if (value.getRatings() == null) {
          stmt.bindNull(4);
        } else {
          stmt.bindString(4, value.getRatings());
        }
        if (value.getImageUrl() == null) {
          stmt.bindNull(5);
        } else {
          stmt.bindString(5, value.getImageUrl());
        }
        if (value.getRestaurantName() == null) {
          stmt.bindNull(6);
        } else {
          stmt.bindString(6, value.getRestaurantName());
        }
      }
    };
    this.__preparedStmtOfDeleteAll = new SharedSQLiteStatement(__db) {
      @Override
      public String createQuery() {
        final String _query = "DELETE FROM restaurant_table";
        return _query;
      }
    };
    this.__preparedStmtOfDelete = new SharedSQLiteStatement(__db) {
      @Override
      public String createQuery() {
        final String _query = "DELETE FROM restaurant_table WHERE restaurant_name = ?";
        return _query;
      }
    };
  }

  @Override
  public void insert(RestaurantEntity... restaurantEntity) {
    __db.beginTransaction();
    try {
      __insertionAdapterOfRestaurantEntity.insert(restaurantEntity);
      __db.setTransactionSuccessful();
    } finally {
      __db.endTransaction();
    }
  }

  @Override
  public void insertList(List<RestaurantEntity> restaurantEntityList) {
    __db.beginTransaction();
    try {
      __insertionAdapterOfRestaurantEntity.insert(restaurantEntityList);
      __db.setTransactionSuccessful();
    } finally {
      __db.endTransaction();
    }
  }

  @Override
  public void updateRestaurantEntities(RestaurantEntity... restaurantEntities) {
    __db.beginTransaction();
    try {
      __updateAdapterOfRestaurantEntity.handleMultiple(restaurantEntities);
      __db.setTransactionSuccessful();
    } finally {
      __db.endTransaction();
    }
  }

  @Override
  public void updateRestaurantLists(List<RestaurantEntity> restaurantEntityList) {
    __db.beginTransaction();
    try {
      __updateAdapterOfRestaurantEntity.handleMultiple(restaurantEntityList);
      __db.setTransactionSuccessful();
    } finally {
      __db.endTransaction();
    }
  }

  @Override
  public void deleteAll() {
    final SupportSQLiteStatement _stmt = __preparedStmtOfDeleteAll.acquire();
    __db.beginTransaction();
    try {
      _stmt.executeUpdateDelete();
      __db.setTransactionSuccessful();
    } finally {
      __db.endTransaction();
      __preparedStmtOfDeleteAll.release(_stmt);
    }
  }

  @Override
  public void delete(String restaurantName) {
    final SupportSQLiteStatement _stmt = __preparedStmtOfDelete.acquire();
    __db.beginTransaction();
    try {
      int _argIndex = 1;
      if (restaurantName == null) {
        _stmt.bindNull(_argIndex);
      } else {
        _stmt.bindString(_argIndex, restaurantName);
      }
      _stmt.executeUpdateDelete();
      __db.setTransactionSuccessful();
    } finally {
      __db.endTransaction();
      __preparedStmtOfDelete.release(_stmt);
    }
  }

  @Override
  public RestaurantEntity getAllRestaurants(String restaurantName) {
    final String _sql = "SELECT * FROM restaurant_table WHERE restaurant_name = ?";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 1);
    int _argIndex = 1;
    if (restaurantName == null) {
      _statement.bindNull(_argIndex);
    } else {
      _statement.bindString(_argIndex, restaurantName);
    }
    final Cursor _cursor = __db.query(_statement);
    try {
      final int _cursorIndexOfRestaurantName = _cursor.getColumnIndexOrThrow("restaurant_name");
      final int _cursorIndexOfLocationAddress = _cursor.getColumnIndexOrThrow("address");
      final int _cursorIndexOfOpeningHours = _cursor.getColumnIndexOrThrow("open_hours");
      final int _cursorIndexOfRatings = _cursor.getColumnIndexOrThrow("ratings");
      final int _cursorIndexOfImageUrl = _cursor.getColumnIndexOrThrow("image_url");
      final RestaurantEntity _result;
      if(_cursor.moveToFirst()) {
        final String _tmpRestaurantName;
        _tmpRestaurantName = _cursor.getString(_cursorIndexOfRestaurantName);
        final String _tmpLocationAddress;
        _tmpLocationAddress = _cursor.getString(_cursorIndexOfLocationAddress);
        final String _tmpOpeningHours;
        _tmpOpeningHours = _cursor.getString(_cursorIndexOfOpeningHours);
        final String _tmpRatings;
        _tmpRatings = _cursor.getString(_cursorIndexOfRatings);
        final String _tmpImageUrl;
        _tmpImageUrl = _cursor.getString(_cursorIndexOfImageUrl);
        _result = new RestaurantEntity(_tmpRestaurantName,_tmpLocationAddress,_tmpOpeningHours,_tmpRatings,_tmpImageUrl);
      } else {
        _result = null;
      }
      return _result;
    } finally {
      _cursor.close();
      _statement.release();
    }
  }

  @Override
  public LiveData<List<RestaurantEntity>> getAllRestaurants() {
    final String _sql = "SELECT * FROM restaurant_table";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 0);
    return new ComputableLiveData<List<RestaurantEntity>>() {
      private Observer _observer;

      @Override
      protected List<RestaurantEntity> compute() {
        if (_observer == null) {
          _observer = new Observer("restaurant_table") {
            @Override
            public void onInvalidated(@NonNull Set<String> tables) {
              invalidate();
            }
          };
          __db.getInvalidationTracker().addWeakObserver(_observer);
        }
        final Cursor _cursor = __db.query(_statement);
        try {
          final int _cursorIndexOfRestaurantName = _cursor.getColumnIndexOrThrow("restaurant_name");
          final int _cursorIndexOfLocationAddress = _cursor.getColumnIndexOrThrow("address");
          final int _cursorIndexOfOpeningHours = _cursor.getColumnIndexOrThrow("open_hours");
          final int _cursorIndexOfRatings = _cursor.getColumnIndexOrThrow("ratings");
          final int _cursorIndexOfImageUrl = _cursor.getColumnIndexOrThrow("image_url");
          final List<RestaurantEntity> _result = new ArrayList<RestaurantEntity>(_cursor.getCount());
          while(_cursor.moveToNext()) {
            final RestaurantEntity _item;
            final String _tmpRestaurantName;
            _tmpRestaurantName = _cursor.getString(_cursorIndexOfRestaurantName);
            final String _tmpLocationAddress;
            _tmpLocationAddress = _cursor.getString(_cursorIndexOfLocationAddress);
            final String _tmpOpeningHours;
            _tmpOpeningHours = _cursor.getString(_cursorIndexOfOpeningHours);
            final String _tmpRatings;
            _tmpRatings = _cursor.getString(_cursorIndexOfRatings);
            final String _tmpImageUrl;
            _tmpImageUrl = _cursor.getString(_cursorIndexOfImageUrl);
            _item = new RestaurantEntity(_tmpRestaurantName,_tmpLocationAddress,_tmpOpeningHours,_tmpRatings,_tmpImageUrl);
            _result.add(_item);
          }
          return _result;
        } finally {
          _cursor.close();
        }
      }

      @Override
      protected void finalize() {
        _statement.release();
      }
    }.getLiveData();
  }
}
