package org.vbm.kittyware;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.widget.Toast;

/**
 * Created by vbm on 25/03/15.
 */
public class DatabaseAccess {

    public Cursor cursor;
    SQLiteDatabase dBase;
    String name;

    public DatabaseAccess(Context context) {
        this.name = "shoplist";
        createDataBase(context);
    }

    public boolean createDataBase(Context context) {
        try {
            dBase = context.openOrCreateDatabase("kitties", Context.MODE_PRIVATE, null);

   /* Create a Table in the Database. */
            dBase.execSQL("CREATE TABLE IF NOT EXISTS "
                    + "species"
                    + " ( _id INT(6), name VARCHAR, breed VARCHAR, desc VARCHAR, imgurl VARCHAR, phone VARCHAR, lattitude REAL, longtitude REAL );");

   /* Insert data to a Table*/
            dBase.execSQL("delete from species");
            dBase.execSQL("INSERT OR REPLACE INTO "
                    + "species"
                    + " (_id, name, breed, desc, imgurl, phone, lattitude, longtitude)"
                    + " VALUES ( '1', 'Tiger Woods', 'Siberian blue', 'Can be taken in two weeks', 'http://img.webmd.com/dtmcms/live/webmd/consumer_assets/site_images/articles/health_tools/taking_care_of_kitten_slideshow/photolibrary_rm_photo_of_woman_snuggling_kitten.jpg', '0000000000', '38.900232', '-77.010376'),"
                    + " ( '2', 'Linda', 'Someone', 'Can be taken in two weeks', 'http://img.webmd.com/dtmcms/live/webmd/consumer_assets/site_images/articles/health_tools/taking_care_of_kitten_slideshow/photolibrary_rm_photo_of_woman_snuggling_kitten.jpg', '0000000000', '38.870232', '-77.010376')"
                    );
            initCursor();
            return true;
        } catch (Exception e) {
            Toast.makeText(context, "Ошибка операции с базами данных\n" + e.toString(), Toast.LENGTH_LONG).show();
            return false;
        }
    }


    public void clearTable(String tableName) {
        dBase.execSQL("DELETE from " + tableName);
    }

    public void initCursor() {
        cursor = dBase.rawQuery("SELECT * from species", null);
    }
}