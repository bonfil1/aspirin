/**
 * MS.java
 * 
 * 
 * Created: Thu Sep 2 18:05:35 2004
 * 
 * @author <a href="mailto:shiva@int">Shiva Chaudhuri </a>
 * @version 1.0
 */
package org.masukomi.aspirin.core;

import org.masukomi.aspirin.core.MailQue;
import org.masukomi.aspirin.core.QueManager;
import org.masukomi.aspirin.core.SimpleMimeMessageGenerator;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import javax.mail.Message;

public class MS {
	public MS() {

	}

	public static void main(String[] args) {
		try {
			System.out.println("starting");
			MimeMessage message = SimpleMimeMessageGenerator
					.getNewMimeMessage();

			message.setFrom(new InternetAddress(
					"jUnit-aspirin-test@masukomi.org"));
			message.addRecipient(Message.RecipientType.TO, new InternetAddress(
					"aspirin-test@masukomi.org"));
			message.setSubject("Aspirin - test to show it doesn't shut down");
			message.setText("This is the text");
			MailQue mq = new MailQue();
			mq.queMail(message);
			System.out.println("queing mail");
			shutDownQueue(mq);
			System.out.println("shutting down");

			
		} catch (Exception ex) {
			ex.printStackTrace();

		}

	}

	/**
	 * Flush pending messages and shut down queue. Ideally, a mail queue should
	 * be capable of initialization and shutdown several times in a single VM
	 * session.
	 */
	static void shutDownQueue(MailQue mq) throws InterruptedException {

		// wait for the queue to clear pending messages, if it takes
		// forever, shut it down anyway.
		int loopLimit = 20;
		int count = 0;
		while (mq.getNextSendable() != null && count < loopLimit) {
			count++;
			Thread.sleep(500);
		}

		QueManager qm = mq.getQueManager();
//		qm.terminateRun();

	}

} // MS
