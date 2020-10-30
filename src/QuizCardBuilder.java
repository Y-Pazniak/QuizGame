import javafx.stage.FileChooser;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.*;
import java.util.ArrayList;

public class QuizCardBuilder {
    public ArrayList<QuizCard> quizCards;
    public JTextArea question;
    public JTextArea answer;
    JFrame frame;

    public static void main(String[] args) {
        QuizCardBuilder qcb = new QuizCardBuilder();
        qcb.go();
    }

    public void go(){
        quizCards = new ArrayList<>();
        frame = new JFrame("Quiz Card Builder");
        JPanel panel = new JPanel();
        Font bigger = new Font("sanserif", Font.BOLD, 22);

        JLabel questionLbl = new JLabel("question");
        questionLbl.setFont(bigger);

        question = new JTextArea(6, 20);
        question.setLineWrap(true);
        question.setWrapStyleWord(true);
        question.setFont(bigger);

        JScrollPane scroll = new JScrollPane(question);
        scroll.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
        scroll.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);

        JLabel answerLbl = new JLabel("answer");
        answerLbl.setFont(bigger);

        answer = new JTextArea(6, 20);
        answer.setLineWrap(true);
        answer.setWrapStyleWord(true);
        answer.setFont(bigger);

        JScrollPane scroll1 = new JScrollPane(answer);
        scroll1.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
        scroll1.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);

        panel.add(questionLbl);
        panel.add(scroll);
        panel.add(answerLbl);
        panel.add(scroll1);

        JButton nextCard = new JButton("NextCard");
        nextCard.setFont(bigger);
        nextCard.addActionListener(new NextCardListener());
        panel.add(nextCard);

        JMenuBar menuBar = new JMenuBar();
        JMenu menu = new JMenu("File");
        JMenuItem save = new JMenuItem("Save");
        save.addActionListener(new SaveCardListener());
        JMenuItem newQuiz = new JMenuItem("New");
        newQuiz.addActionListener(new NewCardListener());
        menu.add(save);
        menu.add(newQuiz);
        menuBar.add(menu);

        frame.setJMenuBar(menuBar);
        frame.getContentPane().add(BorderLayout.CENTER, panel);
        frame.setSize(480, 600);
        frame.setVisible(true);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }

    public class NextCardListener implements ActionListener{
        @Override
        public void actionPerformed(ActionEvent e) {
            quizCards.add(new QuizCard(question.getText(), answer.getText()));
            clearCard();
        }
    }

    public class SaveCardListener implements ActionListener{
        @Override
        public void actionPerformed(ActionEvent e) {
            quizCards.add(new QuizCard(question.getText(), answer.getText()));
            JFileChooser fc = new JFileChooser();
            fc.showSaveDialog(frame);
            saveFile(fc.getSelectedFile());
        }
    }

    public class NewCardListener implements ActionListener{
        @Override
        public void actionPerformed(ActionEvent e) {
            quizCards.clear();
            clearCard();
        }
    }

    public void saveFile(File file){
        try{
            BufferedWriter bw = new BufferedWriter(new FileWriter(file));

            for(QuizCard qc : quizCards){
                bw.write(qc.getQuestion() + "//" + qc.getAnswer() + "\n");
            }
            clearCard();
            bw.close();
        }
        catch (IOException exc) {exc.printStackTrace();}
    }

    public void clearCard(){
        question.setText("");
        answer.setText("");
        question.requestFocus();
    }
}

