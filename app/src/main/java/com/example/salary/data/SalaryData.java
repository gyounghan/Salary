package com.example.salary.data;

import java.util.ArrayList;

public class SalaryData {

    public static SalaryData salaryData = null;

    private SalaryData() {

    }

    public static SalaryData getInstance() {
        if (salaryData == null) {
            salaryData = new SalaryData();
        }
        return salaryData;
    }

    private ArrayList<CompanyData> companyList  = new ArrayList<CompanyData>();

    public void addArrayList(CompanyData companyInfo) {
        companyList.add(companyInfo);
    }

    public void printArrayList() {
        for(int i=0;i<companyList.size();i++) {
            System.out.println(companyList.get(i).getCompanyName() + " " + companyList.get(i).getCompanyAddress() + " " + companyList.get(i).getCompanyType());
        }
    }

    public ArrayList<String> getCompanyNameList() {
        ArrayList<String> companyNameList = new ArrayList<String>();

        for (int i=0;i<companyList.size();i++) {
            companyNameList.add(companyList.get(i).getCompanyName());
        }
        return companyNameList;
    }

    public ArrayList<String> getCompanyAddressList() {
        ArrayList<String> companyAddressList = new ArrayList<String>();

        for (int i=0;i<companyList.size();i++) {
            companyAddressList.add(companyList.get(i).getCompanyAddress());
        }
        return companyAddressList;
    }

    public ArrayList<String> getCompanyTypeList() {
        ArrayList<String> companyTypeList = new ArrayList<String>();

        for (int i=0;i<companyList.size();i++) {
            companyTypeList.add(companyList.get(i).getCompanyType());
        }
        return companyTypeList;
    }

    public void setCompanyList(ArrayList<CompanyData> companyList) {
        this.companyList = companyList;
    }

    public ArrayList<CompanyData> getCompanyList() {
        return companyList;
    }


    public ArrayList<CompanyData> getCentralCompanyList() {
        ArrayList<CompanyData> centralcompanyList = new ArrayList<CompanyData>();

        for (int i=0;i<companyList.size();i++) {
            if (companyList.get(i).getCompanyType().equals("central")) {
                centralcompanyList.add(companyList.get(i));
            }
        }
        return centralcompanyList;
    }

    public ArrayList<CompanyData> getLocalCompanyList() {
        ArrayList<CompanyData> localcompanyList = new ArrayList<CompanyData>();

        for (int i=0;i<companyList.size();i++) {
            if (companyList.get(i).getCompanyType().equals("local")) {
                localcompanyList.add(companyList.get(i));
            }
        }
        return localcompanyList;
    }
}
