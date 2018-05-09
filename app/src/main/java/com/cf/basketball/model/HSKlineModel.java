package com.cf.basketball.model;

import com.cf.basketball.stock.DayData;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * Created by luoyu on 2017/5/26.
 */

public class HSKlineModel {

    /**
     * data : [["20170103",334.28,334.56,337,332.81,2076389,0.12],["20170104",334.62,351.91,352.17,334.6,6525738,5.19],["20170105",350,346.74,351.45,345.44,4170448,-1.47],["20170106",346.64,350.76,359.78,346.1,6809562,1.16],["20170109",347.8,348.51,352.88,346.54,3540500,-0.64],["20170110",348.45,349,352,346.6,3172764,0.14],["20170111",348,345.45,348,343.5,2359851,-1.02],["20170112",346.55,347.05,347.4,344.51,1777940,0.46],["20170113",346.98,344.87,347.39,343.88,1828248,-0.63],["20170116",344.13,341.47,344.8,338.8,3685545,-0.99],["20170117",342.6,349.13,351.5,342,3518838,2.24],["20170118",348.88,355.08,356.77,347.21,4600570,1.7],["20170119",355,354.72,358.48,351.7,2852392,-0.1],["20170120",354.9,354.99,357.4,353.03,2134417,0.08],["20170123",356.88,351.22,359.8,349.46,3258118,-1.06],["20170124",351.27,350.31,352.8,346.75,2691458,-0.26],["20170125",349.98,347.63,349.98,346.2,2299036,-0.77],["20170126",348.14,344.42,348.8,341.3,3782617,-0.92],["20170203",346,346.85,349.18,345,2093643,0.71],["20170206",348.49,346.85,348.89,344.96,1568079,0],["20170207",346.84,343.58,347.4,343.14,2051983,-0.94],["20170208",343.76,344.21,345.5,342.67,1908854,0.18],["20170209",345,347.61,348.79,344.61,2332438,0.99],["20170210",348.62,345.08,349.82,345,2405659,-0.73],["20170213",344,349.75,351.9,341.92,5358790,1.35],["20170214",349.54,350.21,352.6,349.1,2655868,0.13],["20170215",351.5,348.53,352.44,347.2,2625696,-0.48],["20170216",348.5,348.09,348.62,345.51,1764230,-0.13],["20170217",348.9,350.56,354.44,347.5,2910234,0.71],["20170220",350.41,361.29,362.32,350.01,6185340,3.06],["20170221",361.88,358.74,362.43,357.18,2770577,-0.71],["20170222",360.61,361.89,366.52,360,3713031,0.88],["20170223",360.97,361.7,365.48,359.76,2332806,-0.05],["20170224",362,362.62,362.89,360.05,1769278,0.25],["20170227",362.49,357.2,362.85,356.8,2540046,-1.49],["20170228",357,354.66,359.2,353.58,2747351,-0.71],["20170301",355.1,356.61,358.2,355,2194420,0.55],["20170302",356.71,356.82,357,354.53,2210362,0.06],["20170303",355,356.54,358.22,355,1415355,-0.08],["20170306",357.66,358.96,359.54,356.55,2281884,0.68],["20170307",358.03,367.2,368.88,358.03,4628698,2.3],["20170308",367.18,367.28,368.49,365.5,1603770,0.02],["20170309",367,369.9,369.98,365.56,2340606,0.71],["20170310",369.92,369.85,377,368.36,4095105,-0.01],["20170313",370.05,371.55,374.33,367.53,2767432,0.46],["20170314",371.55,369.5,373.85,368.34,2041649,-0.55],["20170315",369.5,374.68,375.15,369.01,2515526,1.4],["20170316",376.56,374.77,378.3,372.8,2502260,0.02],["20170317",373.1,378.48,384.45,373.1,3470026,0.99],["20170320",380.5,386.41,386.71,378.88,3154594,2.1],["20170321",386.55,394.01,396.5,386.5,4846096,1.97],["20170322",390.53,394.48,394.6,385,4553271,0.12],["20170323",392.03,389.79,393.99,386.6,3450481,-1.19],["20170324",392.32,386.82,393.22,385,3328772,-0.76],["20170327",386.03,378.82,389.26,378.39,5369028,-2.07],["20170328",379.95,380.26,381.15,376.66,4118717,0.38],["20170329",381.01,383.65,385.8,379.9,3729967,0.89],["20170330",383,386.14,386.89,382.6,3161299,0.65],["20170331",386.2,386.36,388,384.18,2447422,0.06],["20170405",385.66,389.66,394.99,385,4078998,0.85],["20170406",389.01,391.91,393,386.63,2885720,0.58],["20170407",392.99,392.87,394,389.6,2409723,0.24],["20170410",394,389.2,394,388,2886948,-0.93],["20170411",388.05,385.29,390.7,380.6,4353072,-1],["20170412",385.29,390.23,391,384.8,2700291,1.28],["20170413",390.78,398.39,398.59,390.51,4564835,2.09],["20170414",399.43,395.45,399.88,395,2364656,-0.74],["20170417",391,395.79,398.28,388.55,2879371,0.09],["20170418",394.6,404.65,406.55,394.6,4958100,2.24],["20170419",403,405.74,409.1,402.86,3755011,0.27],["20170420",406.09,415.31,420.43,406.09,5045768,2.36],["20170421",415,400.53,415.6,400,4933292,-3.56],["20170424",400.62,398.55,405,389,5003797,-0.49],["20170425",404.8,418.89,420.17,404.8,7619271,5.1],["20170426",418.85,417.96,428.68,413.01,5662412,-0.22],["20170427",415.53,421.44,421.97,410.43,2824071,0.83],["20170428",421.99,413.48,423,409.9,4950587,-1.89],["20170502",411.6,412.08,415,409.18,2329168,-0.34],["20170503",411,417.61,417.66,411,2026791,1.34],["20170504",414.63,416,418.48,414.63,2670915,-0.39],["20170505",416.2,416.19,422.78,415.1,3743711,0.05],["20170508",416.18,405.48,417,404.72,4072165,-2.57],["20170509",404.44,408.76,409.88,398,5280192,0.81],["20170510",408.4,410.09,414.44,407.02,2603938,0.33],["20170511",408.4,412.16,414.7,406.51,3046764,0.5],["20170512",413.59,413.47,415.3,410.01,2233742,0.32],["20170515",413.12,419.55,420.35,413.12,3344735,1.47],["20170516",419.6,430.12,431.22,418.26,5016490,2.52],["20170517",429,426.6,430.19,424.88,2751521,-0.82],["20170518",425.05,429.54,430.7,425.01,2430080,0.69],["20170519",429.57,440.82,441.8,429.57,3831083,2.63],["20170522",439.04,441.78,446.01,436.2,3532149,0.22],["20170523",442.25,454.2,454.47,442.25,4845439,2.81],["20170524",452.9,450.32,456.48,446,4022618,-0.85],["20170525",448.71,450.6,452.96,447.5,2889842,0.06],["20170526",450.82,451,455.6,448.02,1629328,0.09]]
     * symbol : 600519
     * name : 贵州茅台
     */

    private String symbol;
    private String name;
    DayData dayData;
    private List<List<String>> data;
    ArrayList<Double> closeArray = new ArrayList<>();
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

    public List<List<String>> getData() {
        return data;
    }

    public void setData(List<List<String>> data) {
        this.data = data;
    }

    public synchronized void add(HSKlineModel model){
        data.addAll(0, model.getData());
    }

    public void refresNewestData(List<String> last){
        List<List<Double>> outData = dayData.getKData();
        List<Double> oneData = new ArrayList<>();
        oneData.add(Double.parseDouble(last.get(1)));
        oneData.add(Double.parseDouble(last.get(2)));
        oneData.add(Double.parseDouble(last.get(3)));
        oneData.add(Double.parseDouble(last.get(4)));
        oneData.add(Double.parseDouble(last.get(5)));
        oneData.add(Double.parseDouble(last.get(6)));
        oneData.add(100.0);
        oneData.add(100.0);
        oneData.add(100.0);
        oneData.add(0.0);
        oneData.add(0.0);
        oneData.add(0.0);
        outData.remove(outData.size() - 1);
        outData.add(oneData);
        closeArray.remove(closeArray.size() - 1);
        closeArray.add(Double.parseDouble(last.get(2)));
        getKDJMap(outData);
        ArrayList<ArrayList<Double>> result = getMACD(closeArray, 12,26,9);
        int i = 9;
        for(ArrayList<Double> one : result){
            int j = 0;
            for(Double value : one){
                outData.get(j).set(i, value);
                j++;
            }
            i++;
        }
        dayData.setKData(outData);
    }

    public DayData parseData(){
        dayData = new DayData();
        List<List<Double>> outData = new ArrayList<>();
        List<String> time = new ArrayList<>();
        closeArray = new ArrayList<>();
        for(int i = 0; i < getData().size(); i++){
            time.add(data.get(i).get(0));
            List<Double> one = new ArrayList<>();
            one.add(Double.parseDouble(data.get(i).get(1)));
            one.add(Double.parseDouble(data.get(i).get(2)));
            one.add(Double.parseDouble(data.get(i).get(3)));
            one.add(Double.parseDouble(data.get(i).get(4)));
            one.add(Double.parseDouble(data.get(i).get(5)));
            one.add(Double.parseDouble(data.get(i).get(6)));
            one.add(100.0);
            one.add(100.0);
            one.add(100.0);
            one.add(0.0);
            one.add(0.0);
            one.add(0.0);
            closeArray.add(Double.parseDouble(data.get(i).get(2)));
            outData.add(one);
        }
        getKDJMap(outData);
        ArrayList<ArrayList<Double>> result = getMACD(closeArray, 12,26,9);
        int i = 9;
        for(ArrayList<Double> one : result){
            int j = 0;
            for(Double value : one){
                outData.get(j).set(i, value);
                j++;
            }
            i++;
        }
        dayData.setTimes(time);
        dayData.setKData(outData);
        return dayData;
    }

    public void getKDJMap(List<List<Double>> m_kData){
        // 默认随机值
        int m_iParam[] = {9, 3, 3};
        int n1 = m_iParam[0];
        int n2 = m_iParam[1];
        int n3 = m_iParam[2];
        if(m_kData == null || n1 > m_kData.size() || n1 < 1)
            return;
        List<Double> model = m_kData.get(n1-4);
//        // 计算N日内的最低最高价
        float maxhigh = model.get(2).floatValue(); // 最高价
        float minlow = model.get(3).floatValue(); // 最低价
        for(int j = 0; j < n1-4; j++) {
            List<Double> m = m_kData.get(j);
            if(maxhigh < m.get(2).floatValue())
            maxhigh = m.get(2).floatValue();
            if(minlow > m.get(3).floatValue())
            minlow = m.get(3).floatValue();
        }
        for(int i = n1 - 3; i < m_kData.size(); i++) {
            List<Double> temp = m_kData.get(i);
            maxhigh = temp.get(2).floatValue();
            minlow = temp.get(3).floatValue();
            for(int j = i - 1; j > i - n1 && j >= 0; j--) {
                List<Double> temp2 = m_kData.get(j);
                if(maxhigh < temp2.get(2).floatValue())
                    maxhigh = temp2.get(2).floatValue();
                if(minlow > temp2.get(3).floatValue())
                    minlow = temp2.get(3).floatValue();
            }
            float rsv = ((temp.get(1).floatValue() - minlow) / (maxhigh - minlow)) * 100.0f;


            double newK = (m_kData.get(i - 1).get(6).floatValue() * (float)(n2 - 1)) / (float)n2 + rsv / (float)n2;
            newK = newK < 0 ? 0 : newK;
            newK = newK > 100 ? 100 : newK;
            System.out.println(newK);
            m_kData.get(i).set(6, newK);
            // 计算D值
            double newD = m_kData.get(i).get(6).floatValue() / (float)n3 + (m_kData.get(i - 1).get(7).floatValue() * (float)(n3 - 1)) / (float)n3;
            newD = newD < 0 ? 0 : newD;
            newD = newD > 100 ? 100 : newD;
            m_kData.get(i).set(7, newD);
            // 计算J值
            double newJ = m_kData.get(i).get(6).floatValue() * 3.0f - 2.0f*m_kData.get(i).get(7).floatValue();
            newJ = newJ < 0 ? 0 : newJ;
            newJ = newJ > 100 ? 100 : newJ;
            m_kData.get(i).set(8, newJ);
        }
    }


    public static final Double getEXPMA(final List<Double> list, final int number) {
        // 开始计算EMA值，
        Double k = 2.0 / (number + 1.0);// 计算出序数
        Double ema = list.get(0);// 第一天ema等于当天收盘价
        for (int i = 1; i < list.size(); i++) {
            // 第二天以后，当天收盘 收盘价乘以系数再加上昨天EMA乘以系数-1
            ema = list.get(i) * k + ema * (1 - k);
        }
        return ema;
    }
    public static final  ArrayList<ArrayList<Double>> getMACD(final List<Double> list, final int shortPeriod, final int longPeriod, int midPeriod) {
        HashMap<String, Double> macdData = new HashMap<String, Double>();
        ArrayList<Double> diffList = new ArrayList<Double>();
        ArrayList<Double> deaList = new ArrayList<Double>();
        ArrayList<Double> macdList = new ArrayList<Double>();
        Double shortEMA = 0.0;
        Double longEMA = 0.0;
        Double dif = 0.0;
        Double dea = 0.0;

        for (int i = list.size() - 1, j = 0; i >= 0; i--, j++) {
            List<Double> sublist = list.subList(0, list.size() - i);
            shortEMA = getEXPMA(sublist, shortPeriod);
            longEMA = getEXPMA(sublist, longPeriod);
            dif = shortEMA - longEMA;
            diffList.add(dif);
        }
        for (int i = diffList.size() - 1, j = 0; i >= 0; i--, j++) {
            double temp = getEXPMA(diffList.subList(0, diffList.size() - i), midPeriod);
            deaList.add(temp);
            macdList.add((diffList.get(j) - temp) * 2);
        }
        ArrayList<ArrayList<Double>> result = new ArrayList<ArrayList<Double>>();
        result.add(diffList);
        result.add(deaList);
        result.add(macdList);
        return result;
    }

}
