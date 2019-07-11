package com.fotg.keepingcool.models;

import com.google.firebase.database.PropertyName;

public class Tags {
    private boolean chip_fashion;
    private boolean chip_waste;
    private boolean chip_oceans;
    private boolean chip_rainforest;
    private boolean chip_carbon;
    private boolean chip_diet;

    @PropertyName("Fashion")
    public boolean getChip_fashion() { return chip_fashion; }
    public void setChip_fashion(boolean a) { chip_fashion = a; }

    @PropertyName("Waste")
    public boolean getChip_waste() { return chip_waste; }
    public void setChip_waste(boolean a) { chip_waste = a; }

    @PropertyName("Oceans")
    public boolean getChip_oceans() { return chip_oceans; }
    public void setChip_oceans(boolean a) { chip_oceans = a; }

    @PropertyName("Rainforest")
    public boolean getChip_rainforest() { return chip_rainforest; }
    public void setChip_rainforest(boolean a) { chip_rainforest = a; }

    @PropertyName("Carbon")
    public boolean getChip_carbon() { return chip_carbon; }
    public void setChip_carbon(boolean a) { chip_carbon = a; }

    @PropertyName("Diet")
    public boolean getChip_diet() { return chip_diet; }
    public void setChip_diet(boolean a) { chip_diet = a; }

    public Tags() {
        chip_fashion = false;
        chip_waste = false;
        chip_oceans = false;
        chip_rainforest = false;
        chip_carbon = false;
        chip_diet = false;
    }

    public Tags(Boolean a, Boolean b, Boolean c, Boolean d, Boolean e, Boolean f) {
        chip_fashion = a;
        chip_waste = b;
        chip_oceans = c;
        chip_rainforest = d;
        chip_carbon = e;
        chip_diet = f;
    }
}
