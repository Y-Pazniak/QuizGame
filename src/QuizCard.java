public class QuizCard {
    String question;
    String answer;


    public QuizCard(String answer, String question){
        this.question = question;
        this.answer = answer;
    }

    public String getAnswer(){
        return answer;
    }

    public String getQuestion(){
        return question;
    }
}
