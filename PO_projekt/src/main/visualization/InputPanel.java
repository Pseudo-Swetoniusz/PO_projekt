package visualization;

import parser.JsonParser;
import parser.MapStructure;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class InputPanel implements ActionListener {

    static JFrame frame;
    static JTextField textInput;
    JButton oneMap;
    JButton twoMaps;


    public void inputFrame() {
        frame = new JFrame("Map Properties");
        textInput = new JTextField("enter the path");
        textInput.setPreferredSize(new Dimension(350,20));
        InputPanel path = new InputPanel();
        oneMap = new JButton("create one map");
        twoMaps = new JButton("create two maps");
        oneMap.addActionListener(path);
        twoMaps.addActionListener(path);

        JPanel panelTop = new JPanel();
        JPanel panelBottom = new JPanel();

        panelTop.add(textInput);
        panelBottom.add(oneMap);
        panelBottom.add(twoMaps);

        frame.add(panelTop);
        frame.add(panelBottom);
        frame.setSize(500,200);
        frame.setVisible(true);
        frame.setLayout(new FlowLayout());
        frame.pack();
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

    }

    public void actionPerformed(ActionEvent e) {
        String s = e.getActionCommand();
        if(s.equals("create one map")) {
            String path = textInput.getText();
            JsonParser parser = new JsonParser();
            MapStructure mapProperties = parser.readFromJson(path);
            int type = 1;
            OutputFrame map = new OutputFrame(type, mapProperties);
            frame.dispose();
        }
        if(s.equals("create two maps")) {
            String path = textInput.getText();
            JsonParser parser = new JsonParser();
            MapStructure mapProperties = parser.readFromJson(path);
            int type = 2;
            OutputFrame map = new OutputFrame(type, mapProperties);
            frame.dispose();
        }
    }

}
