package com.apporio.dailysparkle.Database;



import io.realm.RealmObject;

/**
 * Created by samir on 10/07/15.
 */
public class PdfTable extends RealmObject {


    private String Pdfname ;
    private String Pdflocation;











    public String getPdfname() {
        return Pdfname;
    }

    public void setPdfname(String pdfname) {
        Pdfname = pdfname;
    }



    public String getPdflocation() {
        return Pdflocation;
    }

    public void setPdflocation(String pdflocation) {
        Pdflocation = pdflocation;
    }













}
