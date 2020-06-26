package com.view;

import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.ResourceBundle;

import com.vo.Data;
import com.vo.Room;
import com.vo.Status;
import com.vo.User;

import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.stage.Stage;

public class ScoreController {
   //private static ScoreController instance;

   private static ScoreController instance;
   @FXML
   private Button out;
   @FXML
   private Label user1;
   @FXML
   private Label user2;
   @FXML
   private Label user3;
   @FXML
   private Label user4;
   @FXML
   private Label user5;
   @FXML
   private Label user6;
   @FXML
   private Label user7;
   @FXML
   private Label user8;
   @FXML
   private Label score1;
   @FXML
   private Label score2;
   @FXML
   private Label score3;
   @FXML
   private Label score4;
   @FXML
   private Label score5;
   @FXML
   private Label score6;
   @FXML
   private Label score7;
   @FXML
   private Label score8;
//   Map<String,String> map = new HashMap<String,String>();    
//   
//   Map<String,String> finalScore= map;
   @FXML
   private ArrayList<Label> nowPlayerList;

   @FXML
   private ArrayList<Label> scoreList;
   
   private Stage ScoreStage;

   
   public ScoreController() {
      instance = this;
   }
   public static ScoreController getInstance() {
      return instance;
   }
   
   
   @FXML
   public void exitbtn() {
	   Data data = new Data();
	   data.setStatus(Status.DISCONNECTION);
   }
   

   
   public void getRanking(String score) {
//      String score="oh=50,ji=30";
      Platform.runLater(()->{
         System.out.println("setScore: "+score);
         //String score = responseData.getScore();
   
      String text= score.replace("=",",");
      List<String> list = new ArrayList<String>();
      int cnt = 0;
      String[] splitStr = text.split(",");
//      for(int i=0; i<splitStr.length; i++){
////         list.add(splitStr[i]);
////         System.out.println(splitStr);
////        // if(i%2+1>=splitStr.length)	continue;
////         nowPlayerList.get(cnt).setText(list.get(cnt));
////         scoreList.get(cnt).setText(list.get(cnt+1));
////         cnt+=2;
//    	  
//    	  for(int j=0;j<nowPlayerList.size();j++) {
//            nowPlayerList.get(j).setText(list.get(i));
//            scoreList.get(j).setText(list.get(i)); 
//    	  }
//       
//      }
      
      for(int i=0; i<nowPlayerList.size(); i++){
    	  for(int j=0;j<splitStr.length;j++) {
    		  if(j%2==0) {
    			  nowPlayerList.get(i).setText(list.get(j));
    		  }else {
    			  scoreList.get(i).setText(list.get(j)); 
    		  }
          
    	  }//for end   
     }//for end 
      
      
     
    	  
    	  
//      nowPlayerList.get(0).setText(list.get(0));
//      scoreList.get(0).setText(list.get(1));
//      nowPlayerList.get(1).setText(list.get(2));
//      scoreList.get(1).setText(list.get(3));
//      nowPlayerList.get(2).setText(list.get(4));
//      scoreList.get(2).setText(list.get(5));
//      nowPlayerList.get(3).setText(list.get(6));
//      scoreList.get(3).setText(list.get(7));
//      nowPlayerList.get(4).setText(list.get(8));
//      scoreList.get(4).setText(list.get(9));
//      nowPlayerList.get(5).setText(list.get(10));
//      scoreList.get(5).setText(list.get(11));
//      nowPlayerList.get(6).setText(list.get(12));
//      scoreList.get(1).setText(list.get(13));
//      nowPlayerList.get(0).setText(list.get(14));
//      scoreList.get(1).setText(list.get(15));
//      user1.setText(list.get(0));
//      user2.setText(list.get(2));
//      user3.setText(list.get(4));
//      user4.setText(list.get(6));
//      user5.setText(list.get(8));
//      user6.setText(list.get(10));
//      user7.setText(list.get(12));
//      user8.setText(list.get(14));
      
//      score1.setText(list.get(1));
//      score2.setText(list.get(3));
//      score3.setText(list.get(5));
//      score4.setText(list.get(7));
//      score5.setText(list.get(9));
//      score6.setText(list.get(11));
//      score7.setText(list.get(13));
//      score8.setText(list.get(15));
      
      
      /*
       * score1.setText((String) finalScore.keySet().toArray()[0]);
       * score2.setText((String) finalScore.keySet().toArray()[1]);
       * score3.setText((String) finalScore.keySet().toArray()[2]);
       * score4.setText((String) finalScore.keySet().toArray()[3]);
       * score5.setText((String) finalScore.keySet().toArray()[4]);
       * score6.setText((String) finalScore.keySet().toArray()[5]);
       * score7.setText((String) finalScore.keySet().toArray()[6]);
       * score8.setText((String) finalScore.keySet().toArray()[7]);
       */
      
      });
   }
   
   
   
   
   

   
}