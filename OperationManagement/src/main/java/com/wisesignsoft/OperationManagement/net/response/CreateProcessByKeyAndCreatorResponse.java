package com.wisesignsoft.OperationManagement.net.response;

/**
 * Created by ycs on 2016/11/29.
 */

public class CreateProcessByKeyAndCreatorResponse extends BaseResponse {
    /**
     * state  : false
     * message  :
     * result :
     */

    private boolean state;
    private String PIKEY;
    private String CURRENT_TASKID;
    private String firstrequest;
    private String taskNodeType;
    private String formDocument;

    public boolean isState() {
        return state;
    }

    public void setState(boolean state) {
        this.state = state;
    }

    public String getPIKEY() {
        return PIKEY;
    }

    public void setPIKEY(String PIKEY) {
        this.PIKEY = PIKEY;
    }

    public String getCURRENT_TASKID() {
        return CURRENT_TASKID;
    }

    public void setCURRENT_TASKID(String CURRENT_TASKID) {
        this.CURRENT_TASKID = CURRENT_TASKID;
    }

    public String getFirstrequest() {
        return firstrequest;
    }

    public void setFirstrequest(String firstrequest) {
        this.firstrequest = firstrequest;
    }

    public String getTaskNodeType() {
        return taskNodeType;
    }

    public void setTaskNodeType(String taskNodeType) {
        this.taskNodeType = taskNodeType;
    }

    public String getFormDocument() {
        return formDocument;
    }

    public void setFormDocument(String formDocument) {
        this.formDocument = formDocument;
    }

    @Override
    public String toString() {
        return "CreateProcessByKeyAndCreatorResponse{" +
                "state=" + state +
                ", PIKEY='" + PIKEY + '\'' +
                ", CURRENT_TASKID='" + CURRENT_TASKID + '\'' +
                ", firstrequest='" + firstrequest + '\'' +
                ", taskNodeType='" + taskNodeType + '\'' +
                ", formDocument='" + formDocument + '\'' +
                '}';
    }
}