/*
 * Copyright 2017 Hippo Seven
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.hippo.preferences.demo;

/*
 * Created by Hippo on 2/15/2017.
 */

import android.content.SharedPreferences;
import com.hippo.preferences.Preferences;
import com.hippo.preferences.annotation.IntItem;
import com.hippo.preferences.annotation.Items;

@Items(
    getPrefix = "",
    putPrefix = "",
    intItems = {
        @IntItem(key = "red_dog", defValue = 789),
        @IntItem(key = "green_dog", defValue = 333),
    }
)
public class DogPreferencesImpl extends Preferences {

  private static final String KEY_BLUE_DOG_MALE = "blue_dog_male";
  private static final String KEY_BLUE_DOG_FEMALE = "blue_dog_female";
  private static final int VALUE_BLUE_DOG_MALE = 12;
  private static final int VALUE_BLUE_DOG_FEMALE = 3445;

  public DogPreferencesImpl(SharedPreferences shardPref) {
    super(shardPref);
  }

  public void blueDog(int male, int female) {
    putInt(KEY_BLUE_DOG_MALE, male);
    putInt(KEY_BLUE_DOG_FEMALE, female);
  }

  public int blueDog() {
    return getInt(KEY_BLUE_DOG_MALE, VALUE_BLUE_DOG_MALE)
        + getInt(KEY_BLUE_DOG_FEMALE, VALUE_BLUE_DOG_FEMALE);
  }
}
