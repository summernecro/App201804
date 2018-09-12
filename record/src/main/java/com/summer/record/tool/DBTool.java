package com.summer.record.tool;

//by summer on 2018-09-11.

import android.support.annotation.NonNull;

import com.android.lib.base.interf.OnFinishListener;
import com.raizlabs.android.dbflow.config.FlowManager;
import com.raizlabs.android.dbflow.sql.language.Method;
import com.raizlabs.android.dbflow.sql.language.SQLite;
import com.raizlabs.android.dbflow.sql.language.property.PropertyFactory;
import com.raizlabs.android.dbflow.structure.database.DatabaseWrapper;
import com.raizlabs.android.dbflow.structure.database.FlowCursor;
import com.raizlabs.android.dbflow.structure.database.transaction.DefaultTransactionManager;
import com.raizlabs.android.dbflow.structure.database.transaction.ITransaction;
import com.raizlabs.android.dbflow.structure.database.transaction.ProcessModelTransaction;
import com.raizlabs.android.dbflow.structure.database.transaction.Transaction;
import com.summer.record.data.Record;
import com.summer.record.data.Record_Table;
import com.summer.record.data.db.RecordDataBase;

import java.util.ArrayList;

public class DBTool {

    public static void saveRecords(final ArrayList<Record> records, final OnFinishListener onFinishListener){
        if(records==null||records.size()==0){
            onFinishListener.onFinish(records);
            return;
        }
        FlowManager.getDatabase(RecordDataBase.class).beginTransactionAsync(new ITransaction() {
            @Override
            public void execute(DatabaseWrapper databaseWrapper) {
                for(int i=0;i<records.size();i++){
                    records.get(i).save();
                }
            }
        }).error(new Transaction.Error() {
            @Override
            public void onError(@NonNull Transaction transaction, @NonNull Throwable error) {
                onFinishListener.onFinish(records);
            }
        }).success(new Transaction.Success() {
            @Override
            public void onSuccess(@NonNull Transaction transaction) {
                onFinishListener.onFinish(records);
            }
        }).build().executeSync();
    }

    public static long getLastReCordCTime(String type){
        FlowCursor cursor = SQLite.select(Method.max(Record_Table.ctime).as("max")).from(Record.class).where(Record_Table.atype.is(type)).query();
        long last = 0;
        while (cursor.moveToNext()){
            last = cursor.getLong(0);
        }
        return last/1000;
    }

    public static ArrayList<Record> getAllRecord(String type){
        ArrayList<Record> records = (ArrayList<Record>) SQLite.select().from(Record.class).where(Record_Table.atype.is(type)).queryList();
        return records;
    }

    public static ArrayList<Record> getAllRecord(String type,long[] ctime){
        ArrayList<Record> records = (ArrayList<Record>) SQLite.select().from(Record.class)
                .where(Record_Table.atype.is(type))
                .and(Record_Table.ctime.greaterThan(ctime[0]*1000))
                .and(Record_Table.ctime.lessThan(ctime[1]*1000))
                .queryList();
        return records;
    }

    public static int[] getMaxMinYear(String type){
        long max = 0;
        long min = 0;
        FlowCursor maxCursor = SQLite.select(Method.max(Record_Table.ctime).as("max")).from(Record.class).where(Record_Table.atype.is(type)).query();
        while (maxCursor.moveToNext()){
            max = maxCursor.getLongOrDefault(0);
        }
        maxCursor.close();

        FlowCursor minCursor = SQLite.select(Method.min(Record_Table.ctime).as("min")).from(Record.class).where(Record_Table.atype.is(type)).query();
        while (minCursor.moveToNext()){
            min = minCursor.getLongOrDefault(0);
        }
        minCursor.close();
        return new int[]{ DateUtil.toYear(min/1000+""), DateUtil.toYear(max/1000+"")};
    }
}
