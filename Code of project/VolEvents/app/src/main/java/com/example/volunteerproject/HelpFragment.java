package com.example.volunteerproject;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.fragment.app.Fragment;

public class HelpFragment extends Fragment {

	/** Declaring variables  */

	TextView helpTitle, useApp, ht1, ht2, ht3, ht4, ht5, ht6, ht7, ht8, ht9, ht10;
	ImageView helpImg, helpImg1, helpImg2;
	Animation topAnim, bottomAnim;

	public HelpFragment() {

	}




	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		View v = inflater.inflate(R.layout.fragment_help, container, false);
		/** Finding a unique identifier for each variable  */

		helpTitle = v.findViewById(R.id.help__title);
		useApp = v.findViewById(R.id.app_use);
		ht1 = v.findViewById(R.id.help__text_1);
		ht2 = v.findViewById(R.id.help__text_2);
		ht3 = v.findViewById(R.id.help__text_3);
		ht4 = v.findViewById(R.id.help__text_4);
		ht5 = v.findViewById(R.id.help__text_5);
		ht6 = v.findViewById(R.id.help__text_6);
		ht7 = v.findViewById(R.id.help__text_7);
		ht8 = v.findViewById(R.id.help__text_8);
		ht9 = v.findViewById(R.id.help__text_9);
		ht10 = v.findViewById(R.id.help__text_10);
		helpImg = v.findViewById(R.id.help_img);
		helpImg1 = v.findViewById(R.id.help_1);
		helpImg2 = v.findViewById(R.id.help_2);

		/**Finding a unique identifier for animation*/
		topAnim = AnimationUtils.loadAnimation(getActivity(), R.anim.top_animation);
		bottomAnim = AnimationUtils.loadAnimation(getActivity(), R.anim.bottom_animation);

	/** Setting animation for each variable */
		helpTitle.setAnimation(topAnim);
		useApp.setAnimation(topAnim);
		ht1.setAnimation(topAnim);
		ht2.setAnimation(topAnim);
		ht3.setAnimation(topAnim);
		ht4.setAnimation(topAnim);
		ht5.setAnimation(topAnim);
		ht6.setAnimation(topAnim);
		ht7.setAnimation(topAnim);
		ht8.setAnimation(topAnim);
		ht9.setAnimation(topAnim);
		ht10.setAnimation(topAnim);
		helpImg.setAnimation(topAnim);
		helpImg1.setAnimation(topAnim);
		helpImg2.setAnimation(topAnim);




		return v;
	}

}

