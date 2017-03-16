package gui;

import javax.swing.BorderFactory;
import javax.swing.JLabel;
import javax.swing.JPanel;

/**
 * @author MultiPeden
 *
 */
public class MessageField {
	
	private JPanel msgpanel;
	private JLabel filler;

	public MessageField(){
        this.msgpanel = new JPanel();
        this.filler = new JLabel("welcome");
        filler.setHorizontalAlignment(JLabel.LEFT);
        msgpanel.add(filler);
        msgpanel.setBorder(BorderFactory.createEtchedBorder());	
	}
	
	/**
	 * @return
	 */
	public JPanel getMsgPanel(){
		return msgpanel;
	}
	
	/**
	 * @param msg
	 */
	public void setMsgText(String msg){
		filler.setText(msg);
	}
	
}
