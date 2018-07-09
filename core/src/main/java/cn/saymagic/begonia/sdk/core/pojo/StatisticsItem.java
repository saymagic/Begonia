package cn.saymagic.begonia.sdk.core.pojo;

public class StatisticsItem {


    /**
     * total : 1275
     * historical : {"change":242,"resolution":"days","quantity":30,"values":[{"date":"2017-02-27","value":16},{"date":"2017-02-28","value":17},{"date":"2017-03-01","value":26},{"date":"2017-03-02","value":17},{"date":"2017-03-03","value":20},{"date":"2017-03-04","value":15},{"date":"2017-03-05","value":15},{"date":"2017-03-06","value":22},{"date":"2017-03-07","value":18},{"date":"2017-03-08","value":15},{"date":"2017-03-09","value":5},{"date":"2017-03-10","value":2},{"date":"2017-03-11","value":8},{"date":"2017-03-12","value":2},{"date":"2017-03-13","value":4},{"date":"2017-03-14","value":3},{"date":"2017-03-15","value":14},{"date":"2017-03-16","value":1},{"date":"2017-03-17","value":0},{"date":"2017-03-18","value":0},{"date":"2017-03-19","value":0},{"date":"2017-03-20","value":0},{"date":"2017-03-21","value":6},{"date":"2017-03-22","value":15},{"date":"2017-03-23","value":1},{"date":"2017-03-24","value":0},{"date":"2017-03-25","value":0},{"date":"2017-03-26","value":0},{"date":"2017-03-27","value":0},{"date":"2017-03-28","value":0}]}
     */

    private int total;
    private Historical historical;

    public int getTotal() {
        return total;
    }

    public void setTotal(int total) {
        this.total = total;
    }

    public Historical getHistorical() {
        return historical;
    }

    public void setHistorical(Historical historical) {
        this.historical = historical;
    }


}
