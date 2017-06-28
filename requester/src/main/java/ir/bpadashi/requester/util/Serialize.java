package ir.bpadashi.requester.util;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Environment;
import ir.bpadashi.requester.database.DatabaseObj;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;


public class Serialize {

    public  final String TABLE = "ser";

    //private Serialize() {
    //}

   // private static class SingletonHelper {
       // private static final Serialize INSTANCE = new Serialize();
    //}

    //public static Serialize getInstance() {
        //return SingletonHelper.INSTANCE;
    //}

   /* private static Object mutex = new Object();

    private static Serialize aSerialize = null;

    private Serialize() {
    }

    public static Serialize getInstance() {
        if (aSerialize == null)
            synchronized (mutex) {
                if (aSerialize == null) {
                    aSerialize = new Serialize();
                } else {
                    return aSerialize;
                }

            }
        return aSerialize;
    }*/



    public void saveToFile(final Context context, final Object obj, final String name) {

        //System.out.println("->TRY SAVE");

        if (context == null)
            return;


        String fileName = ReturnPath(context) + "/" + name + ".dat";

        try {
            ByteArrayOutputStream fileOutputStream = new ByteArrayOutputStream();
            ObjectOutputStream objectOutputStream = new ObjectOutputStream(fileOutputStream);
            objectOutputStream.writeObject(obj);
            objectOutputStream.close();


            insert(context,name, fileOutputStream.toByteArray());


            fileOutputStream.close();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }


    }

    // Creates an object by reading it from a file
    public  Object readFromFile(Context context, String name) {

        if (context == null)
            return null;


        String fileName = ReturnPath(context) + "/" + name + ".dat";

        Object createResumeForm = null;
        try {

            byte[] bytes = get(context,name);

            if (bytes == null)
                return null;

            ByteArrayInputStream fileInputStream = new ByteArrayInputStream(bytes);

            ObjectInputStream objectInputStream = new ObjectInputStream(fileInputStream);
            createResumeForm = (Object) objectInputStream.readObject();
            objectInputStream.close();
            fileInputStream.close();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }

        return createResumeForm;

    }

    public String ReturnPath(Context context) {

        String root = Environment.getExternalStorageDirectory().getAbsolutePath();

        File fileTarget = new File(root + "/Android/data/" + context.getPackageName());

        if (!fileTarget.exists())
            fileTarget.mkdirs();

        return fileTarget.getAbsolutePath();

    }


    public  void ClearAll(Context context) {
        if (context == null)
            return;

        DeleteRecursive(new File(ReturnPath(context)));
    }

    public  void DeleteRecursive(File fileOrDirectory) {
        if (fileOrDirectory.isDirectory())
            for (File child : fileOrDirectory.listFiles())
                DeleteRecursive(child);

        fileOrDirectory.delete();
    }


    public  synchronized void insert(Context context,String name, byte[] obj)
            throws IllegalAccessException, IllegalArgumentException {

        String res = getName(context,name);

        SQLiteDatabase db = DatabaseObj.getInstance(context).getWritableDatabase();

        ContentValues contentValues = new ContentValues();
        contentValues.put("name", name);
        contentValues.put("obj", obj);


        if (res == null) {
            int value= (int) db.insert(TABLE, null, contentValues);
            //System.out.println("INSERT->" + name+"->"+value);
        } else {
            int value=  db.update(TABLE, contentValues, "name=?", new String[]{name});
            //System.out.println("UPDATE->" + name+"->"+value);
        }
        //db.close();


    }

    public  synchronized byte[] get(Context context,String name) {


        SQLiteDatabase db = DatabaseObj.getInstance(context).getReadableDatabase();
        Cursor cursor = db.query(TABLE, new String[]{"obj"}, "name=?", new String[]{name}, null, null, null);

        byte[] obj = null;

        if (cursor != null && cursor.moveToFirst()) {

            obj = cursor.getBlob(cursor.getColumnIndex("obj"));
        }

        //System.out.println("READ->"+name);

        //db.close();

        return obj;
    }

    private synchronized  String getName(Context context,String name) {

        SQLiteDatabase db = DatabaseObj.getInstance(context).getReadableDatabase();

        Cursor cursor = db.query(TABLE, new String[]{"name"}, "name=?", new String[]{name}, null, null, null);

        String res = null;

        if (cursor != null && cursor.moveToFirst()) {

            res = cursor.getString(cursor.getColumnIndex("name"));
        }

        //db.close();

        return res;
    }

}
