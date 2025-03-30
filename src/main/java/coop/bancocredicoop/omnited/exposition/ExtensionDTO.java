package coop.bancocredicoop.omnited.exposition;

public class ExtensionDTO {

    private String peer;
    private String peerStatus;
    private long time;
    private String cause;

    public ExtensionDTO() {
    }

    public ExtensionDTO(String peer, String peerStatus, long time, String cause) {
        this.peer = peer;
        this.peerStatus = peerStatus;
        this.time = time;
        this.cause = cause;
    }

    public String getPeer() {
        return peer;
    }

    public void setPeer(String peer) {
        this.peer = peer;
    }

    public String getPeerStatus() {
        return peerStatus;
    }

    public void setPeerStatus(String peerStatus) {
        this.peerStatus = peerStatus;
    }

    public long getTime() {
        return time;
    }

    public void setTime(long time) {
        this.time = time;
    }

    public String getCause() {
        return cause;
    }

    public void setCause(String cause) {
        this.cause = cause;
    }
}