package com.pasc.lib.widget.tangram;

import android.content.Context;
import android.view.LayoutInflater;

public class PersonalInfoView extends BaseCardView {

  private RoundedImageView imageView;
  private SingleTextView personNameView;
  private TwoIconTextView authView;

  public PersonalInfoView(Context context) {
    super(context);
  }

  @Override
  protected void initViews(Context context) {
    LayoutInflater.from(context).inflate(R.layout.component_personal_info, this);

    imageView = findViewById(R.id.imageView);
    personNameView = findViewById(R.id.personNameView);
    authView = findViewById(R.id.authView);
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
