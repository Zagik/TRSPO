package Lab1;

import javax.swing.*;

/**
 * Варіант 13. Програма моделює обслуговування одного потоку процесів двома центральними процесорами комп'ютера без черги.
 * Якщо черговий процес генерується в мить, коли будь-який з процесорів вільний,
 * процес поступає на обробку в даний процесор, інакше процес знищується.
 * Визначте відсоток знищених процесів для двох однакових процесорів.
 */
class Gui {

    private JTextArea textArea;
    private JPanel panel1;
    private JButton buttonStop;
    private JButton buttonStart;
    private JButton buttonNewProcess;

    private ProcessFlowController mProcessFlowController;

    private Gui() {
        Logger.setOutput(textArea);

        mProcessFlowController = new ProcessFlowController();

        buttonStart.addActionListener(e -> {
            buttonStart.setEnabled(false);
            buttonStop.setEnabled(true);
            buttonNewProcess.setEnabled(true);

            // threads can't be reused, so reinitialize
            mProcessFlowController = new ProcessFlowController();

            mProcessFlowController.startSimulation();
        });

        buttonStop.addActionListener(e -> {
            buttonStart.setEnabled(true);
            buttonStop.setEnabled(false);
            buttonNewProcess.setEnabled(false);

            mProcessFlowController.stopSimulation();
        });

        buttonNewProcess.addActionListener(e -> mProcessFlowController.createNewProcess());
    }

    public static void main(String[] args) {
        JFrame frame = new JFrame("FenLab1");
        Gui gui = new Gui();
        frame.setContentPane(gui.panel1);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.pack();
        frame.setVisible(true);

    }


}
