public class Bed {
    private String bedID;
    private String ward;
    private String status;

    public Bed(String bedID, String ward, String status) {
        this.bedID = bedID;
        this.ward = ward;
        this.status = status;
    }

    public String getBedID() { return bedID; }
    public String getWard() { return ward; }
    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }

    public String toFileString() {
        return bedID + "," + ward + "," + status;
    }
}