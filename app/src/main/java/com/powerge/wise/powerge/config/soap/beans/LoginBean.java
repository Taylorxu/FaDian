package com.powerge.wise.powerge.config.soap.beans;


import org.simpleframework.xml.Attribute;
import org.simpleframework.xml.Element;
import org.simpleframework.xml.Root;

/**
 * Created by Administrator on 2018/2/5.
 */
@Root
public class LoginBean {

    /**
     * total : 0
     * userId : ccfccb069
     */

    private int total;
    private String userId;

    public int getTotal() {
        return total;
    }

    public void setTotal(int total) {
        this.total = total;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    /** */
    public static LoginBean newInstance() {
        LoginBean fragment = new LoginBean();
        return fragment;
    }


    @Attribute(name = "xmlns:n0")
    String nameSpace;

    @Element(name = "arg0")
    public String userName;
    @Element(name = "arg1")
    public String userPassward;

    public String getNameSpace() {
        return nameSpace;
    }

    public void setNameSpace(String nameSpace) {
        this.nameSpace = nameSpace;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getUserPassward() {
        return userPassward;
    }

    public void setUserPassward(String userPassward) {
        this.userPassward = userPassward;
    }


}
