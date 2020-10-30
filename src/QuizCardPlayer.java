import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.*;
import java.util.ArrayList;

public class QuizCardPlayer {
    ArrayList<QuizCard> quizCards;
    JTextArea text;
    JFrame frame;
    JButton nextButton;
    boolean showAnswer = true;
    QuizCard qurrentCard;
    int currentCardIndex = 0;

    public static void main(String[] args) {
        QuizCardPlayer qcp = new QuizCardPlayer();
        qcp.go();
    }

    public void go(){
        quizCards = new ArrayList<>();
        frame = new JFrame("Quiz Card Player");
        JPanel panel = new JPanel();
        Font bigger = new Font("san-serif", Font.BOLD, 24);

        text = new JTextArea(10,20);
        text.setFont(bigger);
        text.setLineWrap(true);
        text.setWrapStyleWord(true);
        text.setEditable(false);

        JScrollPane jsp = new JScrollPane(text);
        jsp.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
        jsp.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
        nextButton = new JButton("Show Question");
        panel.add(jsp);
        panel.add(nextButton);
        nextButton.addActionListener(new NextCardListener());
        nextButton.setFont(bigger);

        JMenuBar jmb = new JMenuBar();
        JMenu menu = new JMenu("File");
        JMenuItem first = new JMenuItem("Load");
        first.addActionListener(new LoadListener());
        menu.add(first);
        jmb.add(menu);
        frame.setJMenuBar(jmb);
        frame.getContentPane().add(BorderLayout.CENTER, panel);
        frame.setVisible(true);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(500,600);
    }

    public class NextCardListener implements ActionListener{
        @Override
        public void actionPerformed(ActionEvent e) {
            if(showAnswer){
                text.setText(qurrentCard.getAnswer());
                nextButton.setText("Next question");
                showAnswer = false;
            } else {
                if(currentCardIndex < quizCards.size()) {
                    showNextCard();
                }
                else {
                    text.setText("There are no more cards here");
                    nextButton.setText("Again");
                    currentCardIndex = 0;
                }
            }
        }
    }

    public void showNextCard(){
        qurrentCard = quizCards.get(currentCardIndex);
        currentCardIndex++;
        text.setText(qurrentCard.getQuestion());
        nextButton.setText("Show answer");
        showAnswer = true;
    }

    public class LoadListener implements ActionListener{
        @Override
        public void actionPerformed(ActionEvent e) {
            JFileChooser chooser = new JFileChooser();
            chooser.showSaveDialog(frame);
            loadFile(chooser.getSelectedFile());
        }
    }

    public void loadFile(File file) {
        try{
            BufferedReader bfr = new BufferedReader(new FileReader(file));
            String read;
            while((read = bfr.readLine())!=null){
                makeCard(read);
            }
        }
        catch (IOException exc){exc.printStackTrace();}
        showNextCard();
    }

    public void makeCard(String read){
        String[] card = read.split("//");
        quizCards.add(new QuizCard(card[0], card[1]));
    }
}
