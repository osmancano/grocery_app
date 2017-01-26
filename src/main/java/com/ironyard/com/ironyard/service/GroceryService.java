package com.ironyard.com.ironyard.service;

import com.google.gson.Gson;
import com.ironyard.data.GroceryItem;
import com.ironyard.data.IronYardUser;

import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Created by jasonskipper on 1/26/17.
 */
public class GroceryService {

    public List<GroceryItem> getAll(IronYardUser user){
        ArrayList<GroceryItem> found = null;
        try {
            File fileUrl = new File(System.getProperty("java.io.tmpdir")+user.getUsername()+".json");
            if(fileUrl != null && fileUrl.exists()) {
                FileReader fr = new FileReader(fileUrl);


                Gson tmpG = new Gson();
                GroceryItem[] t = tmpG.fromJson(fr, GroceryItem[].class);
                if (t != null && t.length > 0) {
                    found = new ArrayList<GroceryItem>(Arrays.asList(t));
                }
            }
        }catch(Exception e){
            e.printStackTrace();
        }
        return found;
    }

    public void save(IronYardUser user, GroceryItem item){
        List<GroceryItem> foundGroceries = getAll(user);
        if(foundGroceries == null){
            foundGroceries = new ArrayList<GroceryItem>();
        }
        foundGroceries.add(item);
        // save to disk
        try {
            File fileUrl = new File(System.getProperty("java.io.tmpdir")+user.getUsername()+".json");
            FileWriter fr = new FileWriter(fileUrl);
            Gson tmpG = new Gson();
            tmpG.toJson(foundGroceries, fr);
            fr.flush();
            fr.close();
        }catch(Exception e){
            e.printStackTrace();
        }
        return;
    }
}
