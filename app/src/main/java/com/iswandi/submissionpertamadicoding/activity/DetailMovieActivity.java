package com.iswandi.submissionpertamadicoding.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.iswandi.submissionpertamadicoding.R;
import com.iswandi.submissionpertamadicoding.helper.MyConstant;
import com.squareup.picasso.Picasso;

import butterknife.BindView;
import butterknife.ButterKnife;

public class DetailMovieActivity extends AppCompatActivity {

    @BindView(R.id.imgmovie)
    ImageView imgmovie;
    @BindView(R.id.txttitle)
    TextView txttitle;
    @BindView(R.id.txtreleasedate)
    TextView txtreleasedate;
    @BindView(R.id.imgratingmovie)
    ImageView imgratingmovie;
    @BindView(R.id.txtrate)
    TextView txtrate;
    @BindView(R.id.txtsynopsis)
    TextView txtsynopsis;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);
        ButterKnife.bind(this);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });
        //getdatafromintent
        Intent terima =getIntent();
        txttitle.setText(terima.getStringExtra(MyConstant.TITLE));
        txtreleasedate.setText(terima.getStringExtra(MyConstant.RELEASE));
        txtrate.setText("rating :" +String.valueOf(terima.getDoubleExtra(MyConstant.RATE,0)));
        txtsynopsis.setText(terima.getStringExtra(MyConstant.SYSNOPSIS));
        Picasso.with(this)
                .load(MyConstant.IMAGE_URL + getIntent().getStringExtra(MyConstant.POSTER))
                .placeholder(R.drawable.noimage)
                .error(R.drawable.noimage)
                .into(imgmovie);

    }

}
