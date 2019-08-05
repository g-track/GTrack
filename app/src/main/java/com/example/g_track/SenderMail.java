package com.example.g_track;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.widget.Toast;

import java.util.Properties;

import javax.mail.Authenticator;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Session;
import javax.mail.PasswordAuthentication;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

public class SenderMail extends AsyncTask<Void,Void ,Void> {
    private Session session;
    private Context context;
    private String email;

    public SenderMail(Context context, String email) {
        this.context = context;
        this.email = email;
    }

    private ProgressDialog progressDialog;
    @Override
    protected Void doInBackground(Void... voids) {


        Properties properties = new Properties();
        properties.put("mail.smtp.host","smtp.gmail.com");
        properties.put("mail.socketFactory.port","465");
        properties.put("mail.smtp.socketFactory.class","javax.net.ssl.SSL.SocketFactory");
        properties.put("mail.smtp.auth","true");
        properties.put("mail.smtp.port","465");
      //Toast.makeText(context, "javax", Toast.LENGTH_SHORT).show();
        session = Session.getDefaultInstance(properties, new Authenticator() {
            @Override
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(Config.EMAIL,Config.PASSWORD);
            }
        });


        try {
            MimeMessage mm = new MimeMessage(session);
            mm.setFrom(new InternetAddress(Config.EMAIL));
            mm.addRecipient(Message.RecipientType.TO,new InternetAddress(email));
            mm.setSubject("Subject");
            mm.setText("7689");
            Transport.send(mm);
        } catch (MessagingException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
        progressDialog =ProgressDialog.show(context,"Sending Message ...","Please Wait...",false,false);
    }

    @Override
    protected void onPostExecute(Void aVoid) {
        super.onPostExecute(aVoid);
        progressDialog.dismiss();
        Toast.makeText(context, "Message Send.", Toast.LENGTH_SHORT).show();
    }
}
