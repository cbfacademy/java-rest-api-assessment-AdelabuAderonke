package com.cbfacademy.apiassessment.utils;

import com.cbfacademy.apiassessment.model.Investment;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.*;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

public class InvestmentFileUtils {
    private static final String filePath = "investments.json";

    public static List<Investment> readInvestmentsFromJson() {
        doesFileExist();
        try (Reader reader = new FileReader(filePath)) {
            if (new File(filePath).length() == 0) {
                return new ArrayList<>();  // Return an empty list if the file is empty
            }

            Type listType = new TypeToken<List<Investment>>() {}.getType();
            return new Gson().fromJson(reader, listType);
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

    public static void writeInvestmentsToJson(List<Investment> investments) {
        try (Writer writer = new FileWriter(filePath)) {
            new Gson().toJson(investments, writer);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static void doesFileExist() {
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
