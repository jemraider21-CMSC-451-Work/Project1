package Models;

public class ReportData {
    public int size;
    public ReportModel[] data;
    public String string;

    public ReportData(int sizeOfSet, int numSets){
        this.size = size;
        data = new ReportModel[numSets];
    }
}
