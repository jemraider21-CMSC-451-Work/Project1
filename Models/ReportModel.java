package Models;

import java.util.Scanner;

public class ReportModel {
    public int count;
    public long time;

    public ReportModel() {
        this(0, 0);
    }

    public ReportModel(int count, long time) {
        this.count = count;
        this.time = time;
    }

    public String toString() {
        return String.format("{%d,%d}", count, time);
    }

    public void convertFromString(String data){
        data.replace("{", "");
        data.replace("}", "");
        Scanner convertToken = new Scanner(data);
        convertToken.useDelimiter(",");
        
    }
}