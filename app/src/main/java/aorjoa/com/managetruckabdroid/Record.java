package aorjoa.com.managetruckabdroid;

/**
 * Created by AorJoa on 2/3/2017.
 */
public class Record {
    private String ctId;
    private String ctName;
    private String ctPhone;
    private String ctAddress;
    private String dateOp;
    private String hvName;
    private String hvArea;
    private String hvPriceArea;
    private String hvAddress;
    private String bhName;
    private String bhHours;
    private String bhPriceHours;
    private String trName;
    private String trNum;
    private String trPriceNum;
    private String ttName;
    private String ttNum;
    private String ttPriceNum;
    private String price;
    private String recorder;


    //Constructor
    public Record( String ctId,
                   String ctName,
                   String ctPhone,
                   String ctAddress,
                   String dateOp,
                   String hvName,
                   String hvArea,
                   String hvPriceArea,
                   String hvAddress,
                   String bhName,
                   String bhHours,
                   String bhPriceHours,
                   String trName,
                   String trNum,
                   String trPriceNum,
                   String ttName,
                   String ttNum,
                   String ttPriceNum,
                   String price,
                   String recorder
                   ) {

        this.ctId = ctId;
        this.ctName = ctName;
        this.ctPhone = ctPhone;
        this.ctAddress = ctAddress;
        this.dateOp = dateOp;
        this.hvName = hvName;
        this.hvArea = hvArea;
        this.hvPriceArea = hvPriceArea;
        this.hvAddress = hvAddress;
        this.bhName = bhName;
        this.bhHours = bhHours;
        this.bhPriceHours = bhPriceHours;
        this.trName = trName;
        this.trNum = trNum;
        this.trPriceNum = trPriceNum;
        this.ttName = ttName;
        this.ttNum = ttNum;
        this.ttPriceNum = ttPriceNum;
        this.price = price;
        this.recorder = recorder;

    }
    public String getCtId() {
        return ctId;
    }

    public void setCtId(String ctId) {
        this.ctId = ctId;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }
    public String getCtName() {
        return ctName;
    }

    public void setCtName(String ctName) {
        this.ctName = ctName;
    }

    public String getCtPhone() {
        return ctPhone;
    }

    public void setCtPhone(String ctPhone) {
        this.ctPhone = ctPhone;
    }

    public String getCtAddress() {
        return ctAddress;
    }

    public void setCtAddress(String ctAddress) {
        this.ctAddress = ctAddress;
    }

    public String getDateOp() {
        return dateOp;
    }

    public void setDateOp(String dateOp) {
        this.dateOp = dateOp;
    }

    public String getHvName() {
        return hvName;
    }

    public void setHvName(String hvName) {
        this.hvName = hvName;
    }

    public String getHvArea() {
        return hvArea;
    }

    public void setHvArea(String hvArea) {
        this.hvArea = hvArea;
    }

    public String getHvPriceArea() {
        return hvPriceArea;
    }

    public void setHvPriceArea(String hvPriceArea) {
        this.hvPriceArea = hvPriceArea;
    }

    public String getHvAddress() {
        return hvAddress;
    }

    public void setHvAddress(String hvAddress) {
        this.hvAddress = hvAddress;
    }

    public String getBhName() {
        return bhName;
    }

    public void setBhName(String bhName) {
        this.bhName = bhName;
    }

    public String getBhHours() {
        return bhHours;
    }

    public void setBhHours(String bhHours) {
        this.bhHours = bhHours;
    }

    public String getBhPriceHours() {
        return bhPriceHours;
    }

    public void setBhPriceHours(String bhPriceHours) {
        this.bhPriceHours = bhPriceHours;
    }

    public String getTrName() {
        return trName;
    }

    public void setTrName(String trName) {
        this.trName = trName;
    }

    public String getTrNum() {
        return trNum;
    }

    public void setTrNum(String trNum) {
        this.trNum = trNum;
    }

    public String getTrPriceNum() {
        return trPriceNum;
    }

    public void setTrPriceNum(String trPriceNum) {
        this.trPriceNum = trPriceNum;
    }

    public String getTtName() {
        return ttName;
    }

    public void setTtName(String ttName) {
        this.ttName = ttName;
    }

    public String getTtNum() {
        return ttNum;
    }

    public void setTtNum(String ttNum) {
        this.ttNum = ttNum;
    }

    public String getTtPriceNum() {
        return ttPriceNum;
    }

    public void setTtPriceNum(String ttPriceNum) {
        this.ttPriceNum = ttPriceNum;
    }

    public String getRecorder() {
        return recorder;
    }

    public void setRecorder(String recorder) {
        this.recorder = recorder;
    }
}