package com.cbfacademy.apiassessment.utils;

import com.cbfacademy.apiassessment.entity.Portfolio;

import java.io.*;
import java.lang.reflect.Type;
import java.util.List;

public class FileUtils {
    private static  final String filePath = "portfolios.json";
    public static List<Portfolio> readPortfoliosFromJson(){
        try(Reader reader = new FileReader(filePath)){
            Type listType = new TypeToken<List<Portfolio>>() {}.getType();
            return new Gson().fromJson(reader, listType);
        } catch (IOException e) {
            e.printStackTrace();
            return null;

        }
    }

    public static void writeBooksToJson(List<Book> books) {
        try (Writer writer = new FileWriter(filePath)) {
            new Gson().toJson(books, writer);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
