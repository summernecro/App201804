package com.summer.record.ui.main.record.folder;

//by summer on 2018-09-25.

import android.content.Context;
import android.os.AsyncTask;

import com.android.lib.base.interf.OnFinishListener;
import com.android.lib.base.interf.OnLoadingInterf;
import com.android.lib.base.ope.BaseDAOpe;
import com.android.lib.util.NullUtil;
import com.summer.record.data.Folder;
import com.summer.record.data.Record;
import com.summer.record.tool.DBTool;
import com.summer.record.tool.FileTool;

import java.util.ArrayList;
import java.util.HashMap;

public class FolderDAOpe extends BaseDAOpe {

    public void getImages(final Context context, final String type, final OnLoadingInterf i){
        i.onStarLoading(context);
        new AsyncTask<String, String, ArrayList<Record>>() {
            @Override
            protected ArrayList<Record> doInBackground(String... strings) {
                ArrayList<Record> all = FileTool.getRecords(context, type,new String[]{DBTool.getLastReCordCTime(type)+"",""+(System.currentTimeMillis()/1000)}, new OnFinishListener() {
                    @Override
                    public void onFinish(Object o) {

                    }
                });
                ArrayList<Record> records = new ArrayList<>();
                records.addAll(all);
                records.addAll(DBTool.getAllRecord(type));
                return records;
            }

            @Override
            protected void onPostExecute(ArrayList<Record> videos) {
                super.onPostExecute(videos);
                i.onStopLoading(videos);
            }

            @Override
            protected void onProgressUpdate(String... values) {
                super.onProgressUpdate(values);
                i.onProgress(values[0]);
            }
        }.execute();
    }

    /**
     * 将记录按照文件夹目录排序
     * @param records
     * @return
     */
    public void sortRecordByFolder(final ArrayList<Record> records, final OnFinishListener listener){
        new AsyncTask<String, String, ArrayList<Folder>>() {
            @Override
            protected ArrayList<Folder> doInBackground(String... strings) {
                ArrayList<Folder> folders = new ArrayList<>();
                HashMap<String,ArrayList<Record>> map = new HashMap<>();
                for(int i=0;i<records.size();i++){
                    records.get(i).init();
                    String name = FileTool.getFolderName(records.get(i).getLocpath());
                    if(NullUtil.isStrEmpty(name)){
                        continue;
                    }
                    if(map.get(name)==null){
                        map.put(name,new ArrayList<Record>());
                    }
                    map.get(name).add(records.get(i));
                }
                String[] strs = new String[map.keySet().size()];
                strs = map.keySet().toArray(strs);
                for(int i=0;i<strs.length;i++){
                    Folder folder = new Folder(strs[i],map.get(strs[i]));
                    folders.add(folder);
                }
                return folders;
            }

            @Override
            protected void onPostExecute(ArrayList<Folder> folders) {
                super.onPostExecute(folders);
                listener.onFinish(folders);
            }
        }.execute();

    }
}
