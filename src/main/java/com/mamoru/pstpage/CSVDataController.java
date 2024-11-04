package com.mamoru.pstpage;

import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVRecord;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;


@Controller
public class CSVDataController {

    private final String url ="https://gitlab.com/pst-pepega/pst-scripts/-/raw/main/LFM_Proseries_Data/lfm_proseries_data.csv";
    private final String urlSeason ="https://gitlab.com/pst-pepega/pst-scripts/-/raw/main/lfm_proseries_data/lfm_proseries_data_current_season.csv";
    private final String urlSeasonF ="https://gitlab.com/pst-pepega/pst-scripts/-/raw/main/lfm_proseries_data/lfm_proseries_data_pro-series-s%s.csv";

    @GetMapping("/")
    public ModelAndView getCsvData(@RequestParam(required = false) String vorname, @RequestParam(required = false) String nachname, @RequestParam(required = false) String user_id) throws Exception {
        return getModelAndView(vorname, nachname, user_id, url);
    }

    private ModelAndView getModelAndView(@RequestParam(required = false) String vorname, @RequestParam(required = false) String nachname ,@RequestParam(required = false) String user_id, String url) throws Exception {
        List<CSVRecordModel> data = readCSVFromURL(url).stream()
                .filter(record -> vorname == null || record.getVorname().equals(vorname))
                .filter(record -> nachname == null || record.getNachname().equals(nachname))
                .filter(record -> user_id == null || record.getUser_id().equals(user_id))
                .collect(Collectors.toList());

        ModelAndView modelAndView = new ModelAndView("lfmpro");
        modelAndView.addObject("csvData", data);

        return modelAndView;
    }

    @GetMapping("/season/{seasonNumber}")
    public ModelAndView getCsvDataSpecifiedSeason(@PathVariable String seasonNumber, @RequestParam(required = false) String vorname, @RequestParam(required = false) String nachname, @RequestParam(required = false) String user_id) throws Exception {
        return getModelAndView(vorname, nachname, user_id, String.format(urlSeasonF,seasonNumber));
    }

    @GetMapping("/season")
    public ModelAndView getCsvDataSeason(@RequestParam(required = false) String vorname, @RequestParam(required = false) String nachname, @RequestParam(required = false) String user_id) throws Exception {
        return getModelAndView(vorname, nachname,user_id, urlSeason);
    }

    public List<CSVRecordModel> readCSVFromURL(String url) throws Exception {
        URL csvFileUrl = new URL(url);
        URLConnection connection = csvFileUrl.openConnection();
        List<CSVRecordModel> records = new ArrayList<>();

        CSVParser parser = CSVFormat.DEFAULT.withFirstRecordAsHeader().withDelimiter(',').parse(new InputStreamReader(connection.getInputStream()));



        for (CSVRecord record : parser) {
            CSVRecordModel data = new CSVRecordModel();
            data.setVorname(record.get("vorname"));
            data.setNachname(record.get("nachname"));
            data.setUser_id(record.get("user_id"));
            data.setRaces(Integer.parseInt(record.get("Races")));
            data.setWins(Integer.parseInt(record.get("Wins")));
            data.setPodiums(Integer.parseInt(record.get("Podiums")));
            data.setPole_Positions(Integer.parseInt(record.get("Pole_Positions")));
            data.setFastest_Laps(Integer.parseInt(record.get("Fastest_Laps")));
            data.setTop5s(Integer.parseInt(record.get("Top5s")));
            data.setTop10s(Integer.parseInt(record.get("Top10s")));
            data.setAvg_Finishing(Double.parseDouble(record.get("Avg_Finishing")));
            data.setAvg_Qualifying(parseDoubleDefault(record.get("Avg_Qualifying")));
            data.setPoints(Double.parseDouble(record.get("Points")));
            data.setPoints_per_Race(Double.parseDouble(record.get("Points_per_Race")));
            data.setWinrate(Double.parseDouble(record.get("Winrate")));
            data.setPodiumrate(Double.parseDouble(record.get("Podiumrate")));
            data.setPole_Position_Rate(Double.parseDouble(record.get("Pole_Position_Rate")));
            data.setFastest_Lap_Rate(Double.parseDouble(record.get("Fastest_Lap_Rate")));
            data.setSum_Elo_Gain(Integer.parseInt(record.get("Sum_Elo_Gain")));
            data.setSum_SR_Gain(Double.parseDouble(record.get("Sum_SR_Gain")));
            data.setSum_IPs(Integer.parseInt(record.get("Sum_IPs")));
            data.setIP_per_Race(Double.parseDouble(record.get("IP_per_Race")));
            data.setAverage_SOF(Double.parseDouble(record.get("average_SOF")));
            data.setQ_to_R(String.format("%.2f", parseDoubleDefault(record.get("Q_to_R"))));

            records.add(data);
        }
        return records;
    }

    private double parseDoubleDefault(String value) {
        if (value == null || value.isEmpty()) return 0.0;
        return Double.parseDouble(value);
    }
}