package Models;

public class ReportData {
    public int size;
    public ReportModel[] dataModels;
    public String string;

    public ReportData(int sizeOfSet, int numSets) {
        this.size = sizeOfSet;
        this.dataModels = new ReportModel[numSets];
    }
}
