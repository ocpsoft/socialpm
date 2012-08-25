/**
 * Copyright 2012 Red Hat, Inc. and/or its affiliates.
 * 
 * Licensed under the Eclipse Public License Version 1.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * 
 *     http://www.eclipse.org/legal/epl-v10.html
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.ocpsoft.socialpm.gwt.client.local.view.login;

import com.google.gwt.place.shared.Place;
import com.ocpsoft.socialpm.gwt.client.local.history.HistoryConstants;
import com.ocpsoft.socialpm.gwt.client.local.places.TypedPlaceTokenizer;

public class LoginPlace extends Place
{

   public LoginPlace()
   {}

   public LoginPlace(String token)
   {}

   public static class Tokenizer implements TypedPlaceTokenizer<LoginPlace>
   {
      @Override
      public String getToken(LoginPlace place)
      {
         return HistoryConstants.LOGIN();
      }

      @Override
      public LoginPlace getPlace(String token)
      {
         if (HistoryConstants.LOGIN().equals(token))
            return new LoginPlace(token);
         return null;
      }

      @Override
      public Class<LoginPlace> getPlaceType()
      {
         return LoginPlace.class;
      }
   }
}