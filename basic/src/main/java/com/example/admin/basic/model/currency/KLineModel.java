package com.example.admin.basic.model.currency;

import com.example.admin.basic.stock.DayData;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * @author Xinxin Shi
 */

public class KLineModel {


    private int code;
    private String msg;
    private DataBean data;
    private DayData dayData;
    private ArrayList<Double> closeArray;

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public DataBean getData() {
        return data;
    }

    public void setData(DataBean data) {
        this.data = data;
    }

    public static class DataBean {


        private String last;
        private int klineType;
        private List<KlineBean> kline;

        public String getLast() {
            return last;
        }

        public void setLast(String last) {
            this.last = last;
        }

        public int getKlineType() {
            return klineType;
        }

        public void setKlineType(int klineType) {
            this.klineType = klineType;
        }

        public List<KlineBean> getKline() {
            return kline;
        }

        public void setKline(List<KlineBean> kline) {
            this.kline = kline;
        }

        public static class KlineBean {
            /**
             * open : 0.007314
             * close : 0.00731
             * high : 0.007316
             * low : 0.00731
             * time : 2018-05-25 05
             */

            private String open;
            private String close;
            private String high;
            private String low;
            private String time;

            public String getOpen() {
                return open;
            }

            public void setOpen(String open) {
                this.open = open;
            }

            public String getClose() {
                return close;
            }

            public void setClose(String close) {
                this.close = close;
            }

            public String getHigh() {
                return high;
            }

            public void setHigh(String high) {
                this.high = high;
            }

            public String getLow() {
                return low;
            }

            public void setLow(String low) {
                this.low = low;
            }

            public String getTime() {
                return time;
            }

            public void setTime(String time) {
                this.time = time;
            }
        }
    }

    public DayData parseData() {
//        todo 添加volume参数
        String volume = "0.12648834";
        dayData = new DayData();
        List<List<Double>> outData = new ArrayList<>();
        List<String> time = new ArrayList<>();
        closeArray = new ArrayList<>();
        List<DataBean.KlineBean> data = getData().getKline();
        for (int i = 0; i < data.size(); i++) {
            time.add(data.get(i).getTime());
            List<Double> one = new ArrayList<>();
            one.add(Double.parseDouble(data.get(i).getOpen()));
            one.add(Double.parseDouble(data.get(i).getClose()));
            one.add(Double.parseDouble(data.get(i).getHigh()));
            one.add(Double.parseDouble(data.get(i).getLow()));
            one.add(Double.parseDouble(volume));
            if (i == 0) {
                one.add(Double.parseDouble(getData().getLast()));
            } else {
                one.add(Double.parseDouble(data.get(i - 1).getClose()));
            }
            one.add(100.0);
            one.add(100.0);
            one.add(100.0);
            one.add(0.0);
            one.add(0.0);
            one.add(0.0);
            closeArray.add(Double.parseDouble(data.get(i).getClose()));
            outData.add(one);
        }
        getKDJMap(outData);
        ArrayList<ArrayList<Double>> result = getMACD(closeArray, 12, 26, 9);
        int i = 9;
        for (ArrayList<Double> one : result) {
            int j = 0;
            for (Double value : one) {
                outData.get(j).set(i, value);
                j++;
            }
            i++;
        }
        dayData.setTimes(time);
        dayData.setKData(outData);
        return dayData;
    }


    public void getKDJMap(List<List<Double>> m_kData) {
        // 默认随机值
        int m_iParam[] = {9, 3, 3};
        int n1 = m_iParam[0];
        int n2 = m_iParam[1];
        int n3 = m_iParam[2];
        if (m_kData == null || n1 > m_kData.size() || n1 < 1) return;
        List<Double> model = m_kData.get(n1 - 4);
//        // 计算N日内的最低最高价
        float maxhigh = model.get(2).floatValue(); // 最高价
        float minlow = model.get(3).floatValue(); // 最低价
        for (int j = 0; j < n1 - 4; j++) {
            List<Double> m = m_kData.get(j);
            if (maxhigh < m.get(2).floatValue()) maxhigh = m.get(2).floatValue();
            if (minlow > m.get(3).floatValue()) minlow = m.get(3).floatValue();
        }
        for (int i = n1 - 3; i < m_kData.size(); i++) {
            List<Double> temp = m_kData.get(i);
            maxhigh = temp.get(2).floatValue();
            minlow = temp.get(3).floatValue();
            for (int j = i - 1; j > i - n1 && j >= 0; j--) {
                List<Double> temp2 = m_kData.get(j);
                if (maxhigh < temp2.get(2).floatValue()) maxhigh = temp2.get(2).floatValue();
                if (minlow > temp2.get(3).floatValue()) minlow = temp2.get(3).floatValue();
            }
            float rsv = ((temp.get(1).floatValue() - minlow) / (maxhigh - minlow)) * 100.0f;


            double newK = (m_kData.get(i - 1).get(6).floatValue() * (float) (n2 - 1)) / (float)
                    n2 + rsv / (float) n2;
            newK = newK < 0 ? 0 : newK;
            newK = newK > 100 ? 100 : newK;
            System.out.println(newK);
            m_kData.get(i).set(6, newK);
            // 计算D值
            double newD = m_kData.get(i).get(6).floatValue() / (float) n3 + (m_kData.get(i - 1)
                    .get(7).floatValue() * (float) (n3 - 1)) / (float) n3;
            newD = newD < 0 ? 0 : newD;
            newD = newD > 100 ? 100 : newD;
            m_kData.get(i).set(7, newD);
            // 计算J值
            double newJ = m_kData.get(i).get(6).floatValue() * 3.0f - 2.0f * m_kData.get(i).get
                    (7).floatValue();
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

    public static final ArrayList<ArrayList<Double>> getMACD(final List<Double> list, final int
            shortPeriod, final int longPeriod, int midPeriod) {
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
