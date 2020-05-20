package kg.geektech.taskapprestored;


import android.app.Application;

import androidx.room.Room;

import kg.geektech.taskapprestored.room.AppDatabase;

import static androidx.room.Room.databaseBuilder;

public class App extends Application {

    private AppDatabase database;
    public static App instance;

    @Override
    public void onCreate() {
        super.onCreate();
        instance = this;
        database = Room.databaseBuilder(this,AppDatabase.class,"database").allowMainThreadQueries().build();
    }

    public static App getInstance() {
        return instance;
    }

    public AppDatabase getDatabase() {
        return database;
    }
}