package com.example.mytriggersms;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Switch;
import android.widget.Toast;

import com.example.mytriggersms.DBConnection.DAOComingSms;
import com.example.mytriggersms.DBConnection.DAOsms;
import com.example.mytriggersms.entity.ComingSms;
import com.example.mytriggersms.entity.Sms;

import java.util.List;

public class MainActivity extends AppCompatActivity {
    private Context this_;
    private Button saveButton;
    private EditText passwordEdit;
    private EditText smsTitleEdit;
    private Switch activePassive;
    private String password;
    private String smsTitle;
    private String status;
    private Sms sms;
    private DAOsms DBsms;
    private DAOComingSms DBcsms;
    private List<Sms> smsListesi;
    private List<ComingSms> CsmsListesi;

    public List<Sms> getSmsListesi() {
        return smsListesi;
    }

    public void setSmsListesi(List<Sms> smsListesi) {
        this.smsListesi = smsListesi;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        passwordEdit = (EditText) findViewById(R.id.passwordTextEdit);
        smsTitleEdit = (EditText) findViewById(R.id.smsTextEdit);
        saveButton = (Button) findViewById(R.id.saveButton);
        activePassive = (Switch) findViewById(R.id.activePassive);
        this_ = this;
        sms = new Sms();
        DBsms = new DAOsms(this_);
        DBcsms = new DAOComingSms(this_);
        smsListesi = DBsms.getsmsInfo(1);
        CsmsListesi = DBcsms.getsms();

        for (Sms s : smsListesi) {
            smsTitleEdit.setText(s.getSmsTitle());
            passwordEdit.setText(s.getPassword());
            if (s.getStatus().equals("1")) {
                activePassive.isChecked();
            } else {
                activePassive.setChecked(false);
            }
        }
        assignButton();
    }

    private void assignButton() {
        saveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                password = passwordEdit.getText().toString();
                smsTitle = smsTitleEdit.getText().toString();
                status = activePassive.isChecked() ? "1" : "0";

                if (password.matches("")) {
                    Toast.makeText(this_, "Parola alanı boş kalamaz", Toast.LENGTH_LONG).show();
                } else if (smsTitle.matches("")) {
                    Toast.makeText(this_, "Lütfen en az bir sms başlığı giriniz", Toast.LENGTH_LONG).show();
                } else {
                    sms.setSmsTitle(smsTitle);
                    sms.setPassword(password);
                    sms.setStatus(status);
                    long count = DBsms.dabatableCount();
                    long idInsert = 0;
                    int idUpdate = 0;
                    if (count == 0) {
                        idInsert = DBsms.InsertSms(sms);
                        if (idInsert > 0) {
                            Toast.makeText(this_, "Bilgileriniz Başarıyla Kaydedildi", Toast.LENGTH_LONG).show();
                            smsListesi = DBsms.getsmsInfo(1);
                        } else {
                            Toast.makeText(this_, "Kayıt Ekleme Başarısız!", Toast.LENGTH_LONG).show();
                        }
                    } else {
                        idUpdate = DBsms.updateSms(sms);
                        if (idUpdate > 0) {
                            //Toast.makeText(this_, "count " + dbc.dabatableCount(), Toast.LENGTH_LONG).show();
                            //dbc.deleteSms();
                            Toast.makeText(this_, "Bilgileriniz Başarıyla Güncellendi", Toast.LENGTH_LONG).show();
                            //Toast.makeText(this_, "count " + dbc.dabatableCount(), Toast.LENGTH_LONG).show();
                            smsListesi = DBsms.getsmsInfo(1);
                            CsmsListesi = DBcsms.getsms();
                        } else {
                            Toast.makeText(this_, "Kayıt Güncelleme Başarısız!", Toast.LENGTH_LONG).show();
                        }
                    }


                }

            }
        });
    }
}