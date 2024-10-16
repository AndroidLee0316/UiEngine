package com.pasc.lib.widget.tangram;

import android.content.Context;
import android.view.LayoutInflater;

public class PersonalHeader2View extends BaseCardView {

    private RoundedImageView imageView;
    private SingleTextView personNameView;
    private TwoIconTextView authView;
    private IconTwoTextView scoreView;
    private IconTwoTextView stepNumView;

    public PersonalHeader2View(Context context) {
        super(context);
    }

    @Override
    protected void initViews(Context context) {
        LayoutInflater.from(context).inflate(R.layout.component_personal_header_2, this);

        imageView = findViewById(R.id.imageView);
        personNameView = findViewById(R.id.personNameView);
        authView = findViewById(R.id.authView);
        scoreView = findViewById(R.id.scoreView);
        stepNumView = findViewById(R.id.stepNumView);
    }

    public IconTwoTextView getStepNumView() {
        return stepNumView;
    }

    public IconTwoTextView getScoreView() {
        return scoreView;
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

}
