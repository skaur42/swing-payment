import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.border.*;

public class PaymentApp
{
    public static void main(String[] args)
    {
        JFrame frame = new PaymentFrame();
        frame.setVisible(true);
    }
}
//------------------------------------------------------------------------
class PaymentFrame extends JFrame
{
    public PaymentFrame()
    {
        this.setTitle("Payment Application");
        this.setResizable(false);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        JPanel panel = new PaymentPanel();
        this.add(panel);
        this.pack();
        this.centerWindow();
    }

    private void centerWindow()
    {
        Toolkit tk = Toolkit.getDefaultToolkit();
        Dimension d = tk.getScreenSize();
        setLocation((d.width-this.getWidth())/2, (d.height-this.getHeight())/2);
    }
}
//------------------------------------------------------------------------
class PaymentPanel extends JPanel implements ActionListener
{
    private JLabel            cardTypeLabel, cardNumberLabel, expirationDateLabel;
    private JRadioButton      creditCardRadioButton, billCustomerRadioButton;
    private JList<String>     cardTypeList;
    private JTextField        cardNumberTextField;
    private JComboBox<String> monthComboBox, yearComboBox;
    private JCheckBox         verifiedCheckBox;
    private JButton           acceptButton, exitButton;

    public PaymentPanel()
    {
        this.setLayout(new GridBagLayout());

        Border loweredBorder = BorderFactory.createBevelBorder(BevelBorder.LOWERED);

        // create radio button panel --------------------------------------
        JPanel radioPanel        = new JPanel();
        radioPanel.setLayout(new FlowLayout(FlowLayout.LEFT));
        radioPanel.setBorder(BorderFactory.createTitledBorder(loweredBorder,"Billing:"));

        // Create radio button group 
        ButtonGroup billingGroup = new ButtonGroup();

        // credit card radio button
        creditCardRadioButton = new JRadioButton("Credit card", true);
        billingGroup.add(creditCardRadioButton);
        radioPanel.add(creditCardRadioButton);
        creditCardRadioButton.addActionListener(this);

        // bill customer radio button
        billCustomerRadioButton = new JRadioButton("Bill customer");
        billingGroup.add(billCustomerRadioButton);
        radioPanel.add(billCustomerRadioButton);
        billCustomerRadioButton.addActionListener(this);

        add(radioPanel, setConstraints(0,0,3,1, GridBagConstraints.WEST));

        // credit card type label
        cardTypeLabel = new JLabel("Card type:");
        add(cardTypeLabel, setConstraints(0,1,1,1, GridBagConstraints.EAST));

        // credit card type list
        String[] cardNames = {"Visa", "Master Card", "American Express", "Discover", "Other"};
        cardTypeList = new JList<>(cardNames);
        cardTypeList.setFixedCellWidth(170);
        cardTypeList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        cardTypeList.setVisibleRowCount(3);
        JScrollPane cardTypeScrollPane = new JScrollPane(cardTypeList);
        add(cardTypeScrollPane, setConstraints(1,1,2,1, GridBagConstraints.WEST));

        // credit card number label
        cardNumberLabel = new JLabel("Card number:");
        add(cardNumberLabel, setConstraints(0,2,1,1, GridBagConstraints.EAST));

        // credit card number text field
        cardNumberTextField = new JTextField(15);
        add(cardNumberTextField, setConstraints(1,2,2,1, GridBagConstraints.WEST));

        // expiration date label
        expirationDateLabel= new JLabel("Expiration date:");
        add(expirationDateLabel, setConstraints(0,3,1,1, GridBagConstraints.EAST));

        // month combo box
        String[] months = {"January", "February", "March", "April", "May", "June",
                           "July","August","September","October","November","December"};
        monthComboBox = new JComboBox<>(months);
        add(monthComboBox, setConstraints(1,3,1,1, GridBagConstraints.WEST));

        // year combo box
        String[] years = { "2015", "2016", "2017", "2018", "2019", "2020" };
        yearComboBox = new JComboBox<>(years);
        add(yearComboBox, setConstraints(2,3,1,1, GridBagConstraints.WEST));

        // verified check box
        verifiedCheckBox = new JCheckBox("Verified");
        add(verifiedCheckBox, setConstraints(1,4,1,1, GridBagConstraints.WEST));

        // calculate button
        acceptButton = new JButton("Accept");
        add(acceptButton, setConstraints(1,5,1,1, GridBagConstraints.EAST));
        acceptButton.addActionListener(this);

        // exit button
        exitButton = new JButton("Exit");
        add(exitButton, setConstraints(2,5,1,1, GridBagConstraints.CENTER));
        exitButton.addActionListener(this);
    }

    // a  method for setting grid bag constraints
    private GridBagConstraints setConstraints(int x, int y, int width, int height, int anchor)
    {
        GridBagConstraints c = new GridBagConstraints();
        c.insets = new Insets(5, 5, 5, 5);
        c.ipadx = 0;
        c.ipady = 0;
        c.gridx = x;
        c.gridy = y;
        c.gridwidth  = width;
        c.gridheight = height;
        c.anchor = anchor;
        return c;
    }

    public void actionPerformed(ActionEvent e)
    {
        Object source = e.getSource();

        if (source == exitButton)
            System.exit(0);

        if (source == acceptButton)
        {
            String msg = "";
            if (creditCardRadioButton.isSelected())
            {
                String ccType = (String) cardTypeList.getSelectedValue(); 
                String ccNum  = cardNumberTextField.getText(); 
                String ccMth  = (String) monthComboBox.getSelectedItem(); 
                String ccYear = (String) yearComboBox.getSelectedItem(); 

                if (verifiedCheckBox.isSelected())
                    if (!ccType.equals("") && !ccNum.equals("") && !ccMth.equals("")  && !ccYear.equals("") )
                        msg = "Credit Card: "  + ccType +
                              "\nNumber: "     + ccNum  +
                              "\nExpiration: " + ccMth  +
                              ", "             + ccYear +
                              "\nCard has been verified.";
                    else
                        msg = "Please all enter credit card info"; 

                if (!verifiedCheckBox.isSelected())
                    msg = "\nCard has not been verified.";
            }
            else
                msg = "Customer will be billed.";

            JOptionPane.showMessageDialog(this, msg);
            verifiedCheckBox.setSelected(false);
        }
        if (creditCardRadioButton.isSelected())
            enableCreditCardControls(true);
        else if (billCustomerRadioButton.isSelected())
            enableCreditCardControls(false);
    }

    private void enableCreditCardControls(boolean enable)
    {
        cardTypeLabel.setEnabled(enable);
        cardTypeList.setEnabled(enable);
        cardNumberLabel.setEnabled(enable);
        cardNumberTextField.setEnabled(enable);
        expirationDateLabel.setEnabled(enable);
        monthComboBox.setEnabled(enable);
        yearComboBox.setEnabled(enable);
        verifiedCheckBox.setEnabled(enable);
    }

}
