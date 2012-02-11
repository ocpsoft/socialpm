package com.ocpsoft.socialpm.gwt.client.local;

import com.ocpsoft.socialpm.gwt.client.local.places.HomePlace;

public class AppPlaceFactory {

    HomePlace.Tokenizer homePlaceTokenizer = new HomePlace.Tokenizer();

    HomePlace home = new HomePlace();

    public HomePlace.Tokenizer getHomePlaceTokenizer() {
        return homePlaceTokenizer;
    }

    public HomePlace getHome() {
        return home;
    }
  }