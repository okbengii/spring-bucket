package com.beng.utils;

import java.io.DataInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStreamReader;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.beng.dao.CoffeeService;
import com.opencsv.CSVParser;
import com.opencsv.CSVReader;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component
public class CsvUtils {
    private final String CSVPATH = "demo.csv";

    @Autowired
    private CoffeeService requestInterfaceService;

    public void readCsv() {
        File file = new File(CSVPATH);
        try (FileInputStream fileIn = new FileInputStream(file);
                DataInputStream dataIn = new DataInputStream(fileIn);
                InputStreamReader in = new InputStreamReader(dataIn, "UTF-8");) {

            CSVReader csvReader = new CSVReader(in, CSVParser.DEFAULT_SEPARATOR, CSVParser.DEFAULT_QUOTE_CHARACTER,
                    CSVParser.DEFAULT_ESCAPE_CHARACTER, 1);
            String[] strs;
            while ((strs = csvReader.readNext()) != null) {
                if ("是".equals(strs[3]))
                    strs[3] = "0";
                else if ("否".equals(strs[3]))
                    strs[3] = "1";
                else
                    strs[3] = null;
                if ("TRUE".equals(strs[7]))
                    strs[7] = "0";
                else if ("FALSE".equals(strs[7]))
                    strs[7] = "1";
                else
                    strs[7] = null;

                requestInterfaceService.insert(strs);
            }
        } catch (Exception e) {
            log.info(e.getMessage(), e);
        }
    }

}
