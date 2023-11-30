package com.company;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;

public class Main {
    private static final int WORD_LEN = 5;
    private static final int TRY_COUNT = 6;
    private static final int WINDOW_WIDTH = 695;
    private static final int WINDOW_HEIGHT = 460;
    private static final int MARGIN_X = 25;
    private static final int MARGIN_Y = 10;
    private static final int LBL_LEFT = MARGIN_X;
    private static final int LBL_TOP = MARGIN_Y;
    private static final int LBL_WIDTH = 32;
    private static final int LBL_HEIGHT = 32;
    private static final int LBL_DELTA_X = LBL_WIDTH + 10;
    private static final int LBL_DELTA_Y = LBL_HEIGHT + 10;
    private static final int LIST_TOP = MARGIN_Y;
    private static final int LIST_LEFT = MARGIN_X + LBL_DELTA_X * 6 + 10;
    private static final int LIST_WIDTH = 375;
    private static final int LIST_HEIGHT = LBL_DELTA_Y * 6;
    private static final int ALPHABET_COLS = 12;
    private static final int BUTTON_WIDTH = 50;
    private static final int BUTTON_HEIGHT = 32;
    private static final int BUTTON_LEFT = MARGIN_X - 5;
    private static final int BUTTON_TOP = LBL_TOP + LBL_DELTA_Y * TRY_COUNT + 2 * MARGIN_Y;
    private static final int BUTTON_DELTA_X = BUTTON_WIDTH + 4;
    private static final int BUTTON_DELTA_Y = BUTTON_HEIGHT + 4;
    private static final int MAIN_FONT_SIZE = 16;
    private static final int OPTIONAL_FONT_SIZE = 14;
    private static final Color MAIN_COLOR = new Color(238, 238, 238);

    Filter filter = new Filter();
    private final JFrame window;
    private JLabel[][] labels;
    DefaultListModel<String> listModel;
    JList wordList;

    private JButton btnYes, btnBackspace, btnA, btnB, btnV, btnG, btnD, btnEst, btnYo, btnZh, btnZ,
            btnI, btnYi, btnK, btnL, btnM, btnN, btnO, btnP, btnR, btnS, btnT, btnU, btnF, btnH,
            btnC, btnCh, btnSh, btnShch, btnEr, btnY, btnEri, btnE, btnYu, btnYa;

    private int tryNum = 0;
    private static int wordNum = 0;

    public Main() {
        window = new JFrame("5 букв");
        window.setSize(WINDOW_WIDTH, WINDOW_HEIGHT);
        window.setLocationRelativeTo(null);

//==================================================== Окошки для букв
        labels = new JLabel[TRY_COUNT][WORD_LEN];

        int[] xLbl = new int[WORD_LEN];
        xLbl[0] = MARGIN_X + LBL_LEFT;
        for (int i = 1; i < WORD_LEN; i++) xLbl[i] = xLbl[i - 1] + LBL_DELTA_X;

        int[] yLbl = new int[TRY_COUNT];
        yLbl[0] = MARGIN_Y + LBL_TOP;
        for (int i = 1; i < TRY_COUNT; i++) yLbl[i] = yLbl[i - 1] + LBL_DELTA_Y;

        for (int i = 0; i < TRY_COUNT; i++)
            for (int j = 0; j < WORD_LEN; j++) {
                labels[i][j] = initLabel("", MAIN_FONT_SIZE, xLbl[j], yLbl[i]);
            }

//==================================================== Список слов
//        DefaultListModel<String> listModel = new DefaultListModel<>();
        listModel = new DefaultListModel<>();
        try (
                BufferedReader reader = new BufferedReader(new FileReader("5letters.txt"));
        ) {
            String word = null;
            int i = 0;
            while ((word = reader.readLine()) != null) {
                listModel.add(i, word);
                i++;
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        wordList = new JList(listModel);

        JPanel panel = new JPanel();
        panel.setBounds(LIST_LEFT, LIST_TOP, LIST_WIDTH, LIST_HEIGHT);
//        panel.setFont(new Font("Arial", Font.PLAIN, OPTIONAL_FONT_SIZE));
//        panel.setCursor(new Cursor(Cursor.HAND_CURSOR));
//        panel.setAutoscrolls(true);
        panel.setFocusable(false);
//        panel.setVisible(true);
        panel.add(new JScrollPane(wordList));
//        wordList.setBounds(LIST_LEFT, LIST_TOP, LIST_WIDTH, LIST_HEIGHT);
        wordList.setSize(LIST_WIDTH, LIST_HEIGHT);
        wordList.setFont(new Font("Arial", Font.PLAIN, OPTIONAL_FONT_SIZE));
        wordList.setCursor(new Cursor(Cursor.HAND_CURSOR));
//        wordList.setAutoscrolls(true);
        wordList.setFocusable(false);
        wordList.setVisible(true);

        window.add(panel);
//==================================================== Кнопки с буквами
        int[] xBtn = new int[ALPHABET_COLS];
        xBtn[0] = BUTTON_LEFT;
        for (int i = 1; i < ALPHABET_COLS; i++) xBtn[i] = xBtn[i - 1] + BUTTON_DELTA_X;
        int[] yBtn = {BUTTON_TOP, BUTTON_TOP + BUTTON_DELTA_Y, BUTTON_TOP + BUTTON_DELTA_Y * 2};

        btnYes = initButton("Okъ", MAIN_FONT_SIZE, xBtn[0], yBtn[2], event -> eventYes());

        btnBackspace = initButton("←", MAIN_FONT_SIZE, xBtn[11], yBtn[2], event -> eventBackspace());

        btnA = initButton("А", MAIN_FONT_SIZE, xBtn[3], yBtn[1], event -> eventLetter("A"));
        btnB = initButton("Б", MAIN_FONT_SIZE, xBtn[9], yBtn[2], event -> eventLetter("Б"));
        btnV = initButton("В", MAIN_FONT_SIZE, xBtn[2], yBtn[1], event -> eventLetter("В"));
        btnG = initButton("Г", MAIN_FONT_SIZE, xBtn[6], yBtn[0], event -> eventLetter("Г"));
        btnD = initButton("Д", MAIN_FONT_SIZE, xBtn[8], yBtn[1], event -> eventLetter("Д"));
        btnEst = initButton("Е", MAIN_FONT_SIZE, xBtn[4], yBtn[0], event -> eventLetter("Е"));
        btnYo = initButton("Ё", MAIN_FONT_SIZE, xBtn[11], yBtn[1], event -> eventLetter("Ё"));
        btnZh = initButton("Ж", MAIN_FONT_SIZE, xBtn[9], yBtn[1], event -> eventLetter("Ж"));
        btnZ = initButton("З", MAIN_FONT_SIZE, xBtn[9], yBtn[0], event -> eventLetter("З"));
        btnI = initButton("И", MAIN_FONT_SIZE, xBtn[6], yBtn[2], event -> eventLetter("И"));
        btnYi = initButton("Й", MAIN_FONT_SIZE, xBtn[0], yBtn[0], event -> eventLetter("Й"));
        btnK = initButton("К", MAIN_FONT_SIZE, xBtn[3], yBtn[0], event -> eventLetter("К"));
        btnL = initButton("Л", MAIN_FONT_SIZE, xBtn[7], yBtn[1], event -> eventLetter("Л"));
        btnM = initButton("М", MAIN_FONT_SIZE, xBtn[5], yBtn[2], event -> eventLetter("М"));
        btnN = initButton("Н", MAIN_FONT_SIZE, xBtn[5], yBtn[0], event -> eventLetter("Н"));
        btnO = initButton("О", MAIN_FONT_SIZE, xBtn[6], yBtn[1], event -> eventLetter("О"));
        btnP = initButton("П", MAIN_FONT_SIZE, xBtn[4], yBtn[1], event -> eventLetter("П"));
        btnR = initButton("Р", MAIN_FONT_SIZE, xBtn[5], yBtn[1], event -> eventLetter("Р"));
        btnS = initButton("С", MAIN_FONT_SIZE, xBtn[4], yBtn[2], event -> eventLetter("С"));
        btnT = initButton("Т", MAIN_FONT_SIZE, xBtn[7], yBtn[2], event -> eventLetter("Т"));
        btnU = initButton("У", MAIN_FONT_SIZE, xBtn[2], yBtn[0], event -> eventLetter("У"));
        btnF = initButton("Ф", MAIN_FONT_SIZE, xBtn[0], yBtn[1], event -> eventLetter("Ф"));
        btnH = initButton("Х", MAIN_FONT_SIZE, xBtn[10], yBtn[0], event -> eventLetter("Х"));
        btnC = initButton("Ц", MAIN_FONT_SIZE, xBtn[1], yBtn[0], event -> eventLetter("Ц"));
        btnCh = initButton("Ч", MAIN_FONT_SIZE, xBtn[3], yBtn[2], event -> eventLetter("Ч"));
        btnSh = initButton("Ш", MAIN_FONT_SIZE, xBtn[7], yBtn[0], event -> eventLetter("Ш"));
        btnShch = initButton("Щ", MAIN_FONT_SIZE, xBtn[8], yBtn[0], event -> eventLetter("Щ"));
        btnEr = initButton("Ъ", MAIN_FONT_SIZE, xBtn[11], yBtn[0], event -> eventLetter("Ъ"));
        btnY = initButton("Ы", MAIN_FONT_SIZE, xBtn[1], yBtn[1], event -> eventLetter("Ы"));
        btnEri = initButton("Ь", MAIN_FONT_SIZE, xBtn[8], yBtn[2], event -> eventLetter("Ь"));
        btnE = initButton("Э", MAIN_FONT_SIZE, xBtn[10], yBtn[1], event -> eventLetter("Э"));
        btnYu = initButton("Ю", MAIN_FONT_SIZE, xBtn[10], yBtn[2], event -> eventLetter("Ю"));
        btnYa = initButton("Я", MAIN_FONT_SIZE, xBtn[2], yBtn[2], event -> eventLetter("Я"));

        window.setBackground(Color.BLACK);
        window.setLayout(null);
        window.setResizable(true);
        window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        window.setVisible(true);
    }

    private void eventYes() {
        HashSet<Character> absent = new HashSet<>();
        HashMap<Integer, Character> bulls = new HashMap<>();
        HashMap<Integer, Character> cows = new HashMap<>();

        if (wordNum == WORD_LEN) {
            for (int i = 0; i < WORD_LEN; i++) {
                if (labels[tryNum][i].getBackground().equals(Color.GRAY)) {
                    absent.add(labels[tryNum][i].getText().charAt(0));
                }
                else {
                    if (labels[tryNum][i].getBackground().equals(Color.BLUE)) {
                        bulls.put(i, labels[tryNum][i].getText().charAt(0));
                    }
                    else {
                        cows.put(i, labels[tryNum][i].getText().charAt(0));
                    }
                }
            }

            filter.addFilter(absent, bulls, cows); // Обновить фильтр

            refresh(); // Обновить список

            // Переход к новой попытке
            if (tryNum < TRY_COUNT) {
                tryNum++;
                wordNum = 0;
            }

        }
    }

    private void eventBackspace() {
        if (wordNum > 0) {
            wordNum--;
            labels[tryNum][wordNum].setText("");
            labels[tryNum][wordNum].setBackground(MAIN_COLOR);
        }
    }

    private void eventLetter(String letter) {
        if (wordNum < WORD_LEN) {
            labels[tryNum][wordNum].setText(letter);
            labels[tryNum][wordNum].setBackground(Color.GRAY);
            wordNum++;
        }
    }

    private void refresh() {
//        try (
//                BufferedReader reader = new BufferedReader(new FileReader("5letters.txt"));
//                BufferedWriter writer = new BufferedWriter(new FileWriter("result.txt"));
//        ) {
//            String word = null;
//            while ((word = reader.readLine()) != null) {
//                if (filter.checkWord(word)) {
//                    writer.write(word + "\n");
//
//
//                }
//            }
//            writer.flush();
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
        int i = 0;
        while (i < listModel.size()) {
            String word = listModel.getElementAt(i);
            if (!filter.checkWord(word)) {
                System.out.println(word);
                listModel.remove(i);
            }
            else { i++; }
        }
    }

    private JLabel initLabel(String label, int fontSize, int x, int y) {
        JLabel lbl = new JLabel(label);

        lbl.setBounds(x, y, LBL_WIDTH, LBL_HEIGHT);
        lbl.setBorder(BorderFactory.createLineBorder(Color.BLACK));
        lbl.setOpaque(true);
        lbl.setFont(new Font("Arial", Font.PLAIN, fontSize));
        lbl.setHorizontalAlignment(JLabel.CENTER);
        lbl.setCursor(new Cursor(Cursor.HAND_CURSOR));
        lbl.setFocusable(false);

        lbl.addMouseListener(new MouseListener() {
            @Override
            public void mouseClicked(MouseEvent e) {
                if (lbl.getBackground().equals(Color.GRAY)) { lbl.setBackground(Color.BLUE); }
                else {
                    if (lbl.getBackground().equals((Color.BLUE))) { lbl.setBackground(Color.GREEN); }
                    else {
                        if (lbl.getBackground().equals(Color.GREEN)) { lbl.setBackground(Color.GRAY); }
                    }
                }
            }

            @Override
            public void mousePressed(MouseEvent e) { }

            @Override
            public void mouseReleased(MouseEvent e) { }

            @Override
            public void mouseEntered(MouseEvent e) { }

            @Override
            public void mouseExited(MouseEvent e) { }
        });

        window.add(lbl);

        return lbl;
    }

    private JButton initButton(String label, int fontSize, int x, int y, ActionListener event) {
        JButton btn = new JButton(label);

        btn.setBounds(x, y, (!label.equals("Okъ")) ? BUTTON_WIDTH : BUTTON_WIDTH + BUTTON_DELTA_X, BUTTON_HEIGHT);

        btn.setFont(new Font("Arial", Font.PLAIN, fontSize));
        btn.setCursor(new Cursor(Cursor.HAND_CURSOR));
        btn.addActionListener(event);
        btn.setFocusable(false);

        window.add(btn);

        return btn;
    }

//    public static void init() throws IOException{
//
//        BufferedReader reader = new BufferedReader(new FileReader("c:/temp/russian_nouns.txt"));
//        BufferedWriter writer = new BufferedWriter(new FileWriter("c:/temp/5letters.txt"));
//        String word = null;
//        while ((word = reader.readLine()) != null) {
//            if (word.length() == 5) writer.write(word + "\n");
//        }
//        writer.flush();
//        writer.close();
//        reader.close();
//        System.out.println("Job is done.");
//    }

//    public void proc() throws IOException{
//        BufferedReader reader = new BufferedReader(new FileReader("5letters.txt"));
//        BufferedWriter writer = new BufferedWriter(new FileWriter("result.txt"));
//        String word = null;
//        while ((word = reader.readLine()) != null) {
//            if (filter.checkWord(word)) writer.write(word + "\n");
//        }
//        writer.flush();
//        writer.close();
//        reader.close();
//        System.out.println("Done.");
//    }

    public static void main(String[] args) throws IOException {
        Main m = new Main();
    }
}
