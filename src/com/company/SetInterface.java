package com.company;

public class SetInterface {
    private boolean allowAxis;
    private String modifyBackground;

    public SetInterface() {
        allowAxis = true;
        modifyBackground = "axis";
    }
    public SetInterface(boolean myAllowAxis, String myModifyBackground){
        allowAxis = myAllowAxis;
        modifyBackground = myModifyBackground;
    }

    public boolean isAllowAxis() {
        return allowAxis;
    }

    public void setAllowAxis(boolean allowAxis) {
        this.allowAxis = allowAxis;
    }

    public String getModifyBackground() {
        return modifyBackground;
    }

    public void setModifyBackground(String modifyBackground) {
        this.modifyBackground = modifyBackground;
    }
}
