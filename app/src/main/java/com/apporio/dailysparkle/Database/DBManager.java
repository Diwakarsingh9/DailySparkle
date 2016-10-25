package com.apporio.dailysparkle.Database;

import android.content.Context;
import android.util.Log;
import android.widget.Toast;
import io.realm.Realm;
import io.realm.RealmResults;


/**
 * Created by samir on 10/07/15.
 */
public class DBManager {

    public static PdfTable ct ;

    public  Realm myRealm;

    public static Context con ;

   // public static EventBus bus = EventBus.getDefault() ;

    public DBManager(Context con){
        this.con = con ;

        myRealm = Realm.getInstance(con);
    }

    public   void addtocart(String pdfname,  String pdflocation
             ){

        if(checkproductExsistance(pdfname)){
            ////change already saved details in database
//            changeExsistingRowintable(productId,quantity,optionsValue);
        }else {
            /////  create new row in database
            createnreRowintable( pdfname,pdflocation);
        }

    }


    private  void createnreRowintable(String pdfname, String pdflocation) {
        Log.e("LOCAtion","location   ---"+""+pdflocation);
//        new CartTable(ProductId,
//                rest_id,
//                food_name,
//                foodtype,
//                foodprice,foodimage,foodrating,food_no_of_units,toppingprice).save();
//
//        updateTotalOnActionBar();
//        showdataoncart();

        myRealm.beginTransaction();
        PdfTable pd = myRealm.createObject(PdfTable.class);

        pd.setPdfname(pdfname);
        pd.setPdflocation(pdflocation);

        myRealm.commitTransaction();
       // countNoofRowsInDatabse();

    }

    private static void updateTotalOnActionBar() {
       /* if(ActivityDetector.openActivity.equals("MainActivity")){
            MainActivity.showgrossOnCart(calculationForGrossPrice());
        }if(ActivityDetector.openActivity.equals("CartActivity")){
            CartActivity.showgrossOnCartCartActivity(calculationForGrossPrice());
        }   */


    }




//    public  void changeExsistingRowintable(String productid, String quantity, String optionsvalue) {
//        Log.e("hfdhg", "nounits   ---" + quantity);
//        CartTable tobechangedelement =
//                myRealm.where(CartTable.class)
//                        .equalTo("productid", ""+productid)
//                        .equalTo("Option", optionsvalue)
//                        .findFirst();
//
//        Log.e("change",""+productid+" "+quantity+" "+optionsvalue);
//        myRealm.beginTransaction();
//        tobechangedelement.setproductid(productid);
//        tobechangedelement.setQuantity(quantity);
//        tobechangedelement.setOption(optionsvalue);
//
//        myRealm.commitTransaction();
////        updateTotalOnActionBar();
////        showdataoncart();
//    }




    public  void removeItemfromDB(Context ctc, String pdfname){

        PdfTable tobechangedelement =
                myRealm.where(PdfTable.class).equalTo("Pdfname", "" + pdfname)
                .findFirst();


        myRealm.beginTransaction();
        tobechangedelement.removeFromRealm();


        myRealm.commitTransaction();

    }






    public RealmResults<PdfTable> getFullTable(){
        RealmResults<PdfTable> results = myRealm.where(PdfTable.class).findAll();
        return  results ;
    }







    public  void clearCartTable(){
        myRealm.beginTransaction();
        myRealm.allObjects(PdfTable.class).clear();
//      myRealm.where(CartTable.class).findAll().clear();
        myRealm.commitTransaction();
//        showdataoncart();
    }

    public static void showdataoncart (){
//        CartEvent event = null ;
//        event = new CartEvent(""+totalNoofitemsincar() , ""+calculationForGrossPrice());
//        bus.post(event);

    }




    public  boolean checkproductExsistance(String pdfname){

        boolean value  = false;



               if( myRealm.where(PdfTable.class)
                        .equalTo("Pdfname", ""+ pdfname)
                        .count()==0)
     {
            value = false ;
        }else {
            value = true ;
        }
        Log.e("valuesscheck",""+value);
        return  value ;

    }


}
