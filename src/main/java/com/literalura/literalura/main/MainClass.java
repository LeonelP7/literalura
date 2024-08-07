package com.literalura.literalura.main;

import com.literalura.literalura.model.BookData;
import com.literalura.literalura.model.ResultData;
import com.literalura.literalura.service.APIUsage;
import com.literalura.literalura.service.DataConverter;
import com.literalura.literalura.service.IDataConverter;

import java.util.Comparator;
import java.util.InputMismatchException;
import java.util.List;
import java.util.Scanner;

public class MainClass {

    private Scanner scanner = new Scanner(System.in);
    private APIUsage apiUsage = new APIUsage();
    private IDataConverter dataConverter = new DataConverter();

    private final String BASE_URL = "https://gutendex.com/books/";
    public void menu(){

        String json = apiUsage.getData(BASE_URL);
        List<BookData> result = dataConverter.getData(json, ResultData.class).books();

        int option = 0;


        while(true){
            System.out.println("""
                    Select an option:
                    1. Top 10 most downloaded books
                    """);
            try{
                option = scanner.nextInt();
            }catch (InputMismatchException e){
                System.out.println(e.getMessage());
                menu();
            }


            switch (option) {
                case 1:
                    top10BooksByDownloadCount();
                    break;
            }

            if (option == 77){
                System.out.println("Exiting the program");
                break;
            }
        }
    }

    private void top10BooksByDownloadCount(){
        String json = apiUsage.getData(BASE_URL);
        List<BookData> result = dataConverter.getData(json, ResultData.class).books();

        result.stream().
                sorted(Comparator.comparing(BookData::downloadCount))
                .limit(10)
                .forEach(l -> System.out.println("\nTitle: " + l.title() + "\nAuthors: "
                        + l.authors().toString() + "\nSubjects: " + l.subjects().toString()
                        + "\nDownload count: " + l.downloadCount()));
    }
}
