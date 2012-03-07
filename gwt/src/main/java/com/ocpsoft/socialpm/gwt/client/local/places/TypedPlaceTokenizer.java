package com.ocpsoft.socialpm.gwt.client.local.places;

import com.google.gwt.place.shared.Place;
import com.google.gwt.place.shared.PlaceTokenizer;

public interface TypedPlaceTokenizer<P extends Place> extends PlaceTokenizer<P>
{
   public Class<P> getPlaceType();
}
