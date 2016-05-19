package com.bhz.android.caiyoubang.info;

/**
 * Created by Administrator on 2016/4/28.
 */
public class MenuStepInfo {
    private String stepImage;
    private String stepText;

    public MenuStepInfo(String stepImage,String stepText) {
        this.stepImage = stepImage;
        this.stepText = stepText;
    }

    public String getStepImage() {
        return stepImage;
    }

    public void setStepImage(String stepImage) {
        this.stepImage = stepImage;
    }

    public String getStepText() {
        return stepText;
    }

    public void setStepText(String stepText) {
        this.stepText = stepText;
    }
}
