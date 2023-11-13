package com.cbfacademy.apiassessment.utils;

import com.cbfacademy.apiassessment.model.Portfolio;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.*;
import java.lang.reflect.Type;
import java.util.List;

public class FileUtils {
    private static  final String filePath = "portfolios.json";
    public static List<Portfolio> readPortfoliosFromJson(){
        doesFileExists();
        try(Reader reader = new FileReader(filePath)){
            Type listType = new TypeToken<List<Portfolio>>() {}.getType();
            return new Gson().fromJson(reader, listType);
        } catch (IOException e) {
            e.printStackTrace();
            return null;

        }
    }

    public static void writePortfolioToJson(List<Portfolio> portfolios) {
        try (Writer writer = new FileWriter(filePath)) {
            new Gson().toJson(portfolios, writer);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    private static void doesFileExists() {
        File file = new File(filePath);
        if (!file.exists()) {
            try {
                file.createNewFile();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
