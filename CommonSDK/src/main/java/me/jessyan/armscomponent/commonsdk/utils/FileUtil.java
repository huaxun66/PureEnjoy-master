/*
 * Copyright 2018 JessYan
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package me.jessyan.armscomponent.commonsdk.utils;

import android.content.Context;
import android.content.res.AssetManager;

import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;

/**
 * ================================================
 * Created by Watson on 2018/12/29
 * ================================================
 */
public class FileUtil {


    public static boolean isAssetsFileExist(Context context, String path) {
        String dir;
        String filename;
        if (path.contains("/")) {
            dir = path.substring(0, path.lastIndexOf("/"));
            filename = path.substring(path.lastIndexOf("/") + 1);
        } else {
            dir = "";
            filename = path;
        }
        AssetManager assetManager = context.getAssets();
        try {
            String[] names = assetManager.list(dir);
            for (int i = 0; i < names.length; i++) {
                if (names[i].equals(filename.trim())) {
                    return true;
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
        return false;
    }

    public static String getStringFromAssetFile(Context context, String fileName) {
        try {
            InputStream is = context.getResources().getAssets().open(fileName);
            ByteArrayOutputStream result = new ByteArrayOutputStream();
            byte[] buffer = new byte[1024];
            int length;
            while ((length = is.read(buffer)) != -1) {
                result.write(buffer, 0, length);
            }
            String str = result.toString(StandardCharsets.UTF_8.name());
            return str;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

}
