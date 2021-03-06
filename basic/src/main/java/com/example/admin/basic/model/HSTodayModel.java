package com.example.admin.basic.model;

import com.example.admin.basic.stock.MinData;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by luoyu on 2017/5/26.
 */

public class HSTodayModel {

    /**
     * count : 242
     * symbol : 600519
     * name : 贵州茅台
     * data : [["0930",450.82,450.82,19400],["0931",451.9,451.36,55400],["0932",451.6,451.44,14799],["0933",451.13,451.363,10400],["0934",451.13,451.316,14001],["0935",451,451.263,13400],["0936",450.76,451.191,10200],["0937",450.8,451.143,11800],["0938",450.78,451.102,15263],["0939",450.99,451.091,11300],["0940",451.2,451.101,8200],["0941",451.2,451.109,11000],["0942",451.4,451.132,9518],["0943",451.89,451.186,6800],["0944",452.52,451.275,24202],["0945",452.09,451.326,24100],["0946",452.09,451.371,6300],["0947",451.21,451.362,9500],["0948",451.49,451.368,5000],["0949",451.35,451.368,10500],["0950",451.45,451.371,6720],["0951",451.9,451.395,12300],["0952",451.89,451.417,8600],["0953",451.5,451.42,10100],["0954",452,451.444,10900],["0955",451.87,451.46,9100],["0956",451.6,451.465,18000],["0957",451.97,451.483,3800],["0958",451.97,451.5,14200],["0959",451.97,451.516,8700],["1000",451.99,451.531,5000],["1001",452.04,451.547,16400],["1002",452.64,451.58,17079],["1003",453.12,451.625,24755],["1004",454.03,451.694,29900],["1005",455.55,451.801,43215],["1006",454,451.861,50785],["1007",454.1,451.919,26807],["1008",454,451.973,21600],["1009",453.8,452.018,10500],["1010",453.8,452.062,17129],["1011",453.98,452.108,13301],["1012",453.88,452.149,16000],["1013",453.55,452.181,10900],["1014",453.78,452.216,6500],["1015",453.98,452.255,4900],["1016",454,452.292,31900],["1017",454.6,452.34,11798],["1018",454.9,452.392,17299],["1019",454.56,452.435,25811],["1020",454,452.466,8690],["1021",453.6,452.488,16002],["1022",453.7,452.511,6600],["1023",453.85,452.536,5500],["1024",453.6,452.555,10200],["1025",453,452.563,22201],["1026",453,452.571,13300],["1027",452.89,452.576,11202],["1028",453.45,452.591,11600],["1029",453.6,452.608,27100],["1030",454,452.63,22493],["1031",454.1,452.654,23229],["1032",453.99,452.675,36678],["1033",454,452.696,21529],["1034",454,452.716,15450],["1035",454,452.736,16221],["1036",454.11,452.756,14085],["1037",454.24,452.778,6415],["1038",454.25,452.799,6518],["1039",454.3,452.821,33382],["1040",454,452.837,14500],["1041",454,452.853,11814],["1042",454,452.869,4989],["1043",454,452.884,3512],["1044",454.09,452.901,2700],["1045",454.14,452.917,2688],["1046",454.1,452.932,4196],["1047",453.82,452.944,8600],["1048",453.88,452.955,20000],["1049",453.99,452.968,9100],["1050",453.85,452.979,12400],["1051",454,452.992,34300],["1052",453.4,452.997,7400],["1053",453,452.997,18200],["1054",453.9,453.007,9100],["1055",453.9,453.018,17634],["1056",453.16,453.019,8966],["1057",453.16,453.021,5800],["1058",453.05,453.021,6830],["1059",453.01,453.021,6470],["1100",453.15,453.023,12900],["1101",453.27,453.025,3800],["1102",453.26,453.028,2200],["1103",453.09,453.028,2700],["1104",452.92,453.027,14908],["1105",452.9,453.026,5592],["1106",452.9,453.025,1100]]
     * yestclose : 450.6
     * lastVolume : 1375876
     * date : 20170526
     */

    private int count;
    private String symbol;
    private String name;
    private double yestclose;
    private double lastVolume;
    private String date;
    private List<List<String>> data;

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public String getSymbol() {
        return symbol;
    }

    public void setSymbol(String symbol) {
        this.symbol = symbol;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public double getYestclose() {
        return yestclose;
    }

    public void setYestclose(double yestclose) {
        this.yestclose = yestclose;
    }

    public double getLastVolume() {
        return lastVolume;
    }

    public void setLastVolume(int lastVolume) {
        this.lastVolume = lastVolume;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public List<List<String>> getData() {
        return data;
    }

    public void setData(List<List<String>> data) {
        this.data = data;
    }

    public MinData parseData(){
        MinData minData = new MinData();
        List<List<Double>> outData = new ArrayList<>();
        List<String> time = new ArrayList<>();
        List<List<String>> data = getData();
        for(int i = 0; i < getData().size(); i++){
            time.add(data.get(i).get(0));
            List<Double> one = new ArrayList<>();
            one.add(Double.parseDouble(data.get(i).get(1)));
            one.add(Double.parseDouble(data.get(i).get(3)));
            one.add(Double.parseDouble(data.get(i).get(2)));
            one.add(yestclose);
            outData.add(one);
        }
        minData.setLast(yestclose);
        minData.setMinData(outData);
        minData.setTimes(time);
        return minData;
    }
}
