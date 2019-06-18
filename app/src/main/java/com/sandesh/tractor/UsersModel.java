package com.sandesh.tractor;

class UsersModel {
    private String name, mobile;
    private long customer_id;
    private String  workname, worktime, workdate, totalamount, paidamount, reaminamount;



     String getWorkname() {
        return workname;
    }


     String getWorktime() {
        return worktime;
    }


    String getWorkdate() {
        return workdate;
    }


     String getTotalamount() {
        return totalamount;
    }


     String getPaidamount() {
        return paidamount;
    }


     String getReaminamount() {
        return reaminamount;
    }


    UsersModel(long customer_id, String name, String mobile) {
        this.customer_id = customer_id;
        this.name = name;
        this.mobile = mobile;

    }


    UsersModel( String workname, String worktime, String workdate, String totalamount, String paidamount, String reaminamount) {
        this.workname = workname;
        this.worktime = worktime;
        this.workdate = workdate;
        this.totalamount = totalamount;
        this.paidamount = paidamount;
        this.reaminamount = reaminamount;
    }

     long getCustomer_id() {
        return customer_id;
    }


    public String getName() {
        return name;
    }

    String getMobile() {
        return mobile;
    }


}
