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

package com.hippo.preferences;

/*
 * Created by Hippo on 2/14/2017.
 */

import android.content.SharedPreferences;
import com.hippo.preferences.annotation.BooleanItem;
import com.hippo.preferences.annotation.DecimalIntItem;
import com.hippo.preferences.annotation.FloatItem;
import com.hippo.preferences.annotation.IntItem;
import com.hippo.preferences.annotation.Items;
import com.hippo.preferences.annotation.LongItem;
import com.hippo.preferences.annotation.StringItem;
import java.util.List;
import java.util.Map;
import java.util.concurrent.locks.Lock;

@Items(
    booleanItems = {
        @BooleanItem(key = "boolean_ha_ha", defValue = false),
        @BooleanItem(name = "BOOLEAN_HA_HA", key = "boolean_he_he", defValue = true),
    },
    intItems = {
        @IntItem(key = "int_ha_ha", defValue = 454),
        @IntItem(name = "INT_HAHA", key = "int_he_he", defValue = 878),
    },
    longItems = {
        @LongItem(key = "long_ha_ha", defValue = 454L),
        @LongItem(name = "LONG_HAHA", key = "long_he_he", defValue = 878L),
    },
    floatItems = {
        @FloatItem(key = "float_ha_ha", defValue = 454.0f),
        @FloatItem(name = "FLOAT_HAHA", key = "float_he_he", defValue = 878.0f),
    },
    stringItems = {
        @StringItem(key = "string_ha_ha", defValue = "ha ha"),
        @StringItem(key = "string_xi_xi", defValue = {}),
        @StringItem(key = "string_xa_xa", defValue = {"xa xa", "xi xi"}),
        @StringItem(name = "STRING_HAHA", key = "string_he_he", defValue = "he he"),
    },
    decimalIntItems = {
        @DecimalIntItem(key = "decimal_int_ha_ha", defValue = 454),
        @DecimalIntItem(name = "DECIMAL_INT_HAHA", key = "decimal_int_he_he", defValue = 878),
    }
)
public abstract class TextPreferencesImpl extends Preferences {

  public TextPreferencesImpl(SharedPreferences shardPref) {
    super(shardPref);
  }

  public TextPreferencesImpl(List<String> list, Map<Lock[][][], Lock> map) {
    super(null);
  }
}
