package org.vbm.kittyware;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import static org.vbm.kittyware.Globals.kitties;

public class KittieActivity extends AppCompatActivity {

    ImageView catPhoto;
    TextView nameLabel, breedLabel, descLabel;
    Button callButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_kittie);
        initControls();
    }

    private void initControls() {
        catPhoto = (ImageView) findViewById(R.id.catPhoto);
        nameLabel = (TextView) findViewById(R.id.name_label);
        breedLabel = (TextView) findViewById(R.id.breed_label);
        descLabel = (TextView) findViewById(R.id.desc_label);
        final String phone;
        String urls;
        kitties.cursor.moveToFirst();
        do {
            if (kitties.cursor.getString(1).equals(Globals.kittieName)) break;
        } while (kitties.cursor.moveToNext());
        urls = kitties.cursor.getString(4);
        new DownloadImageTask(catPhoto).execute(urls);
        nameLabel.setText(kitties.cursor.getString(1));
        breedLabel.setText(kitties.cursor.getString(2));
        descLabel.setText(kitties.cursor.getString(3));
        phone = kitties.cursor.getString(5);
        callButton = (Button) findViewById(R.id.CallButton);
        callButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent callIn = new Intent(Intent.ACTION_DIAL);
                callIn.setData(Uri.parse("tel:" + phone.trim()));
                startActivity(callIn);
            }
        });
    }
}
