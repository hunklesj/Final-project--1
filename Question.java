class Question{
  private String questionText;
  private String choiceA;
  private String choiceB;
  private String choiceC;
  private String choiceD;
  private int correctAnswer;
  private int points;

  Question() {
    questionText ="";
    choiceA = "";
    choiceB = "";
    choiceC = "";
    choiceD = "";
    correctAnswer = 0;
    points = 0;

  }
  
  Question(String aQuestionText, String aChoiceA, String aChoiceB, String aChoiceC, String aChoiceD, int aCorrectAnswer, int aPoints) {
    questionText = aQuestionText;
    choiceA = aChoiceA;
    choiceB = aChoiceB;
    choiceC = aChoiceC;
    choiceD = aChoiceD;
    correctAnswer = aCorrectAnswer;
    points = aPoints;

  }
  
  String getQuestionText() {
    return questionText;
  }

  String getChoiceA() {
    return choiceA;
  }

  String getChoiceB() {
    return choiceB;
  }

  String getChoiceC() {
    return choiceC;
  }
  String getChoiceD() {
    return choiceD;
  }

  int getCorrectAnswer(){
    return correctAnswer;
  }

  int getPoints(){
    return points;
  }

}