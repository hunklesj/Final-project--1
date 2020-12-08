import java.awt.*; 
import java.awt.event.*; 
import javax.swing.*; 
import java.util.ArrayList;
import java.io.*;

class Game implements ActionListener {
  JLabel jlabWelcome, jlabNamePrompt;
  JTextField field;
  JLabel jlabQAndP;
  JLabel jlabCurrentScore;
  JButton jButNextQuestion, jButRestartGame, jButSubmitName, jButSubmitScore;
  JLabel prompt;
  ArrayList<Question> QuestionList;
  ArrayList<JButton> AnswerButtonList;
  JFrame frame;
  JPanel panel1, panel2, panel3, panel4, panel5, panel6;
  JPanel jPanQuestions, jPanControls, jPanMaster;
  int index, score;
  String strPlayerName;

  Game(){
    index = 0;
    score = 0;
    strPlayerName = "";
    AnswerButtonList = new ArrayList<JButton>();

    QuestionList = new ArrayList<Question>();
    try {
     FileReader myFile = new FileReader("Data.txt");
     BufferedReader reader = new BufferedReader(myFile);

      while (reader.ready()) {
        for (int i = 0;i<=4; i++) {
          String questionText = reader.readLine();
          String choiceA = reader.readLine();
          String choiceB = reader.readLine();
          String choiceC = reader.readLine();
          String choiceD = reader.readLine();
          String correctAnswerstr = reader.readLine();
          String pointsstr = reader.readLine();

          int correctAnswer = Integer.parseInt(correctAnswerstr);
          int points = Integer.parseInt(pointsstr);

          Question q = new Question(questionText, choiceA, choiceB, choiceC, choiceD, correctAnswer, points);
          QuestionList.add(q);
        }
     }
      reader.close();
    } 

    catch (IOException exception) {
      System.out.println("An error occurred: " + exception);
    }

    frame = new JFrame("Trivia Game");
    frame.setLayout(new BorderLayout());
    frame.setSize(750, 250);
    frame.setBackground(Color.RED);

    jlabWelcome = new JLabel("Group number 2 trivia game! ");
    
    jlabNamePrompt = new JLabel("Please Enter Your Name:");
    jButSubmitName= new JButton("Submit Name");
    jButSubmitName.addActionListener(this);
    field = new JTextField(10); 
    field.setActionCommand("myTF");
    field.addActionListener(this);

    jlabQAndP = new JLabel(QuestionList.get(index).getQuestionText()+ " (Point: " + QuestionList.get(index).getPoints()+ ")");
    
    prompt = new JLabel("");
  
    AnswerButtonList.add(new JButton(QuestionList.get(index).getChoiceA()));
    AnswerButtonList.add(new JButton(QuestionList.get(index).getChoiceB()));
    AnswerButtonList.add(new JButton(QuestionList.get(index).getChoiceC()));
    AnswerButtonList.add(new JButton(QuestionList.get(index).getChoiceD()));
    
    jlabCurrentScore = new JLabel("Score: " + score);
    
    jButNextQuestion= new JButton("Next Question");
    jButNextQuestion.addActionListener(this); 
   
    jButRestartGame= new JButton("Restart the Game");
    jButRestartGame.addActionListener(this); 
    
    jButSubmitScore= new JButton("Submit your Score");
    jButSubmitScore.addActionListener(this); 


    panel1 = new JPanel(new FlowLayout());
    panel2 = new JPanel(new FlowLayout());
    panel3 = new JPanel(new FlowLayout());
    panel4 = new JPanel(new FlowLayout());
    panel5 = new JPanel(new FlowLayout());
    panel6 = new JPanel(new FlowLayout());
    jPanMaster = new JPanel(new FlowLayout());

    panel1.add(jlabWelcome);

    panel2.add(jlabNamePrompt);
    panel2.add(field);
    panel2.add(jButSubmitName);
    
    panel3.add(jlabQAndP);
    
    for (int i = 0; i < 4; i++) {
      AnswerButtonList.get(i).addActionListener(this);
      panel4.add(AnswerButtonList.get(i));
    }

    panel5.add(prompt);
    panel5.add(jlabCurrentScore);

    panel6.add(jButNextQuestion);
    panel6.add(jButSubmitScore);
    jButSubmitScore.setVisible(false);
    panel6.add(jButRestartGame);
    jButRestartGame.setBackground(Color.RED);
    jButRestartGame.setForeground(Color.WHITE);
    jButRestartGame.setFont(new Font("Monospaced", Font.BOLD, 15));
    jPanMaster.add(panel1);
    jlabWelcome.setFont(new Font("Monospaced", Font.BOLD, 20));
    jPanMaster.add(panel2);
    jPanMaster.add(panel3);
    jPanMaster.add(panel4);
    jPanMaster.add(panel5);
    jPanMaster.add(panel6);

    frame.add(jPanMaster);
    
    frame.setVisible(true); 

  }

   public void actionPerformed(ActionEvent ae) { 
      String answerForQuestion = AnswerButtonList.get(QuestionList.get(index).getCorrectAnswer()-1).getText();
      System.out.println(answerForQuestion);
      
      if(ae.getActionCommand().equals(answerForQuestion)) {
        prompt.setText("You are correct");
        score+=QuestionList.get(index).getPoints();
        jlabCurrentScore.setText("score: " + score);
        nextQuestion();
            
      }
      else if(ae.getActionCommand().equals("Next Question")) {
        prompt.setText("");
        nextQuestion();
      }
      else if(ae.getActionCommand().equals("Restart Game")) {
        prompt.setText("");
        index = 0;
        score = 0;
        strPlayerName="";
        jlabNamePrompt.setText("Please Enter Your Name:");
        field.setVisible(true);
        field.setText("");
        jButSubmitName.setVisible(true);
        jlabCurrentScore.setText("score: " + score);
        jButNextQuestion.setVisible(true);
        jButSubmitScore.setVisible(false);
        panel1.setVisible(true);
        panel2.setVisible(true);
        panel3.setVisible(true);
        panel4.setVisible(true);
        panel5.setVisible(true);
        panel6.setVisible(true);
      }
      else if(ae.getActionCommand().equals("Submit Name")){
        strPlayerName = field.getText();
        jlabNamePrompt.setText("Welcome Player: "+ strPlayerName);
        field.setVisible(false);
        jButSubmitName.setVisible(false);
      }
      else if(ae.getActionCommand().equals("Submit Score")){
        recordScore();  
      }
      else {
        prompt.setText("Wrong Answer");
        nextQuestion();
      }
  }

  void recordScore(){

    FileWriter toWriteFile;
    try{
      toWriteFile = new FileWriter("Savedscores.txt",true);
      BufferedWriter output = new BufferedWriter(toWriteFile);
      output.write(strPlayerName);
      output.newLine();
      output.write(Integer.toString(score));
      output.newLine();
      output.flush();
      jButSubmitScore.setVisible(false);
    }
    catch (IOException excpt) { 
      excpt.printStackTrace(); 
    } 

  }

  void nextQuestion(){
      if (index<4){
        index = index + 1;
        System.out.println(index);

      }
      else{
        prompt.setText("Game Over! Player " + strPlayerName + " ");
        panel1.setVisible(false);
        panel2.setVisible(false);
        panel3.setVisible(false);
        panel4.setVisible(false);
        jButNextQuestion.setVisible(false);
        jButSubmitScore.setVisible(true);
         
      }

        jlabQAndP.setText(QuestionList.get(index).getQuestionText()+ " (Point: " + QuestionList.get(index).getPoints()+ ")");
        AnswerButtonList.get(0).setText(QuestionList.get(index).getChoiceA());
        AnswerButtonList.get(1).setText(QuestionList.get(index).getChoiceB());
        AnswerButtonList.get(2).setText(QuestionList.get(index).getChoiceC());
        AnswerButtonList.get(3).setText(QuestionList.get(index).getChoiceD());
    }
  } 

