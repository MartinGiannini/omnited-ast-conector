package coop.bancocredicoop.omnited.exposition;

public class NewStateDTO {
    
    private String peer;
    private String state;
    private String callee;
    private long time;

    public NewStateDTO() {
    }

    public NewStateDTO(String peer, String state, String callee, long time) {
        this.peer = peer;
        this.state = state;
        this.callee = callee;
        this.time = time;
    }

    public String getPeer() {
        return peer;
    }

    public void setPeer(String peer) {
        this.peer = peer;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getCallee() {
        return callee;
    }

    public void setCallee(String callee) {
        this.callee = callee;
    }

    public long getTime() {
        return time;
    }

    public void setTime(long time) {
        this.time = time;
    }
}
