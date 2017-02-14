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
 * Created by Hippo on 2/14/2017.
 */

import static org.junit.Assert.assertEquals;

import android.os.Build;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.RobolectricTestRunner;
import org.robolectric.RuntimeEnvironment;
import org.robolectric.annotation.Config;

@Config(constants = BuildConfig.class, sdk = Build.VERSION_CODES.N_MR1)
@RunWith(RobolectricTestRunner.class)
public class TextPreferencesTest {

  @Test
  public void test() {
    TextPreferences p = new TextPreferences(RuntimeEnvironment.application.getSharedPreferences(
        "shared_preferences",
        0
    ));

    assertEquals(false, p.getBooleanHaHa());
    p.putBooleanHaHa(true);
    assertEquals(true, p.getBooleanHaHa());

    assertEquals(true, p.getBOOLEAN_HA_HA());
    p.putBOOLEAN_HA_HA(false);
    assertEquals(false, p.getBOOLEAN_HA_HA());

    assertEquals(454, p.getIntHaHa());
    p.putIntHaHa(555);
    assertEquals(555, p.getIntHaHa());

    assertEquals(878, p.getINT_HAHA());
    p.putINT_HAHA(666);
    assertEquals(666, p.getINT_HAHA());

    assertEquals(454L, p.getLongHaHa());
    p.putLongHaHa(666L);
    assertEquals(666L, p.getLongHaHa());

    assertEquals(878L, p.getLONG_HAHA());
    p.putLONG_HAHA(666L);
    assertEquals(666L, p.getLONG_HAHA());

    assertEquals(454.0f, p.getFloatHaHa(), 0.0f);
    p.putFloatHaHa(666.0f);
    assertEquals(666.0f, p.getFloatHaHa(), 0.0f);

    assertEquals(878.0f, p.getFLOAT_HAHA(), 0.0f);
    p.putFLOAT_HAHA(666.0f);
    assertEquals(666.0f, p.getFLOAT_HAHA(), 0.0f);

    assertEquals("ha ha", p.getStringHaHa());
    p.putStringHaHa("xi xi");
    assertEquals("xi xi", p.getStringHaHa());

    assertEquals("he he", p.getSTRING_HAHA());
    p.putSTRING_HAHA("xa xa");
    assertEquals("xa xa", p.getSTRING_HAHA());

    assertEquals(454, p.getDecimalIntHaHa());
    p.putDecimalIntHaHa(555);
    assertEquals(555, p.getDecimalIntHaHa());

    assertEquals(878, p.getDECIMAL_INT_HAHA());
    p.putDECIMAL_INT_HAHA(666);
    assertEquals(666, p.getDECIMAL_INT_HAHA());
  }
}
