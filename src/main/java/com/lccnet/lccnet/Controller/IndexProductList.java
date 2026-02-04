package com.lccnet.lccnet.Controller;

import java.util.ArrayList;
import java.util.List;

public class IndexProductList {

    List<IndexProduct> wheyList = new ArrayList<>();
    List<IndexProduct> bcaaList = new ArrayList<>();

    public List<IndexProduct> getWheyList() {
        return wheyList;
    }

    public List<IndexProduct> getBcaaList() {
        return bcaaList;
    }

    public void setWheyList(List<IndexProduct> wheylist) {
        this.wheyList = wheylist;
    }

    public void setBcaaList(List<IndexProduct> bcaaList) {
        this.bcaaList = bcaaList;
    }

}
