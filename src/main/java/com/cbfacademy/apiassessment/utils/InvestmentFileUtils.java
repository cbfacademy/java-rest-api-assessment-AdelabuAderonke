package com.cbfacademy.apiassessment.utils;

import com.cbfacademy.apiassessment.dto.InvestmentDTO;
import com.cbfacademy.apiassessment.exception.InvestmentFileException;
import com.cbfacademy.apiassessment.model.Investment;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.*;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

public class InvestmentFileUtils {
    private static final String filePath = "investments.json";

    public static List<InvestmentDTO> readInvestmentsFromJson()throws IOException {
        doesFileExists();

        try (Reader reader = new FileReader(filePath)) {
            Type listType = new TypeToken<List<Investment>>() {}.getType();
            return new Gson().fromJson(reader, listType);
        } catch (IOException e) {
            //e.printStackTrace();
            throw new InvestmentFileException("Error reading investments from JSON", e);

        }
    }
    public static void writeInvestmentsToJson(List<InvestmentDTO> investments) {
        try (Writer writer = new FileWriter(filePath)) {
            new Gson().toJson(investments, writer);
        } catch (IOException e) {
            //e.printStackTrace();
            throw new InvestmentFileException("Error writing investments from JSON", e);
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
