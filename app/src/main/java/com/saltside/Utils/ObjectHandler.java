package com.saltside.Utils;

import com.saltside.model.SaltSideModel;
import com.saltside.model.SaltSideModelData;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by E6430 on 8/18/2016.
 */
public class ObjectHandler {
    public static String TAG = "ObjectHandler";
    public SaltSideModelData parseSaltSideModelDataJson(String sJSONString) {
        JSONObject jsonObject = null;
        JSONArray jsonArray = null;
        try {
            /*jsonObject = new JSONObject(sJSONString);*/
            jsonArray = new JSONArray(sJSONString);
            List<SaltSideModel> saltSideModelList = new ArrayList<SaltSideModel>();
            for (int i = 0; i < jsonArray.length(); i++) {
                JSONObject jsonObject1 = jsonArray.getJSONObject(i);
                SaltSideModel saltSideModel = new SaltSideModel();
                saltSideModel.setDescription(jsonObject1.getString("description"));
                saltSideModel.setImageUrl(jsonObject1.getString("image"));
                saltSideModel.setTitle(jsonObject1.getString("title"));
                saltSideModelList.add(saltSideModel);
            }

            SaltSideModelData saltSideModelData = new SaltSideModelData();
            saltSideModelData.setSaltSideModelList(saltSideModelList);
            return saltSideModelData;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
}
