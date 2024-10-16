package com.pasc.lib.widget.tangram;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.ViewGroup;

public class PersonalHeaderView extends BaseCardView {

    private RoundedImageView imageView;
    private SingleTextView personNameView;
    private TwoIconTextView authView;
    private IconTwoTextScoreView scoreView;

    public PersonalHeaderView(Context context) {
        super(context);
    }

    @Override
    protected void initViews(Context context) {
        LayoutInflater.from(context).inflate(R.layout.component_personal_header, this);

        imageView = findViewById(R.id.imageView);
        personNameView = findViewById(R.id.personNameView);
        authView = findViewById(R.id.authView);
        scoreView = findViewById(R.id.scoreView);
    }

    public RoundedImageView getImageView() {
        return imageView;
    }

    public SingleTextView getPersonNameView() {
        return personNameView;
    }

    public TwoIconTextView getAuthView() {
        return authView;
    }

    public IconTwoTextScoreView getScoreView() {
        return scoreView;
    }
}
